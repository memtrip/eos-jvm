package com.memtrip.eos.abi.writer.preprocessor.gen;

import com.google.googlejavaformat.java.FormatterException;
import com.memtrip.eos.abi.writer.preprocessor.FreeMarker;
import com.memtrip.eos.abi.writer.preprocessor.SourceFileGenerator;

import java.io.IOException;

class RootGen extends Gen<RootMap> {

    private final RootMap rootMap;

    RootGen(RootMap rootMap,
            FreeMarker freeMarker,
            SourceFileGenerator sourceFileGenerator) {

        super(freeMarker, sourceFileGenerator);

        this.rootMap = rootMap;
    }

    void write() throws IOException, FormatterException {
        super.write(
            "AbiBinaryGen.template",
            rootMap,
            "com.memtrip.eosio.abi.binary.gen",
            "AbiBinaryGen");
    }
}
