package io.github.hendraanggrian.dispatcher;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
enum SourceFactory {
    ACTIVITY {
        @NonNull
        @Override
        Context getContext(@NonNull Object origin) {
            return (Activity) origin;
        }

        @Override
        boolean shouldShowRationale(@NonNull Object origin, @NonNull @PermissionString String... permissions) {
            for (String permission : permissions)
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) origin, permission))
                    return true;
            return false;
        }

        @Override
        void startActivityForResult(@NonNull Object origin, @NonNull Intent destination, int requestCode) {
            ((Activity) origin).startActivityForResult(destination, requestCode);
        }

        @Override
        void requestPermissions(@NonNull Object origin, @NonNull String[] permissions, int requestCode) {
            ActivityCompat.requestPermissions(((Activity) origin), permissions, requestCode);
        }
    },
    FRAGMENT {
        @NonNull
        @Override
        Context getContext(@NonNull Object origin) {
            return ((Fragment) origin).getActivity();
        }

        @Override
        @RequiresApi(api = Build.VERSION_CODES.M)
        boolean shouldShowRationale(@NonNull Object origin, @NonNull @PermissionString String... permissions) {
            for (String permission : permissions)
                if (((Fragment) origin).shouldShowRequestPermissionRationale(permission))
                    return true;
            return false;
        }

        @Override
        void startActivityForResult(@NonNull Object origin, @NonNull Intent destination, int requestCode) {
            ((Fragment) origin).startActivityForResult(destination, requestCode);
        }

        @Override
        @RequiresApi(api = Build.VERSION_CODES.M)
        void requestPermissions(@NonNull Object origin, @NonNull String[] permissions, int requestCode) {
            ((Fragment) origin).requestPermissions(permissions, requestCode);
        }
    },
    SUPPORT_FRAGMENT {
        @NonNull
        @Override
        Context getContext(@NonNull Object origin) {
            return ((android.support.v4.app.Fragment) origin).getContext();
        }

        @Override
        boolean shouldShowRationale(@NonNull Object origin, @NonNull @PermissionString String... permissions) {
            for (String permission : permissions)
                if (((android.support.v4.app.Fragment) origin).shouldShowRequestPermissionRationale(permission))
                    return true;
            return false;
        }

        @Override
        void startActivityForResult(@NonNull Object origin, @NonNull Intent destination, int requestCode) {
            ((android.support.v4.app.Fragment) origin).startActivityForResult(destination, requestCode);
        }

        @Override
        void requestPermissions(@NonNull Object origin, @NonNull String[] permissions, int requestCode) {
            ((android.support.v4.app.Fragment) origin).requestPermissions(permissions, requestCode);
        }
    };

    @NonNull
    abstract Context getContext(@NonNull Object origin);

    abstract boolean shouldShowRationale(@NonNull Object origin, @NonNull @PermissionString String... permissions);

    abstract void startActivityForResult(@NonNull Object origin, @NonNull Intent destination, int requestCode);

    abstract void requestPermissions(@NonNull Object origin, @NonNull String[] permissions, int requestCode);

    boolean isGranted(@NonNull Object origin, @NonNull @PermissionString String... permissions) {
        for (String permission : permissions)
            if (ContextCompat.checkSelfPermission(getContext(origin), permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        return true;
    }

    @NonNull
    List<String> listOfShouldShowRationale(@NonNull Object origin, @NonNull @PermissionString String... permissions) {
        final List<String> list = new ArrayList<>();
        for (String permission : permissions)
            if (shouldShowRationale(origin, permission))
                list.add(permission);
        return list;
    }
}