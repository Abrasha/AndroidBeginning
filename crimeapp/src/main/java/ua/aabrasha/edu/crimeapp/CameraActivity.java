package ua.aabrasha.edu.crimeapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by Andrii Abramov on 7/4/16.
 */
public class CameraActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 11;
    private Uri mImageUri = null;

    ImageView takenPicture;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.camera_usage);

        initComponents();

    }

    private void initComponents() {
        Button btnTakePicture = (Button) findViewById(R.id.btnTakePicture);
        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        });

        Button btnShareImage = (Button) findViewById(R.id.btnShareImage);
        btnShareImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                handleShareImage();


            }
        });

        takenPicture = (ImageView) findViewById(R.id.imgPicture);
    }

    private void handleShareImage() {
        if (mImageUri != null){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_STREAM, mImageUri);
            intent.setType("image/jpeg");
            startActivity(Intent.createChooser(intent, "Sent to ..."));
        } else {
            Toast.makeText(CameraActivity.this, "No image taken!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        mImageUri = data.getData();

        Toast.makeText(CameraActivity.this, "Image saved to: " + mImageUri, Toast.LENGTH_SHORT).show();
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageUri);
            takenPicture.setImageBitmap(bitmap);
        } catch (IOException e) {
            Toast.makeText(CameraActivity.this, "Error opening image: " + mImageUri, Toast.LENGTH_SHORT).show();
        }
    }
}
