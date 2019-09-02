package test.apache.skywalking.apm.helper;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.apache.skywalking.apm.helper.vo.CaseConfiguration;

public class DockerContainerRunningGenerator extends AbstractRunningGenerator {

    private Logger logger = LoggerFactory.getLogger(DockerContainerRunningGenerator.class);

    protected DockerContainerRunningGenerator() {
    }

    @Override
    public void generateAdditionFiles(IConfiguration configuration) {
        // DO Nothing
    }

    @Override
    public String runningScript(IConfiguration configuration) {
        Map<String, Object> root = new HashMap<>();
        root.put("data_path", configuration.dataPath());
        root.put("agent_path", configuration.agentPath());
        root.put("packages_path", configuration.packagesPath());
        root.put("container_name", configuration.containerName());
        root.put("entry_service", configuration.entryService());
        root.put("health_check", configuration.healthCheck());
        root.put("test_framework", configuration.testFramework());
        root.put("testing_version", configuration.testingVersion());
        StringWriter out = null;

        try {
            out = new StringWriter();
            cfg.getTemplate("container-start-script.template").process(root, out);
        } catch (Exception e) {
            logger.debug("Failed to generate running script.", e);
        }
        return out.toString();
    }
}
