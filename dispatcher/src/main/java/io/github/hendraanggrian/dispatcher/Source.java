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
abstract class Source {

    @NonNull
    abstract Context getContext();

    abstract boolean shouldShowRationale(@NonNull @PermissionString String... permissions);

    abstract void startActivityForResult(@NonNull Intent destination, int requestCode);

    abstract void requestPermissions(@NonNull String[] permissions, int requestCode);

    private Source() {
    }

    @NonNull
    List<String> listOfShouldShowRationale(@NonNull @PermissionString String... permissions) {
        List<String> list = new ArrayList<>();
        for (String permission : permissions)
            if (shouldShowRationale(permission))
                list.add(permission);
        return list;
    }

    boolean isPermissionsGranted(@NonNull @PermissionString String... permissions) {
        for (String permission : permissions)
            if (ContextCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        return true;
    }

    static Source valueOf(@NonNull final Activity activity) {
        return new Source() {
            @NonNull
            @Override
            Context getContext() {
                return activity;
            }

            @Override
            boolean shouldShowRationale(@NonNull @PermissionString String... permissions) {
                for (String permission : permissions)
                    if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission))
                        return true;
                return false;
            }

            @Override
            void startActivityForResult(@NonNull Intent destination, int requestCode) {
                activity.startActivityForResult(destination, requestCode);
            }

            @Override
            void requestPermissions(@NonNull String[] permissions, int requestCode) {
                ActivityCompat.requestPermissions(activity, permissions, requestCode);
            }
        };
    }

    static Source valueOf(@NonNull final Fragment fragment) {
        return new Source() {
            @NonNull
            @Override
            Context getContext() {
                return fragment.getActivity();
            }

            @Override
            @RequiresApi(api = Build.VERSION_CODES.M)
            boolean shouldShowRationale(@NonNull @PermissionString String... permissions) {
                for (String permission : permissions)
                    if (fragment.shouldShowRequestPermissionRationale(permission))
                        return true;
                return false;
            }

            @Override
            void startActivityForResult(@NonNull Intent destination, int requestCode) {
                fragment.startActivityForResult(destination, requestCode);
            }

            @Override
            @RequiresApi(api = Build.VERSION_CODES.M)
            void requestPermissions(@NonNull String[] permissions, int requestCode) {
                fragment.requestPermissions(permissions, requestCode);
            }
        };
    }

    static Source valueOf(@NonNull final android.support.v4.app.Fragment fragment) {
        return new Source() {
            @NonNull
            @Override
            Context getContext() {
                return fragment.getContext();
            }

            @Override
            boolean shouldShowRationale(@NonNull @PermissionString String... permissions) {
                for (String permission : permissions)
                    if (fragment.shouldShowRequestPermissionRationale(permission))
                        return true;
                return false;
            }

            @Override
            void startActivityForResult(@NonNull Intent destination, int requestCode) {
                fragment.startActivityForResult(destination, requestCode);
            }

            @Override
            void requestPermissions(@NonNull String[] permissions, int requestCode) {
                fragment.requestPermissions(permissions, requestCode);
            }
        };
    }
}