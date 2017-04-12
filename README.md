![logo](/art/logo.png) Dispatcher
=================================
Fluent API in Android to start activity for result and request permissions:
 * Eliminate request codes when starting new Activity and requesting permissions.
 * Put the logic of the result in listeners instead of in `onActivityResult` or `onRequestPermissionsResult`.
 * Java 8 Lambda-friendly.

Download
--------
```gradle
dependencies {
    compile 'com.hendraanggrian:dispatcher:2.3.0'
}
```

Start activity for result
-------------------------
```java
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Dispatcher.with(activity)
            .startActivityForResult(new Intent(context, NextActivity.class))
            .onOK((requestCode, resultCode, data) -> {
                // do something
            })
            .dispatch();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Dispatcher.onActivityResult(requestCode, resultCode, data);
    }
}
```

Request permissions
-------------------
```java
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Dispatcher.with(activity)
            .requestPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .onGranted(requested -> {
                // do something
            })
            .dispatch();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Dispatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
```