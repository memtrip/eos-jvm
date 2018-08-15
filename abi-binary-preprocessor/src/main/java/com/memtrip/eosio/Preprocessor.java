package com.memtrip.eosio;

import com.google.auto.service.AutoService;
import com.google.googlejavaformat.java.FormatterException;
import com.memtrip.eosio.abi.binary.Abi;
import com.memtrip.eosio.gen.Generate;

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

        Set<? extends Element> elements = env.getElementsAnnotatedWith(Abi.class);

        if (!elements.isEmpty()) {
            try {
                new Generate(
                    new ParseAnnotations(elementUtils).abi(elements),
                    new FreeMarker(),
                    new SourceFileGenerator(filer, messager)
                ).generate();
            } catch (IOException | FormatterException e) {
                messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
                return false;
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
        return set;
    }
}