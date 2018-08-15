package com.memtrip.eosio.gen;

import com.google.googlejavaformat.java.FormatterException;
import com.memtrip.eosio.FreeMarker;
import com.memtrip.eosio.SourceFileGenerator;

import java.io.IOException;

abstract class Gen<T extends DataMap> {

    private final FreeMarker freeMarker;
    private final SourceFileGenerator sourceFileGenerator;

    Gen(FreeMarker freeMarker, SourceFileGenerator sourceFileGenerator) {
        this.freeMarker = freeMarker;
        this.sourceFileGenerator = sourceFileGenerator;
    }

    void write(
        String templateFilePath,
        T templateData,
        String outputFilePackage,
        String outputFileNameWithoutExtension
    ) throws IOException, FormatterException {
        String body = freeMarker.generate(templateFilePath, templateData);
        sourceFileGenerator.create(outputFilePackage, outputFileNameWithoutExtension, body);
    }
}