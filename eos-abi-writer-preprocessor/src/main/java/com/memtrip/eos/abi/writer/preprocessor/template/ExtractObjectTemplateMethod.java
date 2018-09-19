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
package com.memtrip.eos.abi.writer.preprocessor.template;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtractObjectTemplateMethod implements TemplateMethodModelEx {

    public static Map<String, Object> mapEntry() {
        Map<String, Object> map = new HashMap<>();
        map.put("extractObject", new ExtractObjectTemplateMethod());
        return map;
    }

    private String extraClassName(String packageType) {
        String[] parts = packageType.split("\\.");
        return parts[parts.length - 1];
    }

    private String typeArg(List args) {
        if (!args.isEmpty() && args.get(0) instanceof SimpleScalar) {
            return ((SimpleScalar) args.get(0)).getAsString();
        } else {
            throw new IllegalStateException("The extractObject(String type); method requires one string argument.");
        }
    }

    @Override
    public Object exec(List arguments) {
        return extraClassName(typeArg(arguments));
    }
}