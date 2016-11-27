package io.github.hendraanggrian.dispatcher;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Random;

/**
 * Base picker request used to store request code and listener for {@link android.app.Activity#onActivityResult(int, int, Intent)} is called.
 *
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
final class DispatcherRequest {

    private static final int REQUEST_CODE_MAX_VALUE = 255;

    @Nullable private static Random random;

    @NonNull private final Integer requestCode;
    @NonNull private final Dispatcher.OnResultListener listener;

    DispatcherRequest(@NonNull Dispatcher.OnResultListener listener) {
        if (random == null)
            random = new Random();
        this.requestCode = random.nextInt(REQUEST_CODE_MAX_VALUE);
        this.listener = listener;
    }

    final int getRequestCode() {
        return requestCode;
    }

    @NonNull
    final Dispatcher.OnResultListener getResultListener() {
        return listener;
    }
}