package com.hendraanggrian.dispatcher;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.util.Random;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
abstract class Dispatch {

    @Nullable static WeakReference<Random> RANDOM;

    @NonNull final Source source;
    final int requestCode;

    Dispatch(@NonNull Source source) {
        this.source = source;
        this.requestCode = generateRandom();
    }

    @Override
    public String toString() {
        return String.format("%s @%s", source.toString(), requestCode);
    }

    private static int generateRandom() {
        if (RANDOM != null && RANDOM.get() != null)
            return RANDOM.get().nextInt(255);
        RANDOM = new WeakReference<>(new Random());
        return generateRandom();
    }
}