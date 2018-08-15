package com.memtrip.eosio;

import com.memtrip.eosio.abi.binary.*;
import com.memtrip.eosio.model.AbiModel;
import com.memtrip.eosio.model.CompressType;
import com.memtrip.eosio.model.FieldModel;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

final class ParseAnnotations {

    private final Elements elementUtils;

    ParseAnnotations(Elements elementUtils) {
        this.elementUtils = elementUtils;
    }

    List<AbiModel> abi(Set<? extends Element> elements) {

        List<AbiModel> abiModels = new ArrayList<>();

        for (Element element : elements) {
            abiModels.add(new AbiModel(
                element.getSimpleName().toString(),
                elementUtils.getPackageOf(element).getQualifiedName().toString(),
                fields(element)
            ));
        }

        return abiModels;
    }

    private List<FieldModel> fields(Element element) {

        List<FieldModel> fieldModels = new ArrayList<>();

        for (Element child : element.getEnclosedElements()) {
            if (elementHasFieldAnnotation(child)) {
                fieldModels.add(new FieldModel(
                    extractName(child.getSimpleName().toString()),
                    extractClassType(child),
                    extractAnnotationType(child)
                ));
            }
        }

        return fieldModels;
    }

    private String extractName(String name) {
        if (name.contains("$")) {
            return name.split("$")[0];
        } else {
            return name;
        }
    }

    private String extractClassType(Element element) {
        TypeMirror typeMirror = element.asType();
        return typeMirror.toString();
    }

    private CompressType extractAnnotationType(Element element) {
        if (hasAnnotation(element, NameCompress.class)) {
            return CompressType.NAME;
        } else if (hasAnnotation(element, AccountNameCompress.class)) {
            return CompressType.ACCOUNT_NAME;
        } else if (hasAnnotation(element, BlockNumCompress.class)) {
            return CompressType.BLOCK_NUM;
        } else if (hasAnnotation(element, BlockPrefixCompress.class)) {
            return CompressType.BLOCK_PREFIX;
        } else if (hasAnnotation(element, DataCompress.class)) {
            return CompressType.DATA;
        } else if (hasAnnotation(element, TimestampCompress.class)) {
            return CompressType.TIMESTAMP;
        } else if (hasAnnotation(element, ByteCompress.class)) {
            return CompressType.BYTE;
        } else if (hasAnnotation(element, ShortCompress.class)) {
            return CompressType.SHORT;
        } else if (hasAnnotation(element, IntCompress.class)) {
            return CompressType.INT;
        } else if (hasAnnotation(element, VariableUIntCompress.class)) {
            return CompressType.VARIABLE_UINT;
        } else if (hasAnnotation(element, LongCompress.class)) {
            return CompressType.LONG;
        } else if (hasAnnotation(element, BytesCompress.class)) {
            return CompressType.BYTES;
        } else if (hasAnnotation(element, StringCompress.class)) {
            return CompressType.STRING;
        } else if (hasAnnotation(element, StringCollectionCompress.class)) {
            return CompressType.STRING_COLLECTION;
        } else if (hasAnnotation(element, CollectionCompress.class)) {
            return CompressType.COLLECTION;
        } else if (hasAnnotation(element, ChildCompress.class)) {
            return CompressType.CHILD;
        } else {
            throw new IllegalStateException("this method is not covering all the values " +
                "allowed by elementHasFieldAnnotation. This method is broken!");
        }
    }

    private boolean elementHasFieldAnnotation(Element element) {
        return hasAnnotation(element, NameCompress.class)
            || hasAnnotation(element, AccountNameCompress.class)
            || hasAnnotation(element, BlockNumCompress.class)
            || hasAnnotation(element, BlockPrefixCompress.class)
            || hasAnnotation(element, DataCompress.class)
            || hasAnnotation(element, TimestampCompress.class)
            || hasAnnotation(element, ByteCompress.class)
            || hasAnnotation(element, ShortCompress.class)
            || hasAnnotation(element, IntCompress.class)
            || hasAnnotation(element, VariableUIntCompress.class)
            || hasAnnotation(element, LongCompress.class)
            || hasAnnotation(element, BytesCompress.class)
            || hasAnnotation(element, StringCompress.class)
            || hasAnnotation(element, StringCollectionCompress.class)
            || hasAnnotation(element, CollectionCompress.class)
            || hasAnnotation(element, ChildCompress.class);
    }

    private boolean hasAnnotation(Element element, Class<? extends Annotation> clazz) {
        return element.getAnnotation(clazz) != null;
    }
}
