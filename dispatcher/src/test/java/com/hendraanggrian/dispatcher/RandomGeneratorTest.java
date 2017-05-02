package com.hendraanggrian.dispatcher;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class RandomGeneratorTest {

    @Test
    public void initializationTest() throws Exception {
        Assert.assertNull(Dispatcher.random);
        Dispatcher.with(new Source() {
            @NonNull
            @Override
            public Context getContext() {
                return null;
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
        Assert.assertNotNull(Dispatcher.random);
        System.gc();
        Assert.assertNull(Dispatcher.random.get());
    }
}