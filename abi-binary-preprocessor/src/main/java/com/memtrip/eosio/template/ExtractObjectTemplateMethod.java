package com.memtrip.eosio.template;

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