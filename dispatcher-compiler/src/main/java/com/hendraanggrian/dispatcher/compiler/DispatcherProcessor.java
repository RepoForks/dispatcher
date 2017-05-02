package com.hendraanggrian.dispatcher.compiler;

import com.google.auto.service.AutoService;
import com.hendraanggrian.dispatcher.annotations.Dispatchable;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.util.TreePath;
import com.sun.source.util.Trees;
import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
@AutoService(Processor.class)
public final class DispatcherProcessor extends AbstractProcessor {

    private Trees trees;
    private TreeMaker maker;
    private JavacElements utils;

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new HashSet<>(Collections.singletonList(Dispatchable.class.getCanonicalName()));
    }

    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);
        trees = Trees.instance(env);
        Context context = ((JavacProcessingEnvironment) env).getContext();
        maker = TreeMaker.instance(context);
        utils = JavacElements.instance(context);
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment env) {
        Scanner<Object, CompilationUnitTree> scanner = new DispatcherScanner(trees, maker, utils);
        for (Element element : env.getElementsAnnotatedWith(Dispatchable.class)) {
            TreePath path = trees.getPath(element);
            scanner.scan(path, path.getCompilationUnit());
        }
        return true;
    }
}