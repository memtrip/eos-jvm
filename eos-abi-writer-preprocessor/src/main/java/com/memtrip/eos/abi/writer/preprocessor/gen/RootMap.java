package com.memtrip.eos.abi.writer.preprocessor.gen;

import com.memtrip.eos.abi.writer.preprocessor.model.AbiModel;
import com.memtrip.eos.abi.writer.preprocessor.model.AbiWriterModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class RootMap implements DataMap {

    private final AbiWriterModel abiWriterModel;
    private final List<AbiModel> abiModelList;

    RootMap(AbiWriterModel abiWriterModel, List<AbiModel> abiModelList) {
        this.abiWriterModel = abiWriterModel;
        this.abiModelList = abiModelList;
    }

    @Override
    public Map<String, Object> map() {
        Map<String, Object> map = new HashMap<>();
        map.put("class_postfix", abiWriterModel.getClassName());
        map.put("package_name", abiWriterModel.getClassPackage());
        map.put("abi_list", abiModelList);
        return map;
    }
}
