package io.github.hendraanggrian.dispatcher;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Random;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
final class DispatcherRequest implements Dispatcher.Callback {

    private static final int REQUEST_CODE_MAX_VALUE = 255;

    @Nullable private static Random random;

    @NonNull private final Integer requestCode;
    @NonNull private final Dispatcher.Callback callback;

    DispatcherRequest(@NonNull Dispatcher.Callback callback) {
        if (random == null)
            random = new Random();
        this.requestCode = random.nextInt(REQUEST_CODE_MAX_VALUE);
        this.callback = callback;
    }

    DispatcherRequest(@Nullable Dispatcher.OnOK onOK, @Nullable Dispatcher.OnCanceled onCanceled, @Nullable Dispatcher.OnUndefined onUndefined) {
        this(new Dispatcher.Callback() {
            @Override
            public void onCanceled(Intent data) {
                if (onCanceled != null)
                    onCanceled.onCanceled(data);
            }

            @Override
            public void onOK(Intent data) {
                if (onOK != null)
                    onOK.onOK(data);
            }

            @Override
            public void onUndefined(Intent data, int resultCode) {
                if (onUndefined != null)
                    onUndefined.onUndefined(data, resultCode);
            }
        });
    }

    @Override
    public void onOK(Intent data) {
        callback.onOK(data);
    }

    @Override
    public void onCanceled(Intent data) {
        callback.onCanceled(data);
    }

    @Override
    public void onUndefined(Intent data, int resultCode) {
        callback.onUndefined(data, resultCode);
    }

    boolean match(int requestCode) {
        return this.requestCode == requestCode;
    }

    void start(@NonNull Activity activity, @NonNull Intent intent) {
        activity.startActivityForResult(intent, requestCode);
    }

    void start(@NonNull android.app.Fragment fragment, @NonNull Intent intent) {
        fragment.startActivityForResult(intent, requestCode);
    }

    void start(@NonNull android.support.v4.app.Fragment fragment, @NonNull Intent intent) {
        fragment.startActivityForResult(intent, requestCode);
    }
}