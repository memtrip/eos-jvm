package com.memtrip.eosio.gen;

import com.google.googlejavaformat.java.FormatterException;
import com.memtrip.eosio.FreeMarker;
import com.memtrip.eosio.SourceFileGenerator;

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
