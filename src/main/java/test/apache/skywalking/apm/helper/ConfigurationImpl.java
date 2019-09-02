package test.apache.skywalking.apm.helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import org.yaml.snakeyaml.Yaml;
import test.apache.skywalking.apm.helper.exception.ConfigureFileNotFoundException;
import test.apache.skywalking.apm.helper.util.StringUtils;
import test.apache.skywalking.apm.helper.vo.CaseConfiguration;

public class ConfigurationImpl implements IConfiguration {

    private CaseConfiguration configuration;
    private final String scenarioHome;

    public ConfigurationImpl() throws FileNotFoundException, ConfigureFileNotFoundException {
        String configureFile = System.getProperty("configure.file");
        if (StringUtils.isBlank(configureFile)) {
            throw new ConfigureFileNotFoundException();
        }

        this.configuration = new Yaml().loadAs(new FileReader(new File(configureFile)), CaseConfiguration.class);
        this.scenarioHome = System.getProperty("scenario.home");
    }

    @Override
    public String agentPath() {
        return System.getProperty("agent.dir");
    }

    @Override public RunningType runningType() {
        return (configuration.getDependencies() != null && configuration.getDependencies().size() > 0) ?
            RunningType.DockerCompose :
            RunningType.Container;
    }

    @Override public ScenarioRunningScriptGenerator scenarioGenerator() {
        switch (runningType()) {
            case DockerCompose:
                return new DockerComposeRunningGenerator();
            case Container:
                return new DockerContainerRunningGenerator();
            default:
                throw new RuntimeException();
        }
    }

    @Override public CaseConfiguration caseConfiguration() {
        return this.configuration;
    }

    @Override public String scenarioName() {
        return System.getProperty("scenario.name");
    }

    @Override public String testingVersion() {
        return this.configuration.getVersion();
    }

    @Override public String testFramework() {
        return this.configuration.getFramework();
    }

    @Override public String entryService() {
        return this.configuration.getEntryService();
    }

    @Override public String healthCheck() {
        return this.configuration.getHealthCheck();
    }

    @Override public String dataPath() {
        return this.scenarioHome + File.separator + "data";
    }

    @Override public String packagesPath() {
        return this.scenarioHome + File.separator + "packages";
    }

    @Override public String containerName() {
        if ("TOMCAT".equalsIgnoreCase(this.configuration.getType())) {
            return "skyapm/agent-test-tomcat";
        }
        throw new RuntimeException("Illegal type!");
    }

    @Override public String scenarioHome() {
        return this.scenarioHome;
    }

    @Override public String outputDir(){
        return System.getProperty("output.dir");
    }
}
