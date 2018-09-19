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
