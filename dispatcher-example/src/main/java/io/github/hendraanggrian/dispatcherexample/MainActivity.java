package io.github.hendraanggrian.dispatcherexample;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import io.github.hendraanggrian.dispatcher.Dispatcher;
import io.github.hendraanggrian.dispatcher.MimeType;
import io.github.hendraanggrian.dispatcher.collection.CaptureImageIntent;
import io.github.hendraanggrian.dispatcher.collection.GetContentIntent;
import io.github.hendraanggrian.permission.Permission;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageViewResult = (ImageView) findViewById(R.id.imageview_result);

        findViewById(R.id.button_camera).setOnClickListener(this);
        findViewById(R.id.button_gallery).setOnClickListener(this);
        findViewById(R.id.button_gallery_multiple).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_camera:
                Permission.request(MainActivity.this, new Permission.OnResultListener() {
                    @Override
                    public void onGranted(boolean alreadyGranted) throws SecurityException {
                        new Dispatcher.Builder(MainActivity.this)
                                .onOK(data -> {
                                    imageViewResult.setImageBitmap(null);
                                    imageViewResult.setImageURI(CaptureImageIntent.getResult());
                                })
                                .onCanceled(data -> Toast.makeText(MainActivity.this, "Canceled!", Toast.LENGTH_SHORT).show())
                                .startActivityForResult(new CaptureImageIntent(MainActivity.this));
                    }

                    @Override
                    public void onDenied(@NonNull List<String> permissionsGranted, @NonNull List<String> permissionsDenied) {
                        Toast.makeText(MainActivity.this, "Permission denied!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onShouldShowRationale(@NonNull List<String> permissions) {
                        Permission.requestAgain(MainActivity.this);
                    }
                }, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                break;
            case R.id.button_gallery:
                new Dispatcher.Builder(MainActivity.this)
                        .onOK(data -> imageViewResult.setImageURI(GetContentIntent.extract(data)[0]))
                        .onCanceled(data -> Toast.makeText(MainActivity.this, "Canceled!", Toast.LENGTH_SHORT).show())
                        .startActivityForResult(new GetContentIntent(MimeType.IMAGE_ALL));
                break;
            case R.id.button_gallery_multiple:
                new Dispatcher.Builder(MainActivity.this)
                        .onOK(data -> {
                            Uri[] results = GetContentIntent.extract(data);
                            Toast.makeText(MainActivity.this, results.length + " images selected, last one used", Toast.LENGTH_SHORT).show();
                            imageViewResult.setImageURI(results[results.length - 1]);
                        })
                        .onCanceled(data -> Toast.makeText(MainActivity.this, "Canceled!", Toast.LENGTH_SHORT).show())
                        .startActivityForResult(new GetContentIntent(true, MimeType.IMAGE_ALL));
                break;
        }
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