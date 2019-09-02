package test.apache.skywalking.apm.helper;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.apache.skywalking.apm.helper.exception.GenerateFailedException;

public abstract class AbstractRunningGenerator implements ScenarioRunningScriptGenerator {

    private Logger logger = LoggerFactory.getLogger(AbstractRunningGenerator.class);
    protected final Configuration cfg;

    protected AbstractRunningGenerator() {
        cfg = new Configuration(Configuration.VERSION_2_3_28);
        try {
            cfg.setClassLoaderForTemplateLoading(this.getClass().getClassLoader(), "/");
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
            cfg.setWrapUncheckedExceptions(true);
        } catch (Exception e) {
            // never to do this
        }
    }

    @Override
    public final void generate(IConfiguration configuration) throws GenerateFailedException {
        generateAdditionFiles(configuration);

        Map<String, Object> root = new HashMap<>();
        root.put("running_script", runningScript(configuration));

        try {
            cfg.getTemplate("scenario.sh").process(root,
                new FileWriter(new File(configuration.outputDir() + File.separator + "scenario.sh")));
        } catch (Exception e) {
            logger.error("Failed to write scenario.sh", e);
        }
    }

    public abstract void generateAdditionFiles(IConfiguration configuration) throws GenerateFailedException;

    public abstract String runningScript(IConfiguration configuration);
}
