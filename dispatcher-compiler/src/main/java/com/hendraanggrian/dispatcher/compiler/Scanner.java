package com.hendraanggrian.dispatcher.compiler;

import com.sun.source.util.TreePath;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
interface Scanner<R, P> {
    R scan(TreePath var1, P var2);
}