package io.github.hendraanggrian.dispatcher;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import io.github.hendraanggrian.dispatcher.collection.CaptureImageIntent;
import io.github.hendraanggrian.dispatcher.collection.GetContentIntent;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public final class Dispatcher {

    public static final String TAG = "Dispatcher";

    private static DispatcherRequest request;

    private Dispatcher() {
    }

    public static <Result, Requester extends Activity & OnResultListener<Result>> void startActivityForResult(@NonNull Requester requester, @NonNull Intent intent) {
        startActivityForResult(requester, intent, requester);
    }

    public static <Result> void startActivityForResult(@NonNull Activity requester, @NonNull Intent intent, @NonNull OnResultListener<Result> listener) {
        request = new DispatcherRequest<>(intent, listener);
        requester.startActivityForResult(intent, request.getRequestCode());
    }

    public static <Result, Requester extends Fragment & OnResultListener<Result>> void startActivityForResult(@NonNull Requester requester, @NonNull Intent intent) {
        startActivityForResult(requester, intent, requester);
    }

    public static <Result> void startActivityForResult(@NonNull Fragment requester, @NonNull Intent intent, @NonNull OnResultListener<Result> listener) {
        request = new DispatcherRequest<>(intent, listener);
        requester.startActivityForResult(intent, request.getRequestCode());
    }

    @SuppressWarnings("unchecked")
    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (request != null && request.getRequestCode() == requestCode) {

            final Object[] results;
            if (request.getInitialIntent() instanceof GetContentIntent) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN && data.getClipData() != null) {
                    final ClipData clipData = data.getClipData();
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++)
                        results[i] = clipData.getItemAt(i).getUri();
                } else {
                    results = new Uri[]{data.getData()};
                }

            } else if (request.getInitialIntent() instanceof CaptureImageIntent) {
                results = new Uri[]{((CaptureImageIntent) request.getInitialIntent()).getResult()};

            } else {
                results = null;
            }

            switch (resultCode) {
                case Activity.RESULT_CANCELED:
                    request.getResultListener().onCancelled();
                    break;
                case Activity.RESULT_FIRST_USER:
                    request.getResultListener().onFirstUser(data, results);
                    break;
                case Activity.RESULT_OK:
                    request.getResultListener().onOK(data, results);
                    break;
            }

            request = null;
        }
    }

    @SuppressWarnings("unchecked")
    public interface OnResultListener<Result> {

        void onCancelled();

        void onFirstUser(Intent data, Result... results);

        void onOK(Intent data, Result... results);
    }

    @SuppressWarnings("unchecked")
    public static class SimpleOnResultListener<Result> implements OnResultListener<Result> {

        @Override
        public void onCancelled() {
        }

        @Override
        public void onFirstUser(Intent data, Result... results) {
        }

        @Override
        public void onOK(Intent data, Result... results) {
        }
    }
}