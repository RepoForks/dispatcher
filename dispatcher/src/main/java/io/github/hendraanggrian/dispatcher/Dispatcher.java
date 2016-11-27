package io.github.hendraanggrian.dispatcher;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public final class Dispatcher {

    public static final String TAG = "Dispatcher";

    private static DispatcherRequest request;

    private Dispatcher() {
    }

    public static <Requester extends Activity & OnResultListener> void startActivityForResult(@NonNull Requester requester, @NonNull Intent intent) {
        startActivityForResult(requester, intent, requester);
    }

    public static void startActivityForResult(@NonNull Activity requester, @NonNull Intent intent, @NonNull OnResultListener listener) {
        request = new DispatcherRequest(listener);
        requester.startActivityForResult(intent, request.getRequestCode());
    }

    public static <Requester extends Fragment & OnResultListener> void startActivityForResult(@NonNull Requester requester, @NonNull Intent intent) {
        startActivityForResult(requester, intent, requester);
    }

    public static void startActivityForResult(@NonNull Fragment requester, @NonNull Intent intent, @NonNull OnResultListener listener) {
        request = new DispatcherRequest(listener);
        requester.startActivityForResult(intent, request.getRequestCode());
    }

    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (request != null && request.getRequestCode() == requestCode) {
            switch (resultCode) {
                case Activity.RESULT_CANCELED:
                    request.getResultListener().onCanceled(data);
                    break;
                case Activity.RESULT_OK:
                    request.getResultListener().onOK(data);
                    break;
                default:
                    request.getResultListener().onUndefined(data, resultCode);
                    break;
            }
            request = null;
        }
    }

    public interface OnResultListener {

        void onCanceled(Intent data);

        void onOK(Intent data);

        void onUndefined(Intent data, int resultCode);
    }

    public static class SimpleOnResultListener implements OnResultListener {

        @Override
        public void onCanceled(Intent data) {
        }

        @Override
        public void onOK(Intent data) {
        }

        @Override
        public void onUndefined(Intent data, int resultCode) {
        }
    }
}