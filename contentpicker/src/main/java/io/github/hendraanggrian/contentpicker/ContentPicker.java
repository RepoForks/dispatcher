package io.github.hendraanggrian.contentpicker;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public final class ContentPicker {

    @NonNull private static final Intent CAMERA_INTENT = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    @NonNull private static final Intent VIDEO_INTENT = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

    public static void pick(@NonNull Activity activity, @NonNull String... mimeTypes) {

    }

    @NonNull
    private static Intent createPickerIntent(@NonNull String... types) {
        final Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(TextUtils.join(" ", types));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            intent.putExtra(Intent.EXTRA_MIME_TYPES, types);
        return intent;
    }
}