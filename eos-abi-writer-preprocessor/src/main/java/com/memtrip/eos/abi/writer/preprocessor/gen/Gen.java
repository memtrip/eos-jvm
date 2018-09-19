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