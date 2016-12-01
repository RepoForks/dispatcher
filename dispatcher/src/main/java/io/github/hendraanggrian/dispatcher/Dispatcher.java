package io.github.hendraanggrian.dispatcher;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import rx.Observable;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public final class Dispatcher {

    public static final String TAG = "Dispatcher";

    @Nullable private static DispatcherRequest request;

    private Dispatcher() {
    }

    // ACTIVITY

    /**
     * Start an Activity for result with single callback, ignoring result code canceled and undefined.
     *
     * @param origin     origin Activity.
     * @param intent     Intent to start.
     * @param okListener will be triggered when Activity returns result code {@link Activity#RESULT_OK}.
     */
    public static void startActivityForResult(@NonNull Activity origin, @NonNull Intent intent, @Nullable OnOKListener okListener) {
        startActivityForResult(origin, intent, okListener, null, null);
    }

    /**
     * Start an Activity for result with multiple callbacks, ignoring result code undefined.
     *
     * @param origin           origin Activity.
     * @param intent           Intent to start.
     * @param okListener       will be triggered when Activity returns result code {@link Activity#RESULT_OK}.
     * @param canceledListener will be triggered when Activity returns result code {@link Activity#RESULT_CANCELED}.
     */
    public static void startActivityForResult(@NonNull Activity origin, @NonNull Intent intent, @Nullable OnOKListener okListener, @Nullable OnCanceledListener canceledListener) {
        startActivityForResult(origin, intent, okListener, canceledListener, null);
    }

    /**
     * Start an Activity for result with multiple callbacks.
     *
     * @param origin            origin Activity.
     * @param intent            Intent to start.
     * @param okListener        will be triggered when Activity returns result code {@link Activity#RESULT_OK}.
     * @param canceledListener  will be triggered when Activity returns result code {@link Activity#RESULT_CANCELED}.
     * @param undefinedListener will be triggered when Activity returns result code that is not {@link Activity#RESULT_OK} and not {@link Activity#RESULT_CANCELED}.
     */
    public static void startActivityForResult(@NonNull Activity origin, @NonNull Intent intent, @Nullable OnOKListener okListener, @Nullable OnCanceledListener canceledListener, @Nullable OnUndefinedListener undefinedListener) {
        startActivityForResult(origin, intent, combineListeners(okListener, canceledListener, undefinedListener));
    }

    /**
     * Start an Activity for result with single callback.
     *
     * @param origin   origin Activity.
     * @param intent   Intent to start.
     * @param listener single OnResultListener that must override onOK, onCancelled, and onUndefined.
     */
    public static void startActivityForResult(@NonNull Activity origin, @NonNull Intent intent, @Nullable OnResultListener listener) {
        request = new DispatcherRequest(listener);
        origin.startActivityForResult(intent, request.getRequestCode());
    }

    // FRAGMENT

    /**
     * Start an Activity for result with single callback, ignoring result code canceled and undefined.
     *
     * @param origin     origin Fragment.
     * @param intent     Intent to start.
     * @param okListener will be triggered when Activity returns result code {@link Activity#RESULT_OK}.
     */
    public static void startActivityForResult(@NonNull android.app.Fragment origin, @NonNull Intent intent, @Nullable OnOKListener okListener) {
        startActivityForResult(origin, intent, okListener, null, null);
    }

    /**
     * Start an Activity for result with multiple callbacks, ignoring result code undefined.
     *
     * @param origin           origin Fragment.
     * @param intent           Intent to start.
     * @param okListener       will be triggered when Activity returns result code {@link Activity#RESULT_OK}.
     * @param canceledListener will be triggered when Activity returns result code {@link Activity#RESULT_CANCELED}.
     */
    public static void startActivityForResult(@NonNull android.app.Fragment origin, @NonNull Intent intent, @Nullable OnOKListener okListener, @Nullable OnCanceledListener canceledListener) {
        startActivityForResult(origin, intent, okListener, canceledListener, null);
    }

    /**
     * Start an Activity for result with multiple callbacks.
     *
     * @param origin            origin Fragment.
     * @param intent            Intent to start.
     * @param okListener        will be triggered when Activity returns result code {@link Activity#RESULT_OK}.
     * @param canceledListener  will be triggered when Activity returns result code {@link Activity#RESULT_CANCELED}.
     * @param undefinedListener will be triggered when Activity returns result code that is not {@link Activity#RESULT_OK} and not {@link Activity#RESULT_CANCELED}.
     */
    public static void startActivityForResult(@NonNull android.app.Fragment origin, @NonNull Intent intent, @Nullable OnOKListener okListener, @Nullable OnCanceledListener canceledListener, @Nullable OnUndefinedListener undefinedListener) {
        startActivityForResult(origin, intent, combineListeners(okListener, canceledListener, undefinedListener));
    }

    /**
     * Start an Activity for result with single callback.
     *
     * @param origin   origin Fragment.
     * @param intent   Intent to start.
     * @param listener single OnResultListener that must override onOK, onCancelled, and onUndefined.
     */
    public static void startActivityForResult(@NonNull android.app.Fragment origin, @NonNull Intent intent, @Nullable OnResultListener listener) {
        request = new DispatcherRequest(listener);
        origin.startActivityForResult(intent, request.getRequestCode());
    }

    // SUPPORT FRAGMENT

    /**
     * Start an Activity for result with single callback, ignoring result code canceled and undefined.
     *
     * @param origin     origin support Fragment.
     * @param intent     Intent to start.
     * @param okListener will be triggered when Activity returns result code {@link Activity#RESULT_OK}.
     */
    public static void startActivityForResult(@NonNull android.support.v4.app.Fragment origin, @NonNull Intent intent, @Nullable OnOKListener okListener) {
        startActivityForResult(origin, intent, okListener, null, null);
    }

    /**
     * Start an Activity for result with multiple callbacks, ignoring result code undefined.
     *
     * @param origin           origin support Fragment.
     * @param intent           Intent to start.
     * @param okListener       will be triggered when Activity returns result code {@link Activity#RESULT_OK}.
     * @param canceledListener will be triggered when Activity returns result code {@link Activity#RESULT_CANCELED}.
     */
    public static void startActivityForResult(@NonNull android.support.v4.app.Fragment origin, @NonNull Intent intent, @Nullable OnOKListener okListener, @Nullable OnCanceledListener canceledListener) {
        startActivityForResult(origin, intent, okListener, canceledListener, null);
    }

    /**
     * Start an Activity for result with multiple callbacks.
     *
     * @param origin            origin support Fragment.
     * @param intent            Intent to start.
     * @param okListener        will be triggered when Activity returns result code {@link Activity#RESULT_OK}.
     * @param canceledListener  will be triggered when Activity returns result code {@link Activity#RESULT_CANCELED}.
     * @param undefinedListener will be triggered when Activity returns result code that is not {@link Activity#RESULT_OK} and not {@link Activity#RESULT_CANCELED}.
     */
    public static void startActivityForResult(@NonNull android.support.v4.app.Fragment origin, @NonNull Intent intent, @Nullable OnOKListener okListener, @Nullable OnCanceledListener canceledListener, @Nullable OnUndefinedListener undefinedListener) {
        startActivityForResult(origin, intent, combineListeners(okListener, canceledListener, undefinedListener));
    }

    /**
     * Start an Activity for result with single callback.
     *
     * @param origin   origin support Fragment.
     * @param intent   Intent to start.
     * @param listener single OnResultListener that must override onOK, onCancelled, and onUndefined.
     */
    public static void startActivityForResult(@NonNull android.support.v4.app.Fragment origin, @NonNull Intent intent, @Nullable OnResultListener listener) {
        request = new DispatcherRequest(listener);
        origin.startActivityForResult(intent, request.getRequestCode());
    }


    /**
     * Finish an Activity with result code and no data.
     *
     * @param activity   Activity to finish.
     * @param resultCode result code to bring to returning Activity.
     */
    public static void finish(@NonNull Activity activity, int resultCode) {
        activity.setResult(resultCode);
        activity.finish();
    }

    /**
     * Finish an Activity with result code with data.
     *
     * @param activity   Activity to finish.
     * @param resultCode result code to bring to returning Activity.
     * @param data       intent returned from Activity that is started for result.
     */
    public static void finish(@NonNull Activity activity, int resultCode, @NonNull Intent data) {
        activity.setResult(resultCode, data);
        activity.finish();
    }

    /**
     * Static function that triggers listeners registered with startActivityForResult.
     *
     * @param requestCode randomly generated in {@link DispatcherRequest}.
     * @param resultCode  determine which listener to trigger.
     * @param data        intent returned from Activity that is started for result.
     */
    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        Observable.just(Dispatcher.request)
                .filter(request -> request != null)
                .filter(request -> request.getRequestCode() == requestCode)
                .map(DispatcherRequest::getListener)
                .filter(listener -> listener != null)
                .subscribe(listener -> {
                    switch (resultCode) {
                        case Activity.RESULT_CANCELED:
                            listener.onCanceled(data);
                            break;
                        case Activity.RESULT_OK:
                            listener.onOK(data);
                            break;
                        default:
                            listener.onUndefined(data, resultCode);
                            break;
                    }
                }, throwable -> {
                }, () -> request = null);
    }

    @Nullable
    private static OnResultListener combineListeners(@Nullable OnOKListener okListener, @Nullable OnCanceledListener canceledListener, @Nullable OnUndefinedListener undefinedListener) {
        return okListener == null && canceledListener == null && undefinedListener == null ? null : new OnResultListener() {
            @Override
            public void onCanceled(Intent data) {
                if (canceledListener != null)
                    canceledListener.onCanceled(data);
            }

            @Override
            public void onOK(Intent data) {
                if (okListener != null)
                    okListener.onOK(data);
            }

            @Override
            public void onUndefined(Intent data, int resultCode) {
                if (undefinedListener != null)
                    undefinedListener.onUndefined(data, resultCode);
            }
        };
    }

    /**
     * Listener that wish to be notified when the Activity receives Activity result
     * with {@link Activity#RESULT_OK}.
     */
    public interface OnOKListener {

        /**
         * Called when result code is {@link Activity#RESULT_OK}.
         *
         * @param data intent returned from Activity that is started for result.
         */
        void onOK(Intent data);
    }

    /**
     * Listener that wish to be notified when the Activity receives Activity result
     * with {@link Activity#RESULT_CANCELED}.
     */
    public interface OnCanceledListener {

        /**
         * Called when result code is {@link Activity#RESULT_CANCELED}.
         *
         * @param data intent returned from Activity that is started for result.
         */
        void onCanceled(Intent data);
    }

    /**
     * Listener that wish to be notified when the Activity receives Activity result
     * with result code that is not {@link Activity#RESULT_OK} and not {@link Activity#RESULT_CANCELED}.
     */
    public interface OnUndefinedListener {

        /**
         * Called when result code is not {@link Activity#RESULT_OK} and not {@link Activity#RESULT_CANCELED}.
         *
         * @param data       intent returned from Activity that is started for result.
         * @param resultCode result code is not {@link Activity#RESULT_OK} and not {@link Activity#RESULT_CANCELED}.
         */
        void onUndefined(Intent data, int resultCode);
    }

    /**
     * Listener that wish to be notified when the Activity receives Activity result.
     */
    public interface OnResultListener extends OnOKListener, OnCanceledListener, OnUndefinedListener {

    }
}