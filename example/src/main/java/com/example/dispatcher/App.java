package com.example.dispatcher;

import android.app.Application;

import com.hendraanggrian.dispatcher.Dispatcher;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Dispatcher.setDebug(true);
    }
}