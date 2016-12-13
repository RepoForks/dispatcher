![logo](/art/logo.png) Dispatcher
=================================

Direct replacement of Android `startActivityForResult()`.

```java
Intent intent = new Intent(Intent.ACTION_GET_CONTENT).setType("image/*");
Dispatcher.startActivityForResult(this, intent, data -> imageView.setImageURI(data.getData())));
```

Download
--------

```gradle
compile 'io.github.hendraanggrian:dispatcher:1.0.0'
```

Usage
-----

```java
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Dispatcher.startActivityForResult(this, new Intent(this, NextActivity.class), new Dispatcher.OnOK(){
            @Override
            public void onOK(Intent data) {
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
