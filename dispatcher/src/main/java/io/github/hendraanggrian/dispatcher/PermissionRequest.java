package io.github.hendraanggrian.dispatcher;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public final class PermissionRequest extends DispatcherRequest {

    @NonNull @PermissionString private final String[] permissions;

    @Nullable Dispatcher.OnPermissionsGranted onGranted;
    @Nullable Dispatcher.OnPermissionsDenied onDenied;
    @Nullable Dispatcher.OnPermissionsShouldShowRationale onShouldShowRationale;

    PermissionRequest(@NonNull Source source, @NonNull @PermissionString String... permissions) {
        super(source);
        this.permissions = permissions;
    }

    @NonNull
    public PermissionRequest onGranted(@NonNull Dispatcher.OnPermissionsGranted onPermissionsGranted) {
        this.onGranted = onPermissionsGranted;
        return this;
    }

    @NonNull
    public PermissionRequest onDenied(@NonNull Dispatcher.OnPermissionsDenied onPermissionsDenied) {
        this.onDenied = onPermissionsDenied;
        return this;
    }

    @NonNull
    public PermissionRequest onShouldShowRationale(@NonNull Dispatcher.OnPermissionsShouldShowRationale onPermissionsShouldShowRationale) {
        this.onShouldShowRationale = onPermissionsShouldShowRationale;
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