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
