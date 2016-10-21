package io.github.hendraanggrian.imagepickerexample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import io.github.hendraanggrian.imagepicker.ImagePicker;

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
                ImagePicker.dispatchCamera(MainActivity.this, new ImagePicker.OnCameraResultListener() {
                    @Override
                    public void onCameraResult(@NonNull Uri uri) {
                        imageViewResult.setImageBitmap(null);
                        imageViewResult.setImageURI(uri);
                    }
                });
            }
        });

        findViewById(R.id.button_gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.pickGallery(MainActivity.this, new ImagePicker.OnGalleryResultListener() {
                    @Override
                    public void onGalleryResult(@NonNull Uri... results) {
                        imageViewResult.setImageURI(results[0]);
                    }
                });
            }
        });

        findViewById(R.id.button_gallery_multiple).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.pickGalleryMultiple(MainActivity.this, new ImagePicker.OnGalleryResultListener() {
                    @Override
                    public void onGalleryResult(@NonNull Uri... results) {
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
        ImagePicker.onActivityResult(requestCode, resultCode, data);
    }
}