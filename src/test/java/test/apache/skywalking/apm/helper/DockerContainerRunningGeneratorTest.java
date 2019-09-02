package test.apache.skywalking.apm.helper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DockerContainerRunningGeneratorTest {

    private DockerContainerRunningGenerator generator;

    @Mock
    private IConfiguration configuration;

    @Before
    public void setUp() {
        generator = new DockerContainerRunningGenerator();
        when(configuration.agentPath()).thenReturn("/agent/path");
        when(configuration.containerName()).thenReturn("skyapm/agent-tomcat");
        when(configuration.entryService()).thenReturn("http://localhost:8080/entryService");
        when(configuration.healthCheck()).thenReturn("http://localhost:8080/healthCheck");
        when(configuration.testFramework()).thenReturn("http");
        when(configuration.testingVersion()).thenReturn("4.3.2");
        when(configuration.packagesPath()).thenReturn("/package/path");
    }

    @Test
    public void testGenerateDockerContainerStartScript() {
        String script = generator.runningScript(configuration);
        assertNotNull(script);
    }

}