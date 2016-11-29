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
    @Nullable private final Dispatcher.OnResultListener canceledListener;
    @Nullable private final Dispatcher.OnResultListener okListener;
    @Nullable private final Dispatcher.OnAnyResultListener othersListener;
    @Nullable private final Dispatcher.OnAnyResultListener anyListener;

    DispatcherRequest(@Nullable Dispatcher.OnResultListener canceledListener,
                      @Nullable Dispatcher.OnResultListener okListener,
                      @Nullable Dispatcher.OnAnyResultListener othersListener,
                      @Nullable Dispatcher.OnAnyResultListener anyListener) {
        if (random == null)
            random = new Random();
        this.requestCode = random.nextInt(REQUEST_CODE_MAX_VALUE);
        this.canceledListener = canceledListener;
        this.okListener = okListener;
        this.othersListener = othersListener;
        this.anyListener = anyListener;
    }

    final int getRequestCode() {
        return requestCode;
    }

    @Nullable
    final Dispatcher.OnResultListener getCanceledListener() {
        return canceledListener;
    }

    @Nullable
    final Dispatcher.OnResultListener getOKListener() {
        return okListener;
    }

    @Nullable
    final Dispatcher.OnAnyResultListener getOthersListener() {
        return othersListener;
    }

    @Nullable
    final Dispatcher.OnAnyResultListener getAnyListener() {
        return anyListener;
    }
}