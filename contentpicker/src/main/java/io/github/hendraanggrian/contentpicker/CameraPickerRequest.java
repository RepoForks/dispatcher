package io.github.hendraanggrian.contentpicker;

import android.net.Uri;
import android.support.annotation.NonNull;

/**
 * {@link BasePickerRequest} with {@link Picker.OnCameraResultListener} generic type and result storage.
 *
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 * @see BasePickerRequest
 */
final class CameraPickerRequest extends BasePickerRequest<Picker.OnCameraResultListener> {

    private Uri result;

    CameraPickerRequest(@NonNull Picker.OnCameraResultListener listener) {
        super(listener);
    }

    void setResult(@NonNull Uri result) {
        this.result = result;
    }

    Uri getResult() {
        return result;
    }
}