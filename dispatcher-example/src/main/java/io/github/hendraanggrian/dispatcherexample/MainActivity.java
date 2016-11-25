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

public class MainActivity extends AppCompatActivity {

    private ImageView imageViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageViewResult = (ImageView) findViewById(R.id.imageview_result);

        findViewById(R.id.button_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Permission.request(MainActivity.this, new Permission.OnResultListener() {
                    @Override
                    public void onGranted(boolean alreadyGranted) throws SecurityException {
                        Dispatcher.startActivityForResult(MainActivity.this, new CaptureImageIntent(MainActivity.this), new Dispatcher.SimpleOnResultListener<Uri>() {
                            @Override
                            public void onOK(Intent data, Uri... results) {
                                imageViewResult.setImageBitmap(null);
                                imageViewResult.setImageURI(results[0]);
                            }
                        });
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
            }
        });

        findViewById(R.id.button_gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dispatcher.startActivityForResult(MainActivity.this, new GetContentIntent(MimeType.IMAGE_ALL), new Dispatcher.SimpleOnResultListener<Uri>() {
                    @Override
                    public void onOK(Intent data, Uri... results) {
                        imageViewResult.setImageURI(results[0]);
                    }
                });
            }
        });

        findViewById(R.id.button_gallery_multiple).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dispatcher.startActivityForResult(MainActivity.this, new GetContentIntent(true, MimeType.IMAGE_ALL), new Dispatcher.SimpleOnResultListener<Uri>() {
                    @Override
                    public void onOK(Intent data, Uri... results) {
                        Toast.makeText(MainActivity.this, results.length + " images selected, last one used", Toast.LENGTH_SHORT).show();
                        imageViewResult.setImageURI(results[results.length - 1]);
                    }
                });
            }
        });
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