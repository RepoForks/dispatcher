package io.github.hendraanggrian.dispatcher;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple class that process result from onRequestPermissionsResult().
 *
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
final class PermissionUtil {

    static boolean isAllGranted(@NonNull int[] grantResults) {
        for (int grantResult : grantResults)
            if (grantResult != PackageManager.PERMISSION_GRANTED)
                return false;
        return true;
    }

    @NonNull
    static Map<String, Boolean> buildMap(@NonNull @PermissionString String[] permissions, @NonNull int[] grantResults) {
        final Map<String, Boolean> map = new HashMap<>();
        for (int i = 0; i < permissions.length; i++)
            map.put(permissions[i], grantResults[i] == PackageManager.PERMISSION_GRANTED);
        return map;
    }
}