package com.hendraanggrian.dispatcher;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class PendingRequestTest {

    @Test
    public void uniqueKeysTest() throws Exception {
        Dispatcher.setDebug(true);
        for (int i = 0; i < 200; i++) {
            Dispatcher dispatcher = Dispatcher.with(new Source() {
                @NonNull
                @Override
                public Context getContext() {
                    return InstrumentationRegistry.getTargetContext();
                }

                @Override
                public boolean shouldShowRationale(@NonNull @PermissionString String... permissions) {
                    return false;
                }

                @Override
                public void startActivityForResult(@NonNull Intent destination, int requestCode) {

                }

                @Override
                public void requestPermissions(@NonNull String[] permissions, int requestCode) {

                }
            });
            Dispatcher.queueRequest(dispatcher);
            System.out.println(dispatcher.toString());
        }
    }
}