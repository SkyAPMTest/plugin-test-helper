package test.apache.skywalking.apm.helper.vo;

import java.io.FileNotFoundException;
import java.io.InputStream;
import org.junit.Before;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class CaseIConfigurationTest {

    private InputStream configurationFile;

    @Before
    public void setUp() {
        configurationFile = CaseIConfigurationTest.class.getResourceAsStream("/configuration-test.yml");
        assertNotNull(configurationFile);
    }

    @Test
    public void testReadCaseConfiguration() throws FileNotFoundException {
        Yaml yaml = new Yaml();
        CaseConfiguration caseConfiguration = yaml.loadAs(configurationFile, CaseConfiguration.class);
        assertNotNull(caseConfiguration);

        assertThat(caseConfiguration.getVersion(), is("4.0.3"));
        assertThat(caseConfiguration.getFramework(), is("httpclient"));
        assertThat(caseConfiguration.getDependencies().size(), is(1));
    }
}