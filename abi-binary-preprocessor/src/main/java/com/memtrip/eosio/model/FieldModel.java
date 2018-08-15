package com.memtrip.eosio.model;

public class FieldModel {

    private final String name;
    private final String classType;
    private final CompressType compressType;

    public String getName() {
        return name;
    }

    public String getClassType() {
        return classType;
    }

    public boolean isName() {
        return compressType == CompressType.NAME;
    }

    public boolean isAccountName() {
        return compressType == CompressType.ACCOUNT_NAME;
    }

    public boolean isBlockNum() {
        return compressType == CompressType.BLOCK_NUM;
    }

    public boolean isBlockPrefix() {
        return compressType == CompressType.BLOCK_PREFIX;
    }

    public boolean isData() {
        return compressType == CompressType.DATA;
    }

    public boolean isTimestamp() {
        return compressType == CompressType.TIMESTAMP;
    }

    public boolean isByte() {
        return compressType == CompressType.BYTE;
    }

    public boolean isShort() {
        return compressType == CompressType.SHORT;
    }

    public boolean isInt() {
        return compressType == CompressType.INT;
    }

    public boolean isVariableUInt() {
        return compressType == CompressType.VARIABLE_UINT;
    }

    public boolean isLong() {
        return compressType == CompressType.LONG;
    }

    public boolean isBytes() {
        return compressType == CompressType.BYTES;
    }

    public boolean isStringCollection() {
        return compressType == CompressType.STRING_COLLECTION;
    }

    public boolean isString() {
        return compressType == CompressType.STRING;
    }

    public boolean isCollection() {
        return compressType == CompressType.COLLECTION;
    }

    public boolean isChild() {
        return compressType == CompressType.CHILD;
    }

    public FieldModel(String name, String classType, CompressType compressType) {
        this.name = name;
        this.classType = classType;
        this.compressType = compressType;
    }
}
