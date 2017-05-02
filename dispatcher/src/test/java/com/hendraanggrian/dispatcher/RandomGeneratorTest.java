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
        Assert.assertNull(Dispatch.RANDOM);
        new Dispatch(Source.valueOf(new Activity())) {
            @Override
            public void dispatch() {
            }
        };
        Assert.assertNotNull(Dispatch.RANDOM);
        System.gc();
        Assert.assertNull(Dispatch.RANDOM.get());
    }

    @Test
    public void stressTest() throws Exception {
        for (int i = 1; i < 10000; i++) {
            System.out.print(new Dispatch(Source.valueOf(new Activity())) {
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
        Dispatch.RANDOM = null;
    }
}