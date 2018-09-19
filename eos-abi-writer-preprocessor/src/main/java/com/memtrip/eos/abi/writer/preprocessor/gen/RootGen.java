package com.memtrip.eos.abi.writer.preprocessor.gen;

import com.google.googlejavaformat.java.FormatterException;
import com.memtrip.eos.abi.writer.preprocessor.FreeMarker;
import com.memtrip.eos.abi.writer.preprocessor.SourceFileGenerator;
import com.memtrip.eos.abi.writer.preprocessor.model.AbiWriterModel;

import java.io.IOException;

class RootGen extends Gen<RootMap> {

    private final AbiWriterModel abiWriterModel;
    private final RootMap rootMap;

    RootGen(
        AbiWriterModel abiWriterModel,
        RootMap rootMap,
        FreeMarker freeMarker,
        SourceFileGenerator sourceFileGenerator
    ) {

        super(freeMarker, sourceFileGenerator);

        this.abiWriterModel = abiWriterModel;
        this.rootMap = rootMap;
    }

    void write() throws IOException, FormatterException {
        super.write(
            "AbiBinaryGen.template",
            rootMap,
            abiWriterModel.getClassPackage(),
            "AbiBinaryGen" + abiWriterModel.getClassName());
    }
}
