package com.hendraanggrian.dispatcher;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public final class Dispatcher {

    private static final String TAG = "Dispatcher";
    private static final SparseArray<DispatcherRequest> PENDING_REQUESTS = new SparseArray<>();
    private static boolean DEBUG;

    @NonNull private final Source source;

    private Dispatcher(@NonNull Source source) {
        this.source = source;
    }

    @NonNull
    public ActivityRequest startActivityForResult(@NonNull Class<?> activityClass) {
        return startActivityForResult(new Intent(source.getContext(), activityClass));
    }

    @NonNull
    public ActivityRequest startActivityForResult(@NonNull Intent intent) {
        return new ActivityRequest(source, intent);
    }

    @NonNull
    public PermissionRequest requestPermissions(@NonNull @PermissionString String... permissions) {
        return new PermissionRequest(source, permissions);
    }

    public static void setDebug(boolean debug) {
        Dispatcher.DEBUG = debug;
    }

    @NonNull
    public static Dispatcher with(@NonNull Activity activity) {
        return with(Source.valueOf(activity));
    }

    @NonNull
    public static Dispatcher with(@NonNull Fragment fragment) {
        return with(Source.valueOf(fragment));
    }

    @NonNull
    public static Dispatcher with(@NonNull android.support.v4.app.Fragment fragment) {
        return with(Source.valueOf(fragment));
    }

    @NonNull
    public static Dispatcher with(@NonNull Source source) {
        return new Dispatcher(source);
    }

    public static void onRequestPermissionsResult(final int requestCode, final @NonNull String[] permissions, final @NonNull int[] grantResults) {
        executeRequest(PermissionRequest.class, requestCode, new RequestExecution<PermissionRequest>() {
            @Override
            public void execute(@NonNull PermissionRequest request) {
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        if (request.onDenied != null) {
                            Map<String, Boolean> map = new HashMap<>();
                            for (int i = 0; i < permissions.length; i++)
                                map.put(permissions[i], grantResults[i] == PackageManager.PERMISSION_GRANTED);
                            request.onDenied.onDenied(map);
                        }
                        return;
                    }
                }

                if (request.onGranted != null)
                    request.onGranted.onGranted(true);
            }
        });
    }

    public static void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        executeRequest(ActivityRequest.class, requestCode, new RequestExecution<ActivityRequest>() {
            @Override
            public void execute(@NonNull ActivityRequest request) {
                if (request.onAny != null) {
                    request.onAny.onResult(requestCode, resultCode, data);
                }
                if (request.onOK != null && resultCode == Activity.RESULT_OK) {
                    request.onOK.onResult(requestCode, resultCode, data);
                } else if (request.onCanceled != null && resultCode == Activity.RESULT_CANCELED) {
                    request.onCanceled.onResult(requestCode, resultCode, data);
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    private static <Request extends DispatcherRequest> void executeRequest(@NonNull Class<Request> requestClass, int requestCode, @NonNull RequestExecution<Request> requestExecution) {
        DispatcherRequest request = PENDING_REQUESTS.get(requestCode);
        if (requestClass.isInstance(request)) {
            if (DEBUG)
                Log.d(TAG, "catching request code: " + requestCode);

            requestExecution.execute((Request) request);

            if (DEBUG)
                Log.d(TAG, "Request executed: " + request.toString());

            PENDING_REQUESTS.delete(requestCode);
        }
    }

    static void queueRequest(DispatcherRequest request) {
        PENDING_REQUESTS.append(request.requestCode, request);
        if (DEBUG)
            Log.d(TAG, "Request queued: " + request.toString());
    }

    public interface OnPermissionsGranted {
        void onGranted(boolean requested) throws SecurityException;
    }

    public interface OnPermissionsDenied {
        void onDenied(@NonNull Map<String, Boolean> permissions);
    }

    public interface OnPermissionsShouldShowRationale {
        void onShouldShowRationale(@NonNull DispatcherRequest dispatcher, @NonNull List<String> permissions);
    }

    public interface OnActivityResult {
        void onResult(int requestCode, int resultCode, Intent data);
    }

    private interface RequestExecution<Request extends DispatcherRequest> {
        void execute(@NonNull Request request);
    }
}