package test.apache.skywalking.apm.helper;

public class Main {

    public static void main(String[] args) throws Exception {
        IConfiguration configuration = new ConfigurationImpl();
        configuration.scenarioGenerator().generate(configuration);
    }
}
