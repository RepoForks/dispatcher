package io.github.hendraanggrian.contentpicker;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Random;

/**
 * Base picker request used to store request code and listener for {@link android.app.Activity#onActivityResult(int, int, Intent)} is called.
 *
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 * @see CameraPickerRequest
 * @see GalleryPickerRequest
 */
abstract class BasePickerRequest<Listener> {

    @Nullable private static Random random;
    private static final int MAX_VALUE = 255;

    private final int requestCode;
    private final Listener listener;

    BasePickerRequest(@NonNull Listener listener) {
        if (random == null)
            random = new Random();
        this.requestCode = random.nextInt(MAX_VALUE);
        this.listener = listener;
    }

    int getRequestCode() {
        return requestCode;
    }

    @NonNull
    Listener getResultListener() {
        return listener;
    }
}