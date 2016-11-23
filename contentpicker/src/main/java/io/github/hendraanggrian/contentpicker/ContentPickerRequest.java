package io.github.hendraanggrian.contentpicker;

import android.content.Intent;
import android.support.annotation.NonNull;

import java.util.Random;

/**
 * Base picker request used to store request code and listener for {@link android.app.Activity#onActivityResult(int, int, Intent)} is called.
 *
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
final class ContentPickerRequest<Listener> {

    private static Random random;
    private static final int MAX_VALUE = 255;

    private final int requestCode;
    @NonNull private final Listener listener;

    ContentPickerRequest(@NonNull Listener listener) {
        if (random == null)
            random = new Random();
        this.requestCode = random.nextInt(MAX_VALUE);
        this.listener = listener;
    }

    final int getRequestCode() {
        return requestCode;
    }

    @NonNull
    final Listener getResultListener() {
        return listener;
    }
}