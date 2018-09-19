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
package com.memtrip.eos.abi.writer.preprocessor.model;

import java.util.List;

public class AbiModel {

    private final String className;
    private final String classPackage;
    private final List<FieldModel> fields;

    public String getClassName() {
        return className;
    }

    public String getClassPackage() {
        return classPackage;
    }

    public List<FieldModel> getFields() {
        return fields;
    }

    public AbiModel(String className, String classPackage, List<FieldModel> fields) {
        this.className = className;
        this.classPackage = classPackage;
        this.fields = fields;
    }
}
