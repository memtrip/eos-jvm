package com.memtrip.eos.abi.writer.preprocessor.gen;

import com.memtrip.eos.abi.writer.preprocessor.model.AbiModel;
import com.memtrip.eos.abi.writer.preprocessor.template.ExtractListObjectTemplateMethod;
import com.memtrip.eos.abi.writer.preprocessor.template.ExtractObjectTemplateMethod;

import java.util.HashMap;
import java.util.Map;

class SquishableMap implements DataMap {

    private final AbiModel abiModel;

    SquishableMap(AbiModel abiModel) {
        this.abiModel = abiModel;
    }

    @Override
    public Map<String, Object> map() {
        Map<String, Object> map = new HashMap<>();
        map.put("abi", abiModel);
        map.putAll(ExtractListObjectTemplateMethod.mapEntry());
        map.putAll(ExtractObjectTemplateMethod.mapEntry());
        return map;
    }
}
