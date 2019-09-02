package test.apache.skywalking.apm.helper.vo;

import java.util.Map;

public class CaseConfiguration {
    private String type;
    private String entryService;
    private String healthCheck;
    private String framework;
    private String version;
    private String startScript;
    private Map<String, DependencyComponent> dependencies;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEntryService() {
        return entryService;
    }

    public void setEntryService(String entryService) {
        this.entryService = entryService;
    }

    public String getFramework() {
        return framework;
    }

    public void setFramework(String framework) {
        this.framework = framework;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getStartScript() {
        return startScript;
    }

    public void setStartScript(String startScript) {
        this.startScript = startScript;
    }

    public Map<String, DependencyComponent> getDependencies() {
        return dependencies;
    }

    public void setDependencies(Map<String, DependencyComponent> dependencies) {
        this.dependencies = dependencies;
    }

    public String getHealthCheck() {
        return healthCheck;
    }

    public void setHealthCheck(String healthCheck) {
        this.healthCheck = healthCheck;
    }
}
