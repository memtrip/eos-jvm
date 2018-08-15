package com.memtrip.eosio;

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
