package test.apache.skywalking.apm.helper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;
import test.apache.skywalking.apm.helper.exception.GenerateFailedException;
import test.apache.skywalking.apm.helper.vo.DockerCompose;
import test.apache.skywalking.apm.helper.vo.DockerService;
import test.apache.skywalking.apm.helper.vo.DockerServiceReader;

public class DockerComposeRunningGenerator extends AbstractRunningGenerator {

    @Override
    public void generateAdditionFiles(IConfiguration configuration) throws GenerateFailedException {
        DockerCompose dockerCompose = convertDockerCompose(configuration);

        try {
            getFileWriter().dump(dockerCompose, new FileWriter(new File(configuration.outputDir(), "docker-compose" +
                ".yml")));
        } catch (IOException e) {
            throw new GenerateFailedException();
        }
    }

    private DockerCompose convertDockerCompose(IConfiguration configuration) {
        DockerCompose dockerCompose = new DockerCompose();
        dockerCompose.setVersion("2.1");

        HashMap<String, DockerService> dockerServices = new HashMap<>();
        DockerService container = new DockerService();
        container.setImage(String.format("%s:latest", configuration.containerName()));

        configuration.caseConfiguration().getDependencies().forEach((name, dependencyComponent) -> {
            DockerService dockerService = new DockerService();
            DockerServiceReader serviceReader = dependencyComponent;
            dockerService.setEnvironment(serviceReader.environment());
            dockerService.setImage(serviceReader.image());
            dockerServices.put(name, dockerService);
        });

        container.setLinks(new ArrayList<>(configuration.caseConfiguration().getDependencies().keySet()));

        List<String> environment = new ArrayList<String>();
        environment.add(String.format("SCENARIO_VERSION=%s", configuration.testingVersion()));
        environment.add(String.format("SCENARIO_SUPPORT_FRAMEWORK=%s", configuration.testFramework()));
        environment.add(String.format("SCENARIO_ENTRY_SERVICE=%s", configuration.entryService()));
        environment.add(String.format("SCENARIO_HEALTH_CHECK_URL=%s", configuration.healthCheck()));

        List<String> volumes = new ArrayList<>();
        volumes.add(String.format("%s:/usr/local/skywalking-agent-scenario/agent", configuration.agentPath()));
        volumes.add(String.format("%s:/usr/local/skywalking-agent-scenario/data", configuration.dataPath()));
        volumes.add(String.format("%s:/usr/local/skywalking-agent-scenario/packages", configuration.packagesPath()));

        container.setVolumes(volumes);
        container.setEnvironment(environment);

        dockerServices.put(String.format("skywalking-agent-test-%s-%s", configuration.testFramework(),
            configuration.testingVersion())
            , container);
        dockerCompose.setServices(dockerServices);

        return dockerCompose;
    }

    private Yaml getFileWriter() {
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Representer representer = new Representer() {
            @Override
            protected NodeTuple representJavaBeanProperty(Object javaBean, Property property, Object propertyValue,
                Tag customTag) {
                if (propertyValue == null) {
                    return null;
                } else {
                    return super.representJavaBeanProperty(javaBean, property, propertyValue, customTag);
                }
            }
        };

        representer.addClassTag(DockerCompose.class, Tag.MAP);
        return new Yaml(representer, dumperOptions);
    }

    @Override
    public String runningScript(IConfiguration configuration) {
        return String.format("docker-compose -f %s up", configuration.outputDir() + File.separator + "docker-compose" +
            ".yml");
    }
}
