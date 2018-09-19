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
package com.memtrip.eos.abi.writer.preprocessor;

import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;

public final class SourceFileGenerator {

    private final Filer filer;
    private final Messager messager;

    SourceFileGenerator(Filer filer, Messager messager) {
        this.filer = filer;
        this.messager = messager;
    }

    public void create(String packageName, String fileName, String body) throws IOException, FormatterException {

        JavaFileObject jfo = filer.createSourceFile(packageName + "." + fileName);

        messager.printMessage(
            Diagnostic.Kind.NOTE,
            "creating source file at: " + jfo.toUri());

        String formattedSource = new Formatter().formatSource(body);

        Writer writer = jfo.openWriter();
        writer.append(formattedSource);
        writer.close();
    }
}
