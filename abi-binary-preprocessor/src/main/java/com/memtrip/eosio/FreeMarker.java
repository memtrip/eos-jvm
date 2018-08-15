package com.memtrip.eosio;

import com.memtrip.eosio.gen.DataMap;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

public final class FreeMarker {
    private Configuration configuration;

    private static final String TEMPLATE_ENCODING = "UTF-8";

    FreeMarker() {
        configuration = createConfiguration();
    }

    private Configuration createConfiguration() {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_21);
        configuration.setDefaultEncoding(TEMPLATE_ENCODING);
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        configuration.setClassForTemplateLoading(FreeMarker.class,"/");
        return configuration;
    }

    public String generate(String templateFileName, DataMap dataMap) {
        try {
            return process(templateFileName, dataMap.map());
        } catch (TemplateException e) {
            throw new IllegalStateException(
                "failed template: \n" + e.getTemplateSourceName() +
                "expression: \n" + e.getBlamedExpressionString() +
                "line: \n" + e.getLineNumber() +
                "col: \n" + e.getColumnNumber()
            );
        } catch (IOException e) {
            throw new IllegalStateException("Mapping com.memtrip.eosio.template FAILED: \n" + e.getMessage());
        }
    }

    private String process(String templateFileName, Map<String, Object> map) throws TemplateException, IOException {
        Template template = getTemplate(templateFileName);

        Writer out = new StringWriter();
        template.process(map, out);
        String output = out.toString();
        out.close();

        return output;
    }

    private Template getTemplate(String fileName) throws IOException {
        return configuration.getTemplate(fileName);
    }
}