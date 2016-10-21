package io.github.hendraanggrian.imagepicker;

import android.support.annotation.NonNull;

/**
 * Basic {@link BasePickerRequest} with {@link ImagePicker.OnGalleryResultListener} generic type.
 *
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 * @see BasePickerRequest
 */
final class GalleryPickerRequest extends BasePickerRequest<ImagePicker.OnGalleryResultListener> {

    GalleryPickerRequest(@NonNull ImagePicker.OnGalleryResultListener listener) {
        super(listener);
    }
}