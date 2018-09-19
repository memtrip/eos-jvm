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
import com.memtrip.eos.abi.writer.preprocessor.model.AbiModel;
import com.memtrip.eos.abi.writer.preprocessor.model.AbiWriterModel;

import java.io.IOException;
import java.util.List;

public class Generate {

    private final AbiWriterModel abiWriterModel;
    private final List<AbiModel> abiModelList;
    private final FreeMarker freeMarker;
    private final SourceFileGenerator sourceFileGenerator;

    public Generate(
        AbiWriterModel abiWriterModel,
        List<AbiModel> abiModelList,
        FreeMarker freeMarker,
        SourceFileGenerator sourceFileGenerator
    ) {
        this.abiWriterModel = abiWriterModel;
        this.abiModelList = abiModelList;
        this.freeMarker = freeMarker;
        this.sourceFileGenerator = sourceFileGenerator;
    }

    public void generate() throws IOException, FormatterException {
        new RootGen(
            abiWriterModel,
            new RootMap(abiWriterModel, abiModelList),
            freeMarker,
            sourceFileGenerator
        ).write();

        for (AbiModel abiModel : abiModelList) {
            new SquishableGen(
                abiWriterModel,
                new SquishableMap(abiWriterModel, abiModel),
                abiModel.getClassName() + "Squishable",
                freeMarker,
                sourceFileGenerator
            ).write();
        }
    }
}
