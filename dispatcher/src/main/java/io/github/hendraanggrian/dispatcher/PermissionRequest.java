package io.github.hendraanggrian.dispatcher;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public final class PermissionRequest<Source> extends DispatcherRequest<Source> {

    @NonNull @PermissionString private final String[] permissions;

    @Nullable Dispatcher.OnGranted onGranted;
    @Nullable Dispatcher.OnDenied onDenied;
    @Nullable Dispatcher.OnShouldShowRationale onShouldShowRationale;

    PermissionRequest(@NonNull SourceFactory factory, @NonNull Source source, @NonNull @PermissionString String... permissions) {
        super(factory, source);
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
        Dispatcher.queueRequest(this);
        if (onGranted != null && factory.isGranted(source, permissions)) {
            onGranted.onGranted(false);
            Dispatcher.flushRequest();
        } else if (onShouldShowRationale != null && factory.shouldShowRationale(source, permissions)) {
            onShouldShowRationale.onShouldShowRationale(factory.listOfShouldShowRationale(source, permissions));
        } else {
            factory.requestPermissions(source, permissions, requestCode);
        }
    }
}