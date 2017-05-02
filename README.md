Dispatcher
==========
Fluent API in Android to start activity for result and request permissions:
 * Eliminate request codes when starting new Activity and requesting permissions.
 * Put the logic of the result in listeners instead of in `onActivityResult` or `onRequestPermissionsResult`.
 * Java 8 Lambda-friendly.

Download
--------
```gradle
dependencies {
    compile 'com.hendraanggrian:dispatcher:3.0.3'
    annotationProcessor 'com.hendraanggrian:dispatcher-compiler:3.0.3'
}
```

Start activity for result
-------------------------
```java
@Dispatchable
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Dispatcher.with(this)
            .startActivityForResult(new Intent(context, NextActivity.class))
            .dispatch((requestCode, resultCode, data) -> {
                // do something
            });
    }
}
```

Request permissions
-------------------
```java
@Dispatchable
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Dispatcher.with(this)
            .requestPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .dispatch(alreadyGranted -> {
                // granted
            }, (granted, denied) -> {
                // denied
            });
    }
}
```