//package com.example.android_mobile_banking.ui.activity;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//
//import com.element.camera.Capture;
//import com.element.camera.ElementFaceCaptureActivity;
//import com.element.common.PermissionUtils;
//import com.example.android_mobile_banking.R;
//import com.example.android_mobile_banking.listener.DialogClickListener;
//import com.example.android_mobile_banking.model.ElementModel;
//import com.example.android_mobile_banking.util.IntentHelper;
//import com.example.android_mobile_banking.util.ProgressDialogHelper;
//import com.example.android_mobile_banking.util.Util;
//import com.example.android_mobile_banking.util.WidgetUtil;
//
//public class CameraFaceElementActivity extends ElementFaceCaptureActivity {
//    private String TAG = CameraFaceElementActivity.class.getName();
//
//
//    private ProgressDialogHelper progressDialogHelper;
//    private int requestCode;
//
//    public static void navigate(Activity activity, int requestCode) {
//        Intent intent = new Intent(activity, CameraFaceElementActivity.class);
//        intent.putExtra("requestCode", requestCode);
//        activity.startActivity(intent);
//    }
//
//    public static void navigate(Activity activity) {
//        Intent intent = new Intent(activity, CameraFaceElementActivity.class);
//        activity.startActivity(intent);
//    }
//
//    @Override
//    protected void onCreate(Bundle bundle) {
//        Log.d("Masuk On Create:", "Ada disini");
//        if (requestCode>0){
//            Log.v("IsiCode",String.valueOf(requestCode));
//        }
//        getIntent().putExtra(EXTRA_USER_APP_ID, getPackageName());
//        getIntent().putExtra(EXTRA_ELEMENT_USER_ID, Util.getData(getApplication(),"username"));
//        getIntent().putExtra(EXTRA_LIVENESS_DETECTION, true);
////        getIntent().putExtra(EXTRA_TUTORIAL, true);
//
//        super.onCreate(bundle);
//
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (!PermissionUtils.isGranted(getBaseContext(), android.Manifest.permission.CAMERA)) {
//            toastMessage("Please grant all permissions in Settings -> Apps");
//            finish();
//        }
//        requestCode = getIntent().getIntExtra("requestCode", 0);
//        Log.d("Camera", "Num " + requestCode);
//
//    }
//
//    @Override
//    protected void onFakeFace() {
//        super.onFakeFace();
//    }
//
//    @SuppressLint("WrongConstant")
//    @Override
//    public void onImageCaptured(Capture[] captures, String resultCode) {
//        try {
//            if (CAPTURE_RESULT_OK.equals(resultCode) || CAPTURE_RESULT_GAZE_OK.equals(resultCode)
//                    || CAPTURE_STATUS_VALID_CAPTURES.equals(resultCode)){
//                if (progressDialogHelper == null) progressDialogHelper = new ProgressDialogHelper();
//                progressDialogHelper.showProgressDialog(this, getString(R.string.processing));
//                ElementModel em = new ElementModel();
//                int i = 0;
//                for (Capture c : captures) {
//                    if (i == 0) {
//                        em.setData1(c.data);
//                        em.setData2(c.data);
//                        em.setTag1(c.tag);
//                        em.setTag2(c.tag);
//                        em.setAppType(requestCode);
//                    } else {
//                        em.setData2(c.data);
//                        em.setTag2(c.tag);
//                        em.setAppType(requestCode);
//                    }
//
//                    i++;
//                }
//                if (em.getTag1() == null) {
//
//                } else if (em.getTag1() != null) {
//                    IntentHelper.remove("element");
//                    IntentHelper.addObjectForKey(em, "element");
//                }
//                CameraFaceConfirmationActivity.navigate(this);
//                progressDialogHelper.dismissProgressDialog(CameraFaceElementActivity.this);
//                finish();
//            }
//            else if (CAPTURE_RESULT_NO_FACE.equals(resultCode) || CAPTURE_RESULT_GAZE_FAILED.equals(resultCode)) {
////                showResult(getString(R.string.capture_failed), R.drawable.icon_focus);
////            progressDialogHelper.dismissProgressDialog(CameraFaceElementActivity.this);
//                Log.d("ErrorFace:","Bukan Wajah");
//                navigate(this);
//                return;
//
//
//            }
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (requestCode == 101) {
//            ApplyDataActivity.navigate(CameraFaceElementActivity.this);
//            finish();
//        } else {
//            WidgetUtil.showDoubleButtonDialog(this, "Tunggu Dulu!", "Kamu akan meninggalkan pengambilan selfie. Apabila kamu tidak mau batalkan, pilih kembali.",
//                    "Tidak, Kembali", new DialogClickListener() {
//                        @Override
//                        public void onClick(Dialog dialog, View view) {
//                            dialog.dismiss();
//                        }
//                    },
//                    "Ya, Batalkan", new DialogClickListener() {
//                        @Override
//                        public void onClick(Dialog dialog, View view) {
//                            ApplyDataActivity.navigate(CameraFaceElementActivity.this);
//                            finish();
//                        }
//                    });
//        }
//
//    }
//}
