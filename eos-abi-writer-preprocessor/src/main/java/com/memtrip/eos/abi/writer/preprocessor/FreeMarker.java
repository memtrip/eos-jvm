/**
 * Copyright 2013-present memtrip LTD.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.memtrip.eos.abi.writer.preprocessor;

import com.memtrip.eos.abi.writer.preprocessor.gen.DataMap;
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
            throw new IllegalStateException("Mapping com.memtrip.eosio.preprocessor.template FAILED: \n" + e.getMessage());
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