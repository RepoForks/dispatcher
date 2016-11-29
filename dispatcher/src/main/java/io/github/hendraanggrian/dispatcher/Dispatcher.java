package io.github.hendraanggrian.dispatcher;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public final class Dispatcher<Requester> {

    public static final String TAG = "Dispatcher";

    private static DispatcherRequest request;

    @NonNull private final Requester requester;

    private Dispatcher(@NonNull Requester requester, @Nullable DispatcherRequest request) {
        this.requester = requester;
        Dispatcher.request = request;
    }

    private void startActivityForResult(@NonNull Class<?> activityClass) {
        if (requester instanceof Activity)
            startActivityForResult(new Intent((Activity) requester, activityClass));
        else if (requester instanceof Fragment)
            startActivityForResult(new Intent(((Fragment) requester).getActivity(), activityClass));
        else if (requester instanceof android.support.v4.app.Fragment)
            startActivityForResult(new Intent(((android.support.v4.app.Fragment) requester).getContext(), activityClass));
    }

    private void startActivityForResult(@NonNull Intent intent) {
        if (requester instanceof Activity)
            ((Activity) requester).startActivityForResult(intent, request.getRequestCode());
        else if (requester instanceof Fragment)
            ((Fragment) requester).startActivityForResult(intent, request.getRequestCode());
        else if (requester instanceof android.support.v4.app.Fragment)
            ((android.support.v4.app.Fragment) requester).startActivityForResult(intent, request.getRequestCode());
    }

    private static void invoke(@Nullable OnResultListener listener, Intent data) {
        if (listener != null)
            listener.onResult(data);
    }

    private static void invoke(@Nullable OnAnyResultListener listener, Intent data, int requestCode) {
        if (listener != null)
            listener.onResult(data, requestCode);
    }

    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (request != null && request.getRequestCode() == requestCode) {
            switch (resultCode) {
                case Activity.RESULT_CANCELED:
                    invoke(request.getCanceledListener(), data);
                    break;
                case Activity.RESULT_OK:
                    invoke(request.getOKListener(), data);
                    break;
                default:
                    invoke(request.getOthersListener(), data, resultCode);
                    break;
            }
            invoke(request.getAnyListener(), data, resultCode);
            request = null;
        }
    }

    public interface OnResultListener {

        void onResult(Intent data);
    }

    public interface OnAnyResultListener {

        void onResult(Intent data, int resultCode);
    }

    public final static class Builder {

        @NonNull private Object requester;
        @Nullable private OnResultListener canceledListener;
        @Nullable private OnResultListener okListener;
        @Nullable private Dispatcher.OnAnyResultListener othersListener;
        @Nullable private Dispatcher.OnAnyResultListener anyListener;

        public Builder(@NonNull Activity requester) {
            this.requester = requester;
        }

        public Builder(@NonNull Fragment requester) {
            this.requester = requester;
        }

        public Builder(@NonNull android.support.v4.app.Fragment requester) {
            this.requester = requester;
        }

        public Builder onCanceled(@NonNull OnResultListener listener) {
            this.canceledListener = listener;
            return this;
        }

        public Builder onOK(@NonNull OnResultListener listener) {
            this.okListener = listener;
            return this;
        }

        public Builder onOthers(@NonNull OnAnyResultListener listener) {
            this.othersListener = listener;
            return this;
        }

        public Builder onAny(@NonNull OnAnyResultListener listener) {
            this.anyListener = listener;
            return this;
        }

        public void startActivityForResult(@NonNull Class<?> activityClass) {
            build().startActivityForResult(activityClass);
        }

        public void startActivityForResult(@NonNull Intent intent) {
            build().startActivityForResult(intent);
        }

        private Dispatcher<?> build() {
            return new Dispatcher<>(requester, new DispatcherRequest(canceledListener, okListener, othersListener, anyListener));
        }
    }
}