package test.apache.skywalking.apm.helper.vo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class DockerComposeTest {
    private InputStream dockerCompose;
    private String writeFile;

    @Before
    public void setUp() {
        writeFile = CaseIConfigurationTest.class.getResource("/").getFile() + "/file.yaml";
        dockerCompose = CaseIConfigurationTest.class.getResourceAsStream("/docker-compose-test.yml");
        assertNotNull(dockerCompose);
    }

    @Test
    public void readDockerCompose() {
        Yaml yaml = new Yaml();
        DockerCompose dockerfile = yaml.loadAs(dockerCompose, DockerCompose.class);
        assertNotNull(dockerfile);
        assertThat(dockerfile.getServices().size(), is(2));
    }

    @Test
    public void writeDockerCompose() throws IOException {
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
        Yaml yaml = new Yaml(representer, dumperOptions);

        DockerCompose dockerfile = yaml.loadAs(dockerCompose, DockerCompose.class);
        FileWriter writer = new FileWriter(writeFile);
        yaml.dump(dockerfile, writer);

        assertTrue(new File(writeFile).exists());
    }

    @After
    public void tearDown(){
        new File(writeFile).deleteOnExit();
    }
}