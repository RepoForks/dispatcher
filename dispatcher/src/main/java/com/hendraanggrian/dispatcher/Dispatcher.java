package com.hendraanggrian.dispatcher;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public final class Dispatcher {

    private static final String TAG = Dispatcher.class.getSimpleName();
    private static SparseArray<Dispatch> pendingRequests;
    private static boolean debug;

    @NonNull private final Source source;

    private Dispatcher(@NonNull Source source) {
        this.source = source;
    }

    @NonNull
    public ActivityDispatch startActivityForResult(@NonNull Class<?> activityClass) {
        return startActivityForResult(new Intent(source.getContext(), activityClass));
    }

    @NonNull
    public ActivityDispatch startActivityForResult(@NonNull Intent intent) {
        return new ActivityDispatch(source, intent);
    }

    @NonNull
    public PermissionDispatch requestPermissions(@NonNull @PermissionString String... permissions) {
        return new PermissionDispatch(source, permissions);
    }

    public static void setDebug(boolean debug) {
        Dispatcher.debug = debug;
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
        executeRequest(PermissionDispatch.class, requestCode, new RequestExecution<PermissionDispatch>() {
            @Override
            public void execute(@NonNull PermissionDispatch request) {
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        if (request.onDenied != null) {
                            List<String> granted = new ArrayList<>();
                            List<String> denied = new ArrayList<>();
                            for (int i = 0; i < permissions.length; i++)
                                if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                                    granted.add(permissions[i]);
                                else
                                    denied.add(permissions[i]);
                            request.onDenied.onDenied(granted, denied);
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
        executeRequest(ActivityDispatch.class, requestCode, new RequestExecution<ActivityDispatch>() {
            @Override
            public void execute(@NonNull ActivityDispatch request) {
                if (request.onAny != null) {
                    request.onAny.onAny(requestCode, resultCode, data);
                }
                if (request.onOK != null && resultCode == Activity.RESULT_OK) {
                    request.onOK.onOK(data);
                } else if (request.onCanceled != null && resultCode == Activity.RESULT_CANCELED) {
                    request.onCanceled.onCanceled(data);
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    private static <R extends Dispatch> void executeRequest(@NonNull Class<R> requestClass, int requestCode, @NonNull RequestExecution<R> requestExecution) {
        if (pendingRequests == null)
            return;
        Dispatch dispatch = pendingRequests.get(requestCode);
        if (dispatch == null) {
            if (debug)
                Log.d(TAG, "MISS: request code " + requestCode + " was not created by Dispatcher");
            return;
        }
        if (requestClass.isInstance(dispatch)) {
            if (debug)
                Log.d(TAG, "HIT: " + dispatch.toString());
            requestExecution.execute((R) dispatch);
            pendingRequests.delete(requestCode);
        }
    }

    static void queueRequest(Dispatch dispatch) {
        if (pendingRequests == null)
            pendingRequests = new SparseArray<>();
        pendingRequests.append(dispatch.requestCode, dispatch);
        if (debug)
            Log.d(TAG, "MARKED: " + dispatch.toString());
    }

    private interface RequestExecution<R extends Dispatch> {
        void execute(@NonNull R request);
    }
}