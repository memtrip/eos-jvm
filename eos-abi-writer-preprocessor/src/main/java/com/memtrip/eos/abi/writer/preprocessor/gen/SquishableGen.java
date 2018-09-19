package com.memtrip.eos.abi.writer.preprocessor.gen;

import com.google.googlejavaformat.java.FormatterException;
import com.memtrip.eos.abi.writer.preprocessor.FreeMarker;
import com.memtrip.eos.abi.writer.preprocessor.SourceFileGenerator;
import com.memtrip.eos.abi.writer.preprocessor.model.AbiWriterModel;

import java.io.IOException;

class SquishableGen extends Gen<SquishableMap> {

    private final String fileName;
    private final AbiWriterModel abiWriterModel;
    private final SquishableMap squishableMap;

    SquishableGen(
        AbiWriterModel abiWriterModel,
        SquishableMap SquishableMap,
        String fileName,
        FreeMarker freeMarker,
        SourceFileGenerator sourceFileGenerator
    ) {
        super(freeMarker, sourceFileGenerator);

        this.fileName = fileName;
        this.abiWriterModel = abiWriterModel;
        this.squishableMap = SquishableMap;
    }

    void write() throws IOException, FormatterException {
        super.write(
            "Squishable.template",
            squishableMap,
            abiWriterModel.getClassPackage(),
            fileName);
    }
}
