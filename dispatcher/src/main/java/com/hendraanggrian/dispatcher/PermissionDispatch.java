package com.hendraanggrian.dispatcher;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public final class PermissionDispatch extends Dispatch {

    @NonNull @PermissionString private final String[] permissions;

    @Nullable OnGranted onGranted;
    @Nullable OnDenied onDenied;
    @Nullable OnShouldShowRationale onShouldShowRationale;

    PermissionDispatch(@NonNull Source source, @NonNull @PermissionString String... permissions) {
        super(source);
        this.permissions = permissions;
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
        Dispatcher.queueRequest(this);
        if (onShouldShowRationale != null && source.shouldShowRationale(permissions)) {
            onShouldShowRationale.onShouldShowRationale(this, source.listOfShouldShowRationale(permissions));
        } else {
            source.requestPermissions(permissions, requestCode);
        }
    }

    public interface OnGranted {
        void onGranted(boolean alreadyGranted) throws SecurityException;
    }

    public interface OnDenied {
        void onDenied(@NonNull List<String> granted, @NonNull List<String> denied);
    }

    public interface OnShouldShowRationale {
        void onShouldShowRationale(@NonNull PermissionDispatch dispatcher, @NonNull List<String> permissions);
    }
}