package com.hendraanggrian.dispatcher;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseArray;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class Dispatcher {

    private static final String TAG = Dispatcher.class.getSimpleName();
    private static boolean debug;
    @Nullable static WeakReference<Random> random;
    @Nullable static SparseArray<Dispatcher> pendingRequests;

    @NonNull final Source source;
    int requestCode;

    Dispatcher(@NonNull Source source, int requestCode) {
        this.source = source;
        this.requestCode = requestCode;
    }

    private Dispatcher(@NonNull Source source) {
        this.source = source;
        do requestCode = generateRandom();
        while (pendingRequests != null && pendingRequests.get(requestCode) != null);
    }

    @NonNull
    public Dispatcher exclude(int... requestCodes) {
        Arrays.sort(requestCodes);
        while (pendingRequests != null && pendingRequests.get(requestCode) != null && Arrays.binarySearch(requestCodes, requestCode) >= 0)
            requestCode = generateRandom();
        return this;
    }

    @NonNull
    public ActivityDispatcher startActivityForResult(@NonNull Class<?> activityClass) {
        return startActivityForResult(new Intent(source.getContext(), activityClass));
    }

    @NonNull
    public ActivityDispatcher startActivityForResult(@NonNull Intent intent) {
        return new ActivityDispatcher(source, requestCode, intent);
    }

    @NonNull
    public PermissionDispatcher requestPermissions(@NonNull @PermissionString String... permissions) {
        return new PermissionDispatcher(source, requestCode, permissions);
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
        executeRequest(PermissionDispatcher.class, requestCode, new RequestExecution<PermissionDispatcher>() {
            @Override
            public void execute(@NonNull PermissionDispatcher dispatcher) {
                if (grantResults.length == 0)
                    return;
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        if (dispatcher.onDenied != null) {
                            List<String> granted = new ArrayList<>();
                            List<String> denied = new ArrayList<>();
                            for (int i = 0; i < permissions.length; i++)
                                if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                                    granted.add(permissions[i]);
                                else
                                    denied.add(permissions[i]);
                            dispatcher.onDenied.onDenied(granted, denied);
                        }
                        return;
                    }
                }
                if (dispatcher.onGranted != null)
                    dispatcher.onGranted.onGranted(true);
            }
        });
    }

    public static void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        executeRequest(ActivityDispatcher.class, requestCode, new RequestExecution<ActivityDispatcher>() {
            @Override
            public void execute(@NonNull ActivityDispatcher dispatcher) {
                if (dispatcher.onAny != null) {
                    dispatcher.onAny.onAny(requestCode, resultCode, data);
                }
                if (dispatcher.onOK != null && resultCode == Activity.RESULT_OK) {
                    dispatcher.onOK.onOK(data);
                } else if (dispatcher.onCanceled != null && resultCode == Activity.RESULT_CANCELED) {
                    dispatcher.onCanceled.onCanceled(data);
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    private static <D extends Dispatcher> void executeRequest(@NonNull Class<D> requestClass, int requestCode, @NonNull RequestExecution<D> requestExecution) {
        if (pendingRequests == null)
            return;
        Dispatcher dispatcher = pendingRequests.get(requestCode);
        if (dispatcher == null) {
            if (debug)
                Log.d(TAG, "MISS: request code " + requestCode + " was not created by Dispatcher");
            return;
        }
        if (requestClass.isInstance(dispatcher)) {
            if (debug)
                Log.d(TAG, "HIT: " + dispatcher.toString());
            requestExecution.execute((D) dispatcher);
            pendingRequests.delete(requestCode);
        }
    }

    public static void queueRequest(Dispatcher dispatcher) {
        if (pendingRequests == null)
            pendingRequests = new SparseArray<>();
        pendingRequests.put(dispatcher.requestCode, dispatcher);
        if (debug)
            Log.d(TAG, "MARKED: " + dispatcher.toString());
    }

    private interface RequestExecution<D extends Dispatcher> {
        void execute(@NonNull D dispatcher);
    }

    private static int generateRandom() {
        if (random != null && random.get() != null)
            return random.get().nextInt(255);
        random = new WeakReference<>(new Random());
        return generateRandom();
    }
}