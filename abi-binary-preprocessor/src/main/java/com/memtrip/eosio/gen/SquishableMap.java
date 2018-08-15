package com.memtrip.eosio.gen;

import com.memtrip.eosio.model.AbiModel;
import com.memtrip.eosio.template.ExtractListObjectTemplateMethod;

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
        return map;
    }
}
