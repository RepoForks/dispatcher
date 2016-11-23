package io.github.hendraanggrian.contentpicker;

import android.support.annotation.NonNull;

/**
 * Basic {@link BasePickerRequest} with {@link Picker.OnGalleryResultListener} generic type.
 *
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 * @see BasePickerRequest
 */
final class GalleryPickerRequest extends BasePickerRequest<Picker.OnGalleryResultListener> {

    GalleryPickerRequest(@NonNull Picker.OnGalleryResultListener listener) {
        super(listener);
    }
}