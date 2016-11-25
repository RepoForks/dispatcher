package io.github.hendraanggrian.dispatcher.collection;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public final class GetContentIntent extends Intent {

    public GetContentIntent(@NonNull String... mimeTypes) {
        this(false, mimeTypes);
    }

    public GetContentIntent(boolean pickMultiple, @NonNull String... mimeTypes) {
        super(Intent.ACTION_GET_CONTENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2)
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, pickMultiple);

        setType(TextUtils.join(" ", mimeTypes));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
    }
}