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
final class DispatcherRequest<Result> {

    private static final int REQUEST_CODE_MAX_VALUE = 255;

    @Nullable private static Random random;

    @NonNull private final Integer requestCode;
    @NonNull private final Intent initialIntent;
    @NonNull private final Dispatcher.OnResultListener<Result> listener;

    DispatcherRequest(@NonNull Intent initialIntent, @NonNull Dispatcher.OnResultListener<Result> listener) {
        if (random == null)
            random = new Random();
        this.requestCode = random.nextInt(REQUEST_CODE_MAX_VALUE);
        this.initialIntent = initialIntent;
        this.listener = listener;
    }

    final int getRequestCode() {
        return requestCode;
    }

    @NonNull
    final Intent getInitialIntent() {
        return initialIntent;
    }

    @NonNull
    final Dispatcher.OnResultListener<Result> getResultListener() {
        return listener;
    }
}