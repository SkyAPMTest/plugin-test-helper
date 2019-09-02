package test.apache.skywalking.apm.helper.vo;

import java.util.HashMap;

public class DockerCompose {
    private String version;
    private HashMap<String, DockerService> services;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public HashMap<String, DockerService> getServices() {
        return services;
    }

    public void setServices(HashMap<String, DockerService> services) {
        this.services = services;
    }
}
