package io.github.hendraanggrian.imagepicker;

import android.net.Uri;
import android.support.annotation.NonNull;

/**
 * {@link BasePickerRequest} with {@link ImagePicker.OnCameraResultListener} generic type and result storage.
 *
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 * @see BasePickerRequest
 */
final class CameraPickerRequest extends BasePickerRequest<ImagePicker.OnCameraResultListener> {

    private Uri result;

    CameraPickerRequest(@NonNull ImagePicker.OnCameraResultListener listener) {
        super(listener);
    }

    void setResult(@NonNull Uri result) {
        this.result = result;
    }

    Uri getResult() {
        return result;
    }
}