package io.github.hendraanggrian.dispatcher;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;
import java.util.Map;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public final class Dispatcher {

    @Nullable private static DispatcherRequest PENDING_REQUEST;

    @NonNull private final SourceFactory factory;
    @NonNull private final Object source;

    private Dispatcher(@NonNull SourceFactory factory, @NonNull Object source) {
        this.factory = factory;
        this.source = source;
    }

    @NonNull
    public ActivityRequest startActivityForResult(@NonNull Intent intent) {
        return new ActivityRequest(factory, source, intent);
    }

    @NonNull
    public PermissionRequest requestPermissions(@NonNull @PermissionString String... permissions) {
        return new PermissionRequest(factory, source, permissions);
    }

    @NonNull
    public static Dispatcher with(@NonNull Activity activity) {
        return new Dispatcher(SourceFactory.ACTIVITY, activity);
    }

    @NonNull
    public static Dispatcher with(@NonNull Fragment fragment) {
        return new Dispatcher(SourceFactory.FRAGMENT, fragment);
    }

    @NonNull
    public static Dispatcher with(@NonNull android.support.v4.app.Fragment fragment) {
        return new Dispatcher(SourceFactory.SUPPORT_FRAGMENT, fragment);
    }

    public static void onRequestPermissionsResult(final int requestCode, final @NonNull String[] permissions, final @NonNull int[] grantResults) {
        executeRequest(PermissionRequest.class, requestCode, new RequestExecution<PermissionRequest>() {
            @Override
            public void execute(@NonNull PermissionRequest request) {
                if (request.onGranted != null && PermissionUtil.isAllGranted(grantResults))
                    request.onGranted.onGranted(true);
                else if (request.onDenied != null)
                    request.onDenied.onDenied(PermissionUtil.buildMap(permissions, grantResults));
            }
        });
    }

    public static void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        executeRequest(ActivityRequest.class, requestCode, new RequestExecution<ActivityRequest>() {
            @Override
            public void execute(@NonNull ActivityRequest request) {
                if (request.onAny != null)
                    request.onAny.onResult(requestCode, resultCode, data);
                if (request.onOK != null && resultCode == Activity.RESULT_OK)
                    request.onOK.onResult(requestCode, resultCode, data);
                else if (request.onCanceled != null && resultCode == Activity.RESULT_CANCELED)
                    request.onCanceled.onResult(requestCode, resultCode, data);
            }
        });
    }

    static <R extends DispatcherRequest> void queueRequest(R request) {
        PENDING_REQUEST = request;
    }

    static void flushRequest() {
        PENDING_REQUEST = null;
    }

    @SuppressWarnings("unchecked")
    private static <Request extends DispatcherRequest> void executeRequest(@NonNull Class<Request> requestClass, int requestCode, @NonNull RequestExecution<Request> requestExecution) {
        if (PENDING_REQUEST != null && requestClass.isInstance(PENDING_REQUEST) && PENDING_REQUEST.requestCode == requestCode) {
            requestExecution.execute((Request) PENDING_REQUEST);
            flushRequest();
        }
    }

    public interface OnGranted {
        void onGranted(boolean requested) throws SecurityException;
    }

    public interface OnDenied {
        void onDenied(@NonNull Map<String, Boolean> permissions);
    }

    public interface OnShouldShowRationale {
        void onShouldShowRationale(@NonNull DispatcherRequest dispatcher, @NonNull List<String> permissions);
    }

    public interface OnActivityResult {
        void onResult(int requestCode, int resultCode, Intent data);
    }

    private interface RequestExecution<Request extends DispatcherRequest> {
        void execute(@NonNull Request request);
    }
}