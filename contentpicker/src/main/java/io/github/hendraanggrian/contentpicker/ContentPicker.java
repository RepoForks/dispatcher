package io.github.hendraanggrian.contentpicker;

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
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com
 */
public final class ContentPicker {

    @NonNull private static final Intent INTENT_CAPTURE_IMAGE = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    @NonNull private static final Intent INTENT_CAPTURE_VIDEO = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
    @NonNull private static final Intent INTENT_CONTENT = new Intent(Intent.ACTION_GET_CONTENT);

    @Nullable private static ContentPickerRequest request;
    @Nullable private static Uri captureImageUri;

    private ContentPicker() {

    }

    public static <Requester extends Activity & OnContentResultListener> void pickSingle(@NonNull Requester requester, @NonNull String... mimeTypes) {
        pickSingle(requester, requester, mimeTypes);
    }

    public static <Requester extends Fragment & OnContentResultListener> void pickSingle(@NonNull Requester requester, @NonNull String... mimeTypes) {
        pickSingle(requester, requester, mimeTypes);
    }

    public static void pickSingle(@NonNull Activity requester, @NonNull OnContentResultListener listener, @NonNull String... mimeTypes) {
        request = ContentPickerRequest.newInstance(listener);
        requester.startActivityForResult(createContentIntent(mimeTypes), request.getRequestCode());
    }

    public static void pickSingle(@NonNull Fragment requester, @NonNull OnContentResultListener listener, @NonNull String... mimeTypes) {
        request = ContentPickerRequest.newInstance(listener);
        requester.startActivityForResult(createContentIntent(mimeTypes), request.getRequestCode());
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static <Requester extends Activity & OnContentResultListener> void pickMultiple(@NonNull Requester requester, @NonNull String... mimeTypes) {
        pickMultiple(requester, requester, mimeTypes);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static <Requester extends Fragment & OnContentResultListener> void pickMultiple(@NonNull Requester requester, @NonNull String... mimeTypes) {
        pickMultiple(requester, requester, mimeTypes);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static void pickMultiple(@NonNull Activity requester, @NonNull OnContentResultListener listener, @NonNull String... mimeTypes) {
        request = ContentPickerRequest.newInstance(listener);
        requester.startActivityForResult(createContentIntent(mimeTypes).putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true), request.getRequestCode());
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static void pickMultiple(@NonNull Fragment requester, @NonNull OnContentResultListener listener, @NonNull String... mimeTypes) {
        request = ContentPickerRequest.newInstance(listener);
        requester.startActivityForResult(createContentIntent(mimeTypes).putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true), request.getRequestCode());
    }

    public static <Requester extends Activity & OnContentResultListener> void pickMultipleIfAvailable(@NonNull Requester requester, @NonNull String... mimeTypes) {
        pickMultipleIfAvailable(requester, requester, mimeTypes);
    }

    public static <Requester extends Fragment & OnContentResultListener> void pickMultipleIfAvailable(@NonNull Requester requester, @NonNull String... mimeTypes) {
        pickMultipleIfAvailable(requester, requester, mimeTypes);
    }

    public static void pickMultipleIfAvailable(@NonNull Activity requester, @NonNull OnContentResultListener listener, @NonNull String... mimeTypes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2)
            pickMultiple(requester, listener, mimeTypes);
        else
            pickSingle(requester, listener, mimeTypes);
    }

    public static void pickMultipleIfAvailable(@NonNull Fragment requester, @NonNull OnContentResultListener listener, @NonNull String... mimeTypes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2)
            pickMultiple(requester, listener, mimeTypes);
        else
            pickSingle(requester, listener, mimeTypes);
    }

    public static <Requester extends Activity & OnCaptureResultListener> void dispatchCaptureImage(@NonNull Requester requester) {
        dispatchCaptureImage(requester, requester);
    }

    public static <Requester extends Fragment & OnCaptureResultListener> void dispatchCaptureImage(@NonNull Requester requester) {
        dispatchCaptureImage(requester, requester);
    }

    public static void dispatchCaptureImage(@NonNull Activity requester, @NonNull OnCaptureResultListener listener) {
        request = ContentPickerRequest.newInstance(listener);
        File tempFile = createTempFile(requester);
        if (tempFile == null) {
            Log.d(ContentPicker.class.getSimpleName(), "Temp file is null, consult to https://github.com/hendraanggrian/imagepicker for correct configuration");
        } else {
            captureImageUri = FileProvider.getUriForFile(requester, requester.getPackageName() + ".fileprovider", tempFile);
            requester.startActivityForResult(INTENT_CAPTURE_IMAGE.putExtra(MediaStore.EXTRA_OUTPUT, captureImageUri), request.getRequestCode());
        }
    }

