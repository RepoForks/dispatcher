package io.github.hendraanggrian.dispatcher.collection;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import io.github.hendraanggrian.dispatcher.R;

import static io.github.hendraanggrian.dispatcher.Dispatcher.TAG;


/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class CaptureImageIntent extends Intent {

    private static Uri result;

    public CaptureImageIntent(@NonNull Context context) {
        super(MediaStore.ACTION_IMAGE_CAPTURE);
        final File tempFile = createTempFile(context);
        if (tempFile == null) {
            Log.d(TAG, "Temp file is null, consult to https://github.com/hendraanggrian/dispatcher for correct configuration");
        } else {
            result = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", tempFile);
            putExtra(MediaStore.EXTRA_OUTPUT, result);
        }
    }

    public static Uri getResult() {
        return result;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static File createTempFile(@NonNull Context context) {
        try {
            final File parent = new File(Environment.getExternalStorageDirectory(), context.getString(R.string.dispatcher_parent));
            if (parent.exists()) {
                Log.d(TAG + " [1/3]", "parent folder already exists, skipping...");
            } else {
                Log.d(TAG + " [1/3]", "parent folder not found, creating...");
                parent.mkdirs();
            }

            final File file = new File(parent, context.getString(R.string.dispatcher_child));
            if (file.exists()) {
                file.delete();
                Log.d(TAG + " [2/3]", "temp file found, deleting...");
            } else {
                Log.d(TAG + " [2/3]", "temp file does not exist, skipping...");
            }

            final boolean isFileCreated = file.createNewFile();
            Log.d(TAG + " [3/3]", isFileCreated ? "temp file created!" : "unable to create file!");
            return file;
        } catch (IOException ignored) {
            return null;
        }
    }
}