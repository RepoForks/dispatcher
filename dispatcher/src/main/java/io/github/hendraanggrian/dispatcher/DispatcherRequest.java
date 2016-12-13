package io.github.hendraanggrian.dispatcher;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import java.util.Random;

/**
 * Represents a single request to start another Activity for result.
 * This object is kept with static modifier in {@link Dispatcher} and is released as soon as one of the callbacks is triggered.
 *
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
final class DispatcherRequest {

    private static final int REQUEST_CODE_MAX_VALUE = 255;
    @Nullable private static Random random;

    final int requestCode;
    @NonNull final Dispatcher.OnOK onOK;
    @Nullable final Dispatcher.OnCanceled onCanceled;
    @Nullable final Dispatcher.OnUndefined onUndefined;

    DispatcherRequest(@NonNull Dispatcher.OnOK onOK, @Nullable Dispatcher.OnCanceled onCanceled, @Nullable Dispatcher.OnUndefined onUndefined) {
        if (random == null)
            random = new Random();
        this.requestCode = random.nextInt(REQUEST_CODE_MAX_VALUE);
        this.onOK = onOK;
        this.onCanceled = onCanceled;
        this.onUndefined = onUndefined;
    }

    void start(@NonNull Activity activity, @NonNull Intent intent) {
        activity.startActivityForResult(intent, requestCode);
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    void start(@NonNull android.app.Fragment fragment, @NonNull Intent intent) {
        fragment.startActivityForResult(intent, requestCode);
    }

    void start(@NonNull android.support.v4.app.Fragment fragment, @NonNull Intent intent) {
        fragment.startActivityForResult(intent, requestCode);
    }
}