package com.memtrip.eosio.gen;

import com.google.googlejavaformat.java.FormatterException;
import com.memtrip.eosio.FreeMarker;
import com.memtrip.eosio.SourceFileGenerator;

import java.io.IOException;

class SquishableGen extends Gen<SquishableMap> {

    private final String fileName;
    private final SquishableMap SquishableMap;

    SquishableGen(SquishableMap SquishableMap,
                    String fileName,
                    FreeMarker freeMarker,
                    SourceFileGenerator sourceFileGenerator) {

        super(freeMarker, sourceFileGenerator);

        this.fileName = fileName;
        this.SquishableMap = SquishableMap;
    }

    void write() throws IOException, FormatterException {
        super.write(
            "Squishable.template",
            SquishableMap,
            "com.memtrip.eosio.abi.binary.gen",
            fileName);
    }
}
