/**
 * Copyright 2013-present memtrip LTD.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
