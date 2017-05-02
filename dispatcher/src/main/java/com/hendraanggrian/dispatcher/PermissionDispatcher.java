package com.hendraanggrian.dispatcher;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public final class PermissionDispatcher extends Dispatcher {

    @NonNull @PermissionString private final String[] permissions;

    @Nullable OnGranted onGranted;
    @Nullable OnDenied onDenied;
    @Nullable OnShouldShowRationale onShouldShowRationale;

    PermissionDispatcher(@NonNull Source source, int requestCode, @NonNull @PermissionString String... permissions) {
        super(source, requestCode);
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return requestCode + " - " + Arrays.toString(permissions);
    }

    public void dispatch(@NonNull OnGranted onGranted) {
        dispatch(onGranted, null, null);
    }

    public void dispatch(@NonNull OnGranted onGranted, @Nullable OnDenied onDenied) {
        dispatch(onGranted, onDenied, null);
    }

    public void dispatch(@NonNull OnGranted onGranted, @Nullable OnDenied onDenied, @Nullable OnShouldShowRationale onShouldShowRationale) {
        this.onGranted = onGranted;
        this.onDenied = onDenied;
        this.onShouldShowRationale = onShouldShowRationale;
        if (source.isPermissionsGranted(permissions)) {
            onGranted.onGranted(true);
            return;
        }
        if (onShouldShowRationale != null && source.shouldShowRationale(permissions)) {
            onShouldShowRationale.onShouldShowRationale(source.listOfShouldShowRationale(permissions), this, onGranted, onDenied);
            return;
        }
        Dispatcher.queueRequest(this);
        source.requestPermissions(permissions, requestCode);
    }

    public interface OnGranted {
        void onGranted(boolean alreadyGranted) throws SecurityException;
    }

    public interface OnDenied {
        void onDenied(@NonNull List<String> granted, @NonNull List<String> denied);
    }

    public interface OnShouldShowRationale {
        void onShouldShowRationale(@NonNull List<String> permissions, @NonNull PermissionDispatcher dispatcher, @NonNull OnGranted onGranted, @Nullable OnDenied onDenied);
    }
}