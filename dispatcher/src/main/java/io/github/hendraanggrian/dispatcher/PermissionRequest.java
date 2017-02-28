package io.github.hendraanggrian.dispatcher;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public final class PermissionRequest extends DispatcherRequest {

    @NonNull @PermissionString private final String[] permissions;

    @Nullable Dispatcher.OnGranted onGranted;
    @Nullable Dispatcher.OnDenied onDenied;
    @Nullable Dispatcher.OnShouldShowRationale onShouldShowRationale;

    PermissionRequest(@NonNull Source source, @NonNull @PermissionString String... permissions) {
        super(source);
        this.permissions = permissions;
    }

    @NonNull
    public PermissionRequest onGranted(@NonNull Dispatcher.OnGranted onGranted) {
        this.onGranted = onGranted;
        return this;
    }

    @NonNull
    public PermissionRequest onDenied(@NonNull Dispatcher.OnDenied onDenied) {
        this.onDenied = onDenied;
        return this;
    }

    @NonNull
    public PermissionRequest onShouldShowRationale(@NonNull Dispatcher.OnShouldShowRationale onShouldShowRationale) {
        this.onShouldShowRationale = onShouldShowRationale;
        return this;
    }

    @Override
    public void dispatch() {
        if (onGranted != null && source.isPermissionsGranted(permissions)) {
            onGranted.onGranted(false);
            return;
        }

        Dispatcher.queueRequest(this);
        if (onShouldShowRationale != null && source.shouldShowRationale(permissions)) {
            onShouldShowRationale.onShouldShowRationale(new PermissionRequest(source, permissions)
                    .onGranted(onGranted)
                    .onDenied(onDenied), source.listOfShouldShowRationale(permissions));
        } else {
            source.requestPermissions(permissions, requestCode);
        }
    }
}