package com.memtrip.eos.abi.writer.preprocessor.model;

public class AbiWriterModel {

    private final String className;
    private final String classPackage;

    public String getClassName() {
        return className;
    }

    public String getClassPackage() {
        return classPackage;
    }

    public AbiWriterModel(String className, String classPackage) {
        this.className = className;
        this.classPackage = classPackage;
    }
}
