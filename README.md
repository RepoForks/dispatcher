TO BE UPDATED
=============

Taking picture from camera can be a lot of hard work for such a simple task thanks to `FileProvider` in newer Android version.
This library is specifically created to do the hard work and keeps it easy on lazy developers like me.
In addition, it also picks single or multiple images from gallery.

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
compile 'io.github.hendraanggrian:dispatcher:0.3.1'
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

Installation
------------

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

Usage
-----

Take picture from camera in `Activity` or `Fragment`:

```java
ContentPicker.dispatchCamera(MainActivity.this, new ContentPicker.OnContentResultListener() {
    @Override
    public void onResult(@NonNull Uri uri) {
        // do what you want with uri
    }
});
```

Pick single or multiple images from gallery in `Activity` or `Fragment`:

```java
ContentPicker.pickGallery(MainActivity.this, new ContentPicker.OnContentResultListener() {
    @Override
    public void onResult(@NonNull Uri... results) {
        // do what you want with uri
    }
});

ContentPicker.pickGalleryMultiple(MainActivity.this, new ContentPicker.OnContentResultListener() {
    @Override
    public void onResult(@NonNull Uri... results) {
        // do what you want with uri
    }
});
```

In both cases, override `onActivityResult()` in `Activity` or `Fragment`:

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    ContentPicker.onActivityResult(requestCode, resultCode, data);
}
```

It is worth mentioning that calling camera or gallery intent will crash if device do not have camera hardware or apps do not have camera and external storage permissions.
To avoid this issue, call `Content.isCameraAvailable(context)` and `ContentPicker.isGalleryAvailable(context)` respectively.