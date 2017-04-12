package com.hendraanggrian.dispatcher;

import android.app.Activity;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by hendraanggrian on 2/17/17.
 */
public class RandomGeneratorTest {

    @Test
    public void initializationTest() throws Exception {
        Assert.assertNull(DispatcherRequest.RANDOM);
        new DispatcherRequest(Source.valueOf(new Activity())) {
            @Override
            public void dispatch() {
            }
        };
        Assert.assertNotNull(DispatcherRequest.RANDOM);
        System.gc();
        Assert.assertNull(DispatcherRequest.RANDOM.get());
    }

    @Test
    public void stressTest() throws Exception {
        for (int i = 1; i < 10000; i++) {
            System.out.print(new DispatcherRequest(Source.valueOf(new Activity())) {
                @Override
                public void dispatch() {
                }
            }.requestCode);
            if (i % 100 == 0) {
                System.out.println();
                System.gc();
                System.out.println("garbage collected");
            }
        }
        DispatcherRequest.RANDOM = null;
    }
}