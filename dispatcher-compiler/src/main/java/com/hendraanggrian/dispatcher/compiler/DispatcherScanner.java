package com.hendraanggrian.dispatcher.compiler;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.util.TreePathScanner;
import com.sun.source.util.Trees;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.code.TypeTag;
import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.List;

import javax.tools.JavaFileObject;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
final class DispatcherScanner extends TreePathScanner<Object, CompilationUnitTree> implements Scanner<Object, CompilationUnitTree> {

    private final Trees trees;
    private final TreeMaker maker;
    private final JavacElements utils;

    DispatcherScanner(Trees trees, TreeMaker maker, JavacElements utils) {
        this.trees = trees;
        this.maker = maker;
        this.utils = utils;
    }

    @Override
    public Object visitClass(ClassTree classTree, CompilationUnitTree unitTree) {
        if (unitTree instanceof JCTree.JCCompilationUnit) {
            JCTree.JCCompilationUnit compilationUnit = (JCTree.JCCompilationUnit) unitTree;
            // Only process on files which have been compiled from source
            if (compilationUnit.sourcefile.getKind() == JavaFileObject.Kind.SOURCE) {
                compilationUnit.accept(new TreeTranslator() {
                    private boolean hasOnActivityResult = false;
                    private boolean hasOnRequestPermissionResult = false;

                    @Override
                    public void visitMethodDef(JCTree.JCMethodDecl tree) {
                        super.visitMethodDef(tree);
                        if (tree.name.toString().equals("onActivityResult") || tree.name.toString().equals("onRequestPermissionsResult")) {
                            switch (tree.name.toString()) {
                                case "onActivityResult":
                                    hasOnActivityResult = true;
                                    break;
                                case "onRequestPermissionsResult":
                                    hasOnRequestPermissionResult = true;
                                    break;
                            }
                            boolean hasDispatcher = false;
                            for (JCTree.JCStatement stat : tree.body.stats)
                                if (stat.toString().contains("Dispatcher." + tree.name.toString()))
                                    hasDispatcher = true;
                            if (!hasDispatcher)
                                tree.body.stats = tree.body.stats.append(createMethodCall("com.hendraanggrian.dispatcher", "Dispatcher", tree.name.toString(), tree.params));
                        }
                    }

                    @Override
                    public void visitClassDef(JCTree.JCClassDecl tree) {
                        super.visitClassDef(tree);
                        String methodName;
                        String[] params;
                        if (!hasOnActivityResult) {
                            methodName = "onActivityResult";
                            params = new String[]{"requestCode", "resultCode", "data"};
                            tree.defs = tree.defs.append(maker.MethodDef(
                                    maker.Modifiers(Flags.PUBLIC),
                                    utils.getName(methodName),
                                    maker.Type(new Type.JCVoidType()),
                                    List.<JCTree.JCTypeParameter>nil(),
                                    List.of(
                                            createMethodParam(params[0], maker.TypeIdent(TypeTag.INT)),
                                            createMethodParam(params[1], maker.TypeIdent(TypeTag.INT)),
                                            createMethodParam(params[2], maker.Select(maker.Ident(utils.getName("android.content")), utils.getName("Intent")))
                                    ),
                                    List.<JCTree.JCExpression>nil(),
                                    maker.Block(0, List.<JCTree.JCStatement>of(
                                            createMethodCall(null, "super", methodName, params),
                                            createMethodCall("com.hendraanggrian.dispatcher", "Dispatcher", methodName, params)
                                    )),
                                    null
                            ));
                        }
                        if (!hasOnRequestPermissionResult) {
                            methodName = "onRequestPermissionsResult";
                            params = new String[]{"requestCode", "permissions", "grantResults"};
                            tree.defs = tree.defs.append(maker.MethodDef(
                                    maker.Modifiers(Flags.PUBLIC),
                                    utils.getName(methodName),
                                    maker.Type(new Type.JCVoidType()),
                                    List.<JCTree.JCTypeParameter>nil(),
                                    List.of(
                                            createMethodParam(params[0], maker.TypeIdent(TypeTag.INT)),
                                            createMethodParam(params[1], maker.TypeArray(maker.Ident(utils.getName("String")))),
                                            createMethodParam(params[2], maker.TypeArray(maker.TypeIdent(TypeTag.INT)))
                                    ),
                                    List.<JCTree.JCExpression>nil(),
                                    maker.Block(0, List.<JCTree.JCStatement>of(
                                            createMethodCall(null, "super", methodName, params),
                                            createMethodCall("com.hendraanggrian.dispatcher", "Dispatcher", methodName, params)
                                    )),
                                    null
                            ));
                        }
                    }
                });
            }
        }
        return trees;
    }

    private JCTree.JCVariableDecl createMethodParam(String varName, JCTree.JCExpression type) {
        return maker.VarDef(maker.Modifiers(Flags.PARAMETER), utils.getName(varName), type, null);
    }

    private JCTree.JCExpressionStatement createMethodCall(String packageName, String clsName, String methodName, List<JCTree.JCVariableDecl> params) {
        String[] paramsNames = new String[params.length()];
        for (int i = 0; i < paramsNames.length; i++)
            paramsNames[i] = params.get(i).getName().toString();
        return createMethodCall(packageName, clsName, methodName, paramsNames);
    }

    private JCTree.JCExpressionStatement createMethodCall(String packageName, String clsName, String methodName, String... paramNames) {
        List<JCTree.JCExpression> params = List.nil();
        for (String paramName : paramNames)
            params = params.append(maker.Ident(utils.getName(paramName)));
        return maker.Exec(maker.Apply(
                List.<JCTree.JCExpression>nil(),
                maker.Select(
                        packageName != null
                                ? maker.Select(maker.Ident(utils.getName(packageName)), utils.getName(clsName))
                                : maker.Ident(utils.getName(clsName)),
                        utils.getName(methodName)
                ),
                params
        ));
    }
}