package com.memtrip.eos.abi.writer.preprocessor;

import com.google.auto.service.AutoService;
import com.google.googlejavaformat.java.FormatterException;
import com.memtrip.eos.abi.writer.Abi;
import com.memtrip.eos.abi.writer.AbiWriter;
import com.memtrip.eos.abi.writer.preprocessor.gen.Generate;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@AutoService(Processor.class)
public final class Preprocessor extends AbstractProcessor {
    private Filer filer;
    private Elements elementUtils;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment env) {
        filer = env.getFiler();
        elementUtils = env.getElementUtils();
        messager = env.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annoations, RoundEnvironment env) {

        Set<? extends Element> abiElements = env.getElementsAnnotatedWith(Abi.class);
        Set<? extends Element> abiWriterElements = env.getElementsAnnotatedWith(AbiWriter.class);

        if (!abiElements.isEmpty()) {
            if (abiWriterElements.isEmpty()) {
                messager.printMessage(
                    Diagnostic.Kind.ERROR,
                    "You must annotate an interface with @AbiWriter for us to generate the boilerplate for you.\n" +
                        "We will generate the boilerplate in the package of this interface and generate a class named \n" +
                        "AbiBinaryGen${your_interface_name} for you.");
                return false;
            } else {

                ParseAnnotations parseAnnotations = new ParseAnnotations(elementUtils);

                try {
                    new Generate(
                        parseAnnotations.abiWriter(abiWriterElements),
                        parseAnnotations.abi(abiElements),
                        new FreeMarker(),
                        new SourceFileGenerator(filer, messager)
                    ).generate();
                } catch (IOException | FormatterException e) {
                    messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>();
        set.add(Abi.class.getCanonicalName());
        set.add(AbiWriter.class.getCanonicalName());
        return set;
    }
}