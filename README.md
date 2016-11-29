Dispatcher ![android](/art/snippet_android.png)
==========

![logo](/art/logo.png)

Direct replacement of Android `startActivityForResult()`.

```java
Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

new Dispatcher.Builder(this)
    .onOK(data -> imageView.setImageUri(data.getData()))
    .startActivityForResult(intent);
```

Download
--------

```gradle
compile 'io.github.hendraanggrian:dispatcher:0.4.1'
```

Usage
-----

```java
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Dispatcher.Builder(this)
            .onOK(new OnResultListener() {
                @Override
                public void onResult(Intent data) {
                    // do something
                }
            })
            .onCanceled(new OnResultListener() {
                @Override
                public void onResult(Intent data) {
                    // do something
                }
            })
            .startActivityForResult(new Intent(this, NextActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Dispatcher.onActivityResult(requestCode, resultCode, data);
    }
}
```
