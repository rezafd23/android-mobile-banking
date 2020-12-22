package com.example.android_mobile_banking.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.android_mobile_banking.R;
import com.example.android_mobile_banking.constant.AppConstant;
import com.example.android_mobile_banking.ui.helper.SingleActivity;
import com.example.android_mobile_banking.util.Util;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraUtils;
import com.otaliastudios.cameraview.CameraView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class CameraKtpActivity extends SingleActivity implements CameraUtils.BitmapCallback {

    private CameraView cameraView;
    private AppCompatButton btnCamera,btnRetake,btnUseFoto;
    private AppCompatImageView ivPreview;
    private View bottomPreview;
    private Bitmap resultFoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_ktp);
        initView();

//        int MY_PERMISSIONS_REQUEST_CODE=getIntent().getIntExtra("requestCode",0);
//
//
//        if ((checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) || (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))  {
//            Log.v("ERRORRCAMERA", "No camera and storage permission");
//            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_CODE);
//        }

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraView.capturePicture();
            }
        });

        cameraView.addCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(byte[] picture) {
                // Create a bitmap or a file...
                // CameraUtils will read EXIF orientation for you, in a worker thread.
                //CameraUtils.decodeBitmap(picture, CameraActivity.this);
                if (AppConstant.MAX_SIZE_FOTO > 0) {
                    CameraUtils.decodeBitmap(picture, AppConstant.MAX_SIZE_FOTO, AppConstant.MAX_SIZE_FOTO, CameraKtpActivity.this);
                } else {
                    CameraUtils.decodeBitmap(picture, CameraKtpActivity.this);
                }
            }
        });


    }

    private void initView(){
        cameraView = findViewById(R.id.camera_view);
        cameraView.setLifecycleOwner(this);
        btnCamera = findViewById(R.id.btn_camera);
        ivPreview = findViewById(R.id.iv_preview);
        bottomPreview = findViewById(R.id.bottom_preview);
        btnRetake = findViewById(R.id.btn_retake_foto);
        btnUseFoto = findViewById(R.id.btn_use_foto);

    }
    @Override
    protected void onResume(){
        super.onResume();
    }


    public static void navigate(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, CameraKtpActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    private void gotoPreviewMode(Bitmap bitmap) {
        resultFoto = bitmap;
        ivPreview.setImageBitmap(bitmap);

        ivPreview.setVisibility(View.VISIBLE);
        bottomPreview.setVisibility(View.VISIBLE);
        btnCamera.setVisibility(View.GONE);
    }

    private void gotoCameraMode() {
        ivPreview.setVisibility(View.GONE);
        bottomPreview.setVisibility(View.GONE);
        btnCamera.setVisibility(View.VISIBLE);
    }

    public void onUseFoto(View view) {
//        showProgressDialog();
//        Application.getInstance().runInBackground(new Runnable() {
//            @Override
//            public void run() {
                saveAndReturn();
//            }
//        });

    }

    public void onRetakeFoto(View view) {
        gotoCameraMode();
    }

    private void saveAndReturn() {
        try {
            File tempFile = File.createTempFile("EKTP-" + new Date().getTime(), ".jpg",getCacheDir());
            FileOutputStream outStream = new FileOutputStream(tempFile);
            resultFoto.compress(Bitmap.CompressFormat.JPEG, 80, outStream);
            resultFoto = Util.scaleDown(resultFoto,700,true);
            outStream.close();
            resultFoto.recycle();
            String mPath = tempFile.getAbsolutePath();
            Intent returnIntent = new Intent();
            returnIntent.putExtra("foto_file", mPath);
            dismissProgressDialog();
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
            dismissProgressDialog();
        }
    }

    @Override
    public void onBitmapReady(Bitmap bitmap) {
        gotoPreviewMode(bitmap);
    }

}