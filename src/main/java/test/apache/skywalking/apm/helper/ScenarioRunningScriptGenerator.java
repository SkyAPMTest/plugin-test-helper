package test.apache.skywalking.apm.helper;

import test.apache.skywalking.apm.helper.exception.GenerateFailedException;

public interface ScenarioRunningScriptGenerator {
    void generate(IConfiguration configuration) throws GenerateFailedException;
}