    public static void dispatchCaptureImage(@NonNull Fragment requester, @NonNull OnCaptureResultListener listener) {
        request = ContentPickerRequest.newInstance(listener);
        File tempFile = createTempFile(requester.getContext());
        if (tempFile == null) {
            Log.d(ContentPicker.class.getSimpleName(), "Temp file is null, consult to https://github.com/hendraanggrian/imagepicker for correct configuration");
        } else {
            captureImageUri = FileProvider.getUriForFile(requester.getContext(), requester.getContext().getPackageName() + ".fileprovider", tempFile);
            requester.startActivityForResult(INTENT_CAPTURE_IMAGE.putExtra(MediaStore.EXTRA_OUTPUT, captureImageUri), request.getRequestCode());
        }
    }

    public static <Requester extends Activity & OnCaptureResultListener> void dispatchCaptureVideo(@NonNull Requester requester) {
        dispatchCaptureVideo(requester, requester);
    }

    public static <Requester extends Fragment & OnCaptureResultListener> void dispatchCaptureVideo(@NonNull Requester requester) {
        dispatchCaptureVideo(requester, requester);
    }

    public static void dispatchCaptureVideo(@NonNull Activity requester, @NonNull OnCaptureResultListener listener) {
        request = ContentPickerRequest.newInstance(listener);
        requester.startActivityForResult(INTENT_CAPTURE_VIDEO, request.getRequestCode());
    }

    public static void dispatchCaptureVideo(@NonNull Fragment requester, @NonNull OnCaptureResultListener listener) {
        request = ContentPickerRequest.newInstance(listener);
        requester.startActivityForResult(INTENT_CAPTURE_VIDEO, request.getRequestCode());
    }

    public interface OnCaptureResultListener {

        /**
         * @param uri result of camera intent.
         */
        void onCaptureResult(@NonNull Uri uri);
    }

    public interface OnContentResultListener {

        /**
         * @param results of gallery intent in array of uri.
         */
        void onContentResult(@NonNull Uri... results);
    }

    /**
     * Function that should be called within {@link Activity#onActivityResult(int, int, Intent)} or {@link Fragment#onActivityResult(int, int, Intent)} to automatically triggers the listener.
     *
     * @param requestCode that was previously randomized in {@link ContentPickerRequest} constructor.
     * @param resultCode  ensures that result of intent is correct to proceed.
     * @param data        intent result.
     */
    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (request != null && requestCode == request.getRequestCode() && resultCode == Activity.RESULT_OK) {
            if (request.getResultListener() instanceof OnContentResultListener) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN && data.getClipData() != null) {
                    final ClipData clipData = data.getClipData();
                    final Uri[] results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++)
                        results[i] = clipData.getItemAt(i).getUri();
                    ((OnContentResultListener) request.getResultListener()).onContentResult(results);
                } else {
                    final Uri result = data.getData();
                    ((OnContentResultListener) request.getResultListener()).onContentResult(result);
                }
            } else if (captureImageUri != null) {
                ((OnCaptureResultListener) request.getResultListener()).onCaptureResult(captureImageUri);
            } else {
                ((OnCaptureResultListener) request.getResultListener()).onCaptureResult(data.getData());
            }
            request = null;
        }
    }

    public static boolean isCaptureImageAvailable(@NonNull Context context) {
        return INTENT_CAPTURE_IMAGE.resolveActivity(context.getPackageManager()) != null;
    }

    public static boolean isCaptureVideoAvailable(@NonNull Context context) {
        return INTENT_CAPTURE_VIDEO.resolveActivity(context.getPackageManager()) != null;
    }

    public static boolean isPickerAvailable(@NonNull Context context, @NonNull String... mimeTypes) {
        return createContentIntent(mimeTypes).resolveActivity(context.getPackageManager()) != null;
    }

    @Nullable
    private static File createTempFile(@NonNull Context context) {
        try {
            File parent = new File(Environment.getExternalStorageDirectory(), context.getString(R.string.contentpicker_parent));
            boolean parentCreationResult = parent.mkdirs();
            Log.d(ContentPicker.class.getSimpleName(), parentCreationResult ? "parent folder created." : "parent folder already exists.");

            File file = new File(parent, context.getString(R.string.contentpicker_child));
            boolean fileDeletionResult = file.delete();
            Log.d(ContentPicker.class.getSimpleName(), fileDeletionResult ? "temp file deleted." : "temp file doesn't exist.");
            boolean fileCreationResult = file.createNewFile();
            Log.d(ContentPicker.class.getSimpleName(), fileCreationResult ? "temp file created." : "unable to create file.");
            return file;
        } catch (IOException ignored) {
            return null;
        }
    }

    @NonNull
    private static Intent createContentIntent(@NonNull String... types) {
        if (types.length > 0) {
            final Intent intent = new Intent(INTENT_CONTENT);
            intent.setType(TextUtils.join(" ", types));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                intent.putExtra(Intent.EXTRA_MIME_TYPES, types);
            return intent;
        }
        return INTENT_CONTENT;
    }
}