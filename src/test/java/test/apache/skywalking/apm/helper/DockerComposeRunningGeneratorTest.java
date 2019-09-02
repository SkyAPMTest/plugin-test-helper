package test.apache.skywalking.apm.helper;

import java.io.File;
import java.io.InputStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.yaml.snakeyaml.Yaml;
import test.apache.skywalking.apm.helper.exception.GenerateFailedException;
import test.apache.skywalking.apm.helper.vo.CaseConfiguration;
import test.apache.skywalking.apm.helper.vo.CaseIConfigurationTest;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DockerComposeRunningGeneratorTest {

    private DockerComposeRunningGenerator dockerComposeRunningGenerator;
    private InputStream configurationFile;

    @Mock
    private IConfiguration configuration;

    public static final String TARGET_DIR = DockerComposeRunningGeneratorTest.class.getResource("/").getFile();

    @Before
    public void setUp() {
        dockerComposeRunningGenerator = new DockerComposeRunningGenerator();

        when(configuration.outputDir()).thenReturn(TARGET_DIR);
        when(configuration.agentPath()).thenReturn("/agent/path");
        when(configuration.containerName()).thenReturn("skyapm/agent-tomcat");
        when(configuration.dataPath()).thenReturn("/data/path");
        when(configuration.entryService()).thenReturn("http://localhost:8080/entryService");
        when(configuration.healthCheck()).thenReturn("http://localhost:8080/healthCheck");
        when(configuration.testFramework()).thenReturn("http");
        when(configuration.testingVersion()).thenReturn("4.3.2");
        when(configuration.packagesPath()).thenReturn("/package/path");

        configurationFile = CaseIConfigurationTest.class.getResourceAsStream("/configuration-test.yml");
        assertNotNull(configurationFile);
        when(configuration.caseConfiguration()).thenReturn(new Yaml().loadAs(configurationFile, CaseConfiguration.class));
    }

    @Test
    public void testGenerateDockerCompose() {
        String runningScript = dockerComposeRunningGenerator.runningScript(configuration);
        assertEquals(String.format("docker-compose -f %s/docker-compose.yml up", TARGET_DIR), runningScript);
    }

    @Test
    public void testGenerateAdditionalFile() throws GenerateFailedException {
        dockerComposeRunningGenerator.generateAdditionFiles(configuration);
        assertTrue(new File(TARGET_DIR, "docker-compose.yml").exists());
    }

    @After
    public void tearDown() {

    }
}