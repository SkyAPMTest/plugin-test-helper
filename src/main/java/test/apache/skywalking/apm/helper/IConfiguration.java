package test.apache.skywalking.apm.helper;

import test.apache.skywalking.apm.helper.vo.CaseConfiguration;

public interface IConfiguration {
    String agentPath();

    RunningType runningType();

    ScenarioRunningScriptGenerator scenarioGenerator();

    CaseConfiguration caseConfiguration();

    String scenarioName();

    String testingVersion();

    String testFramework();

    String entryService();

    String healthCheck();

    String dataPath();

    String packagesPath();

    String containerName();

    String scenarioHome();

    String outputDir();
}
