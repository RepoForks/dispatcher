Dispatcher ![android](/art/snippet_android.png)
==========

![logo](/art/logo.png)

Direct replacement of Android `startActivityForResult()`.

```java
Dispatcher.startActivityForResult(activity, intent, new Dispatcher.SimpleOnResultListener() {
    @Override
    public void onOK(Intent data) {
        // do something
    }
});
```

Download
--------

```gradle
compile 'io.github.hendraanggrian:dispatcher:0.3.2'
```

Usage
-----

```java
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Dispatcher.startActivityForResult(MainActivity.this, new GetContentIntent(MimeType.IMAGE_ALL), new Dispatcher.OnResultListener() {
            @Override
            public void onCanceled(Intent data) {
                // do something
            }

            @Override
            public void onOK(Intent data) {
                // do something
            }

            @Override
            public void onUndefined(Intent data, int resultCode) {
                // do something
            }         
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Dispatcher.onActivityResult(requestCode, resultCode, data);
    }
}
```

Intent Collection
-----------------

### GetContentIntent

```java
Intent intent = new GetIntentContent(MimeType.IMAGE_ALL);
Dispatcher.startActivityForResult(activity, intent, new Dispatcher.SimpleOnResultListener() {
    @Override
    public void onOK(Intent data) {
        Uri result = GetContentIntent.extract(data);
    }
});
```

### CaptureImageIntent

In your `AndroidManifest.xml`, list certain permissions and include `FileProvider`:

```xml
<uses-permission android:name="android.permission.CAMERA"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>

<application ...>
    <provider
        android:name="android.support.v4.content.FileProvider"
        android:authorities="com.your.package.fileprovider"
        android:exported="false"
        android:grantUriPermissions="true">
        <meta-data
            android:name="android.support.FILE_PROVIDER_PATHS"
            android:resource="@xml/dispatcher_resource"/>
    </provider>
</application>
```

Notice that you should use the name of your package + `.fileprovider` as the authorities.

Take picture from camera in `Activity` or `Fragment`:

```java
Intent intent = new CaptureImageIntent(context);
Dispatcher.startActivityForResult(activity, intent, new Dispatcher.SimpleOnResultListener() {
    @Override
    public void onOK(Intent data) {
        Uri result = CaptureImageIntent.getResult();
    }
});
```
