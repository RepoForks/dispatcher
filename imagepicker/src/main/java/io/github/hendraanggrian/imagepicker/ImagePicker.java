package io.github.hendraanggrian.imagepicker;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com
 */
public class ImagePicker {

    private static BasePickerRequest request;

    /**
     * Start camera intent. It is recommended to check camera availability first with {@link #isCameraAvailable(Context)}.
     *
     * @param activity current activity.
     * @param listener will be triggered on {@link Activity#onActivityResult(int, int, Intent)}.
     */
    public static void dispatchCamera(@NonNull Activity activity, @NonNull OnCameraResultListener listener) {
        request = new CameraPickerRequest(listener);
        File tempFile = createTempFile(activity);
        if (tempFile == null) {
            Log.d(ImagePicker.class.getSimpleName(), "Temp file is null, consult to https://github.com/hendraanggrian/imagepicker for correct configuration");
        } else {
            ((CameraPickerRequest) request).setResult(FileProvider.getUriForFile(activity, activity.getString(R.string.imagepicker_authorities), tempFile));
            activity.startActivityForResult(createCameraIntent().putExtra(MediaStore.EXTRA_OUTPUT, ((CameraPickerRequest) request).getResult()), request.getRequestCode());
        }
    }

    /**
     * Start camera intent. It is recommended to check camera availability first with {@link #isCameraAvailable(Context)}.
     *
     * @param fragment current fragment.
     * @param listener will be triggered on {@link Fragment#onActivityResult(int, int, Intent)}.
     */
    public static void dispatchCamera(@NonNull Fragment fragment, @NonNull OnCameraResultListener listener) {
        request = new CameraPickerRequest(listener);
        File tempFile = createTempFile(fragment.getContext());
        if (tempFile == null) {
            Log.d(ImagePicker.class.getSimpleName(), "Temp file is null, consult to https://github.com/hendraanggrian/imagepicker for correct configuration");
        } else {
            ((CameraPickerRequest) request).setResult(FileProvider.getUriForFile(fragment.getContext(), fragment.getString(R.string.imagepicker_authorities), tempFile));
            fragment.startActivityForResult(createCameraIntent().putExtra(MediaStore.EXTRA_OUTPUT, ((CameraPickerRequest) request).getResult()), request.getRequestCode());
        }
    }

    /**
     * Start gallery intent to pick single gallery. It is recommended to check gallery availability first with {@link #isGalleryAvailable(Context)}.
     *
     * @param activity current activity.
     * @param listener will be triggered on {@link Activity#onActivityResult(int, int, Intent)}.
     */
    public static void pickGallery(@NonNull Activity activity, @NonNull OnGalleryResultListener listener) {
        request = new GalleryPickerRequest(listener);
        activity.startActivityForResult(createGalleryIntent(), request.getRequestCode());
    }

    /**
     * Start gallery intent to pick single gallery. It is recommended to check gallery availability first with {@link #isGalleryAvailable(Context)}.
     *
     * @param fragment current fragment.
     * @param listener will be triggered on {@link Fragment#onActivityResult(int, int, Intent)}.
     */
    public static void pickGallery(@NonNull Fragment fragment, @NonNull OnGalleryResultListener listener) {
        request = new GalleryPickerRequest(listener);
        fragment.startActivityForResult(createGalleryIntent(), request.getRequestCode());
    }

    /**
     * Start gallery intent to pick multiple gallery on API level 18 and above.
     * When API level is lower than 18, this function picks single image instead.
     * It is recommended to check gallery availability first with {@link #isGalleryAvailable(Context)}.
     *
     * @param activity current activity.
     * @param listener will be triggered on {@link Activity#onActivityResult(int, int, Intent)}.
     */
    public static void pickGalleryMultiple(@NonNull Activity activity, @NonNull OnGalleryResultListener listener) {
        request = new GalleryPickerRequest(listener);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            activity.startActivityForResult(createGalleryIntent().putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true), request.getRequestCode());
        } else {
            activity.startActivityForResult(createGalleryIntent(), request.getRequestCode());
            Log.d(ImagePicker.class.getSimpleName(), "Device with API level lower than 18 cannot pick multiple images, picking single image instead.");
        }
    }

    /**
     * Start gallery intent to pick multiple gallery on API level 18 and above.
     * When API level is lower than 18, this function picks single image instead.
     * It is recommended to check gallery availability first with {@link #isGalleryAvailable(Context)}.
     *
     * @param fragment current fragment.
     * @param listener will be triggered on {@link Fragment#onActivityResult(int, int, Intent)}.
     */
    public static void pickGalleryMultiple(@NonNull Fragment fragment, @NonNull OnGalleryResultListener listener) {
        request = new GalleryPickerRequest(listener);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            fragment.startActivityForResult(createGalleryIntent().putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true), request.getRequestCode());
        } else {
            fragment.startActivityForResult(createGalleryIntent(), request.getRequestCode());
            Log.d(ImagePicker.class.getSimpleName(), "Device with API level lower than 18 cannot pick multiple images, picking single image instead.");
        }
    }

    public interface OnCameraResultListener {

        /**
         * @param uri result of camera intent.
         */
        void onCameraResult(@NonNull Uri uri);
    }

    public interface OnGalleryResultListener {

        /**
         * @param results of gallery intent in array of uri.
         */
        void onGalleryResult(@NonNull Uri... results);
    }

    /**
     * Function that should be called within {@link Activity#onActivityResult(int, int, Intent)} or {@link Fragment#onActivityResult(int, int, Intent)} to automatically triggers the listener.
     *
     * @param requestCode that was previously randomized in {@link BasePickerRequest} constructor.
     * @param resultCode  ensures that result of intent is correct to proceed.
     * @param data        intent result.
     */
    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == request.getRequestCode() && resultCode == Activity.RESULT_OK) {
            if (request instanceof CameraPickerRequest) {
                final Uri result = ((CameraPickerRequest) request).getResult();
                ((CameraPickerRequest) request).getResultListener().onCameraResult(result);
            } else {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    final ClipData clipData = data.getClipData();
                    final Uri[] results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++)
                        results[i] = clipData.getItemAt(i).getUri();
                    ((GalleryPickerRequest) request).getResultListener().onGalleryResult(results);
                } else {
                    final Uri result = data.getData();
                    ((GalleryPickerRequest) request).getResultListener().onGalleryResult(result);
                }
            }
        }
    }

    /**
     * Checks whether the device has camera.
     *
     * @param context any active context.
     * @return true if device has camera installed, false otherwise.
     */
    public static boolean isCameraAvailable(@NonNull Context context) {
        return createCameraIntent().resolveActivity(context.getPackageManager()) != null;
    }

    /**
     * Checks whether the device has gallery.
     *
     * @param context any active context.
     * @return true if device has gallery installed, false otherwise.
     */
    public static boolean isGalleryAvailable(@NonNull Context context) {
        return createGalleryIntent().resolveActivity(context.getPackageManager()) != null;
    }

    @Nullable
    private static File createTempFile(@NonNull Context context) {
        try {
            File parent = new File(Environment.getExternalStorageDirectory(), context.getString(R.string.imagepicker_parent));
            boolean parentCreationResult = parent.mkdirs();
            Log.d(ImagePicker.class.getSimpleName(), parentCreationResult ? "parent folder created." : "parent folder already exists.");

            File file = new File(parent, context.getString(R.string.imagepicker_file));
            boolean fileDeletionResult = file.delete();
            Log.d(ImagePicker.class.getSimpleName(), fileDeletionResult ? "temp file deleted." : "temp file doesn't exist.");
            boolean fileCreationResult = file.createNewFile();
            Log.d(ImagePicker.class.getSimpleName(), fileCreationResult ? "temp file created." : "unable to create file.");
            return file;
        } catch (IOException exc) {
            return null;
        }
    }

    @NonNull
    private static Intent createCameraIntent() {
        return new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    }

    @NonNull
    private static Intent createGalleryIntent() {
        return new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    }
}