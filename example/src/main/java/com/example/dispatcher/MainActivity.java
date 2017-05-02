package com.example.dispatcher;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dispatcher.collection.CaptureImageIntent;
import com.example.dispatcher.collection.GetContentIntent;
import com.example.dispatcher.collection.MimeType;
import com.hendraanggrian.dispatcher.Dispatcher;
import com.hendraanggrian.dispatcher.annotations.Dispatchable;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
@Dispatchable
public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Dispatcher";

    private ViewGroup viewGroup;
    private ImageView imageViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewGroup = (ViewGroup) findViewById(R.id.viewgroup);
        imageViewResult = (ImageView) findViewById(R.id.imageview_result);

        findViewById(R.id.button_camera).setOnClickListener(v -> Dispatcher.with(this)
                .requestPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .dispatch(alreadyGranted -> Dispatcher.with(this)
                                .startActivityForResult(new CaptureImageIntent(MainActivity.this))
                                .dispatch(data -> {
                                    imageViewResult.setImageBitmap(null);
                                    imageViewResult.setImageURI(CaptureImageIntent.getResult());
                                }),
                        (granted, denied) -> Toast.makeText(MainActivity.this, "Permission denied!", Toast.LENGTH_SHORT).show(),
                        (permissions, dispatcher, onGranted, onDenied) -> Snackbar.make(viewGroup, "Permission to camera and storage are required.", Snackbar.LENGTH_INDEFINITE)
                                .setAction("Request", v1 -> dispatcher.dispatch(onGranted, onDenied))
                                .show()));

        findViewById(R.id.button_gallery).setOnClickListener(v -> Dispatcher.with(this)
                .startActivityForResult(new GetContentIntent(MimeType.IMAGE_ALL))
                .dispatch(data -> imageViewResult.setImageURI(GetContentIntent.extract(data)[0])));

        findViewById(R.id.button_gallery_multiple).setOnClickListener(v -> Dispatcher.with(this)
                .startActivityForResult(new GetContentIntent(true, MimeType.IMAGE_ALL))
                .dispatch(data -> {
                    Uri[] results = GetContentIntent.extract(data);
                    Toast.makeText(MainActivity.this, results.length + " images selected, last one used", Toast.LENGTH_SHORT).show();
                    imageViewResult.setImageURI(results[results.length - 1]);
                }));
    }
}