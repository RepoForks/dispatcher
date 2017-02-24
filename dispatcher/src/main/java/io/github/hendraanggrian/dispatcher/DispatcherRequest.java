package io.github.hendraanggrian.dispatcher;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.util.Random;

/**
 * Represents a single request to start another Activity for result.
 * This object is kept with static modifier in {@link Dispatcher} and is released as soon as one of the callbacks is triggered.
 *
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */

public abstract class DispatcherRequest {

    @Nullable static WeakReference<Random> RANDOM;

    public abstract void dispatch();

    @NonNull final SourceFactory factory;
    @NonNull final Object source;
    final int requestCode;

    DispatcherRequest(@NonNull SourceFactory factory, @NonNull Object source) {
        this.factory = factory;
        this.source = source;
        this.requestCode = generateRandom();
    }

    @Override
    public String toString() {
        return String.format("%s @%s", source.getClass().getSimpleName(), requestCode);
    }

    private static int generateRandom() {
        if (RANDOM != null && RANDOM.get() != null)
            return RANDOM.get().nextInt(255);
        RANDOM = new WeakReference<>(new Random());
        return generateRandom();
    }
}