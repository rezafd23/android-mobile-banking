package com.example.android_mobile_banking.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.android_mobile_banking.R;
import com.example.android_mobile_banking.listener.DialogClickListener;
import com.example.android_mobile_banking.model.ElementModel;
import com.example.android_mobile_banking.ui.helper.SingleActivity;
import com.example.android_mobile_banking.util.IntentHelper;
import com.example.android_mobile_banking.util.WidgetUtil;

import java.io.ByteArrayInputStream;

public class CameraFaceConfirmationActivity extends SingleActivity {

    private ElementModel elementModel;
    private AppCompatImageView ivPhoto;
    private AppCompatButton btnUsePhoto, btnRetakeFoto;
    private Toolbar toolbar;
    private static final int CAMERA_FULL_REQ_CODE_ELEMENT = 101;
    private static final int CAMERA_FULL_REQ_CODE_PRIVY = 102;
    private int appType;
    private Context context;
    private RelativeLayout rel_button;
    private boolean goHome = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_face_confirm);

        try {
            ivPhoto = findViewById(R.id.iv_photo);
            btnUsePhoto = findViewById(R.id.btn_use_photo);
            btnRetakeFoto = findViewById(R.id.btn_retake_foto);
            context = CameraFaceConfirmationActivity.this;
            elementModel = (ElementModel) IntentHelper.getObjectForKey("element");
            toolbar = findViewById(R.id.toolbar);
            rel_button = findViewById(R.id.rel_button);
            toolbar.setTitleTextColor(context.getColor(R.color.dark_blue));
            ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(elementModel.getData1());
            Bitmap bmpTemp = BitmapFactory.decodeStream(arrayInputStream);
            ivPhoto.setImageBitmap(bmpTemp);

//            appType = elementModel.getAppType();
//            if (appType == CAMERA_FULL_REQ_CODE_PRIVY) {
//                btnUsePhoto.setVisibility(View.GONE);
//                btnRetakeFoto.setVisibility(View.GONE);
//                rel_button.getLayoutParams().height = 0;
//                validatePrivyPhoto();
//                toolbar.setTitle("Verifikasi Identitas");
//            } else {
            toolbar.setTitle("Foto Selfie");
            btnRetakeFoto.setVisibility(View.VISIBLE);
            btnUsePhoto.setVisibility(View.VISIBLE);
//            }


        } catch (Exception e) {
            Log.v("ERRORR: ","ERROR CREATE FACE CONFIRM");
            e.printStackTrace();
        }

    }

    public static void navigate(Activity activity) {
        Intent intent = new Intent(activity, CameraFaceConfirmationActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
//        CameraFaceElementActivity.navigate(this, appType);
        finish();
    }

    public void BackCamera(View view) {
//        CameraFaceElementActivity.navigate(this, appType);
        finish();
    }

    public void onRetakePhoto(View view) {
        try {
            IntentHelper.remove("element");
//            CameraFaceElementActivity.navigate(this, appType);
            finish();
        } catch (Exception e) {
            ;
        }
    }


    public void onUsePhoto(View view) {
        WidgetUtil.showDoubleButtonDialog(this, "Tunggu Dulu!", "Kamu akan meninggalkan proses pengambilan fotomu. Apabila anda sudah puas dengan fotomu, pilih lanjutkan.",
                "Ya, Lanjut", new DialogClickListener() {
                    @Override
                    public void onClick(Dialog dialog, View view) {
                        Log.v("Cek3", "Masuk Sini");
                       MainActivity.navigate(CameraFaceConfirmationActivity.this,true);
                    }
                },
                "TIDAK, ULANG", new DialogClickListener() {
                    @Override
                    public void onClick(Dialog dialog, View view) {
                        dialog.dismiss();
                    }
                });
    }
}