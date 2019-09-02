package test.apache.skywalking.apm.helper.vo;

import java.util.List;

public class DependencyComponent implements DockerServiceReader {
    private String image;
    private String host;
    private List<String> environments;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public List<String> getEnvironments() {
        return environments;
    }

    public void setEnvironments(List<String> environments) {
        this.environments = environments;
    }

    @Override public List<String> environment() {
        return environments;
    }

    @Override public String image() {
        return host;
    }
}
