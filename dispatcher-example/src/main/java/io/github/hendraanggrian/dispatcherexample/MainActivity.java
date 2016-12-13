package io.github.hendraanggrian.dispatcherexample;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import io.github.hendraanggrian.dispatcher.Dispatcher;
import io.github.hendraanggrian.dispatcherexample.collection.MimeType;
import io.github.hendraanggrian.dispatcherexample.collection.CaptureImageIntent;
import io.github.hendraanggrian.dispatcherexample.collection.GetContentIntent;
import io.github.hendraanggrian.permission.Permission;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Dispatcher";

    private ImageView imageViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageViewResult = (ImageView) findViewById(R.id.imageview_result);

        findViewById(R.id.button_camera).setOnClickListener(view ->
                Permission.request(this,
                        requested ->
                                Dispatcher.startActivityForResult(this, new CaptureImageIntent(MainActivity.this), data -> {
                                    imageViewResult.setImageBitmap(null);
                                    imageViewResult.setImageURI(CaptureImageIntent.getResult());
                                }),
                        map -> Toast.makeText(MainActivity.this, "Permission denied!", Toast.LENGTH_SHORT).show(),
                        Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE));

        findViewById(R.id.button_gallery).setOnClickListener(view ->
                Dispatcher.startActivityForResult(this, new GetContentIntent(MimeType.IMAGE_ALL),
                        data -> imageViewResult.setImageURI(GetContentIntent.extract(data)[0])));

        findViewById(R.id.button_gallery_multiple).setOnClickListener(view ->
                Dispatcher.startActivityForResult(this, new GetContentIntent(true, MimeType.IMAGE_ALL),
                        data -> {
                            Uri[] results = GetContentIntent.extract(data);
                            Toast.makeText(MainActivity.this, results.length + " images selected, last one used", Toast.LENGTH_SHORT).show();
                            imageViewResult.setImageURI(results[results.length - 1]);
                        }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Dispatcher.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Permission.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}