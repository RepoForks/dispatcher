package io.github.hendraanggrian.dispatcher.collection;

import android.content.Intent;
import android.provider.MediaStore;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public final class CaptureVideoIntent extends Intent {

    public CaptureVideoIntent() {
        super(MediaStore.ACTION_VIDEO_CAPTURE);
    }
}