package com.example.latte_core.ui.camera;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.utilcode.util.FileUtils;
import com.example.latte_core.R;
import com.example.latte_core.delegates.PermissionCheckerDelegate;
import com.example.latte_core.utils.file.FileUtil;

import java.io.File;

/**
 * Created by FANGDINGJIE
 * 2018/2/5.
 */

public class CameraHandler implements View.OnClickListener {
    private final AlertDialog DIALOG;
    private final PermissionCheckerDelegate DELEGATE;
    public CameraHandler(PermissionCheckerDelegate delegate){
        this.DELEGATE = delegate;
        DIALOG = new AlertDialog.Builder(delegate.getContext()).create();
    }

    final void beginCameraDialog(){
        DIALOG.show();
        final Window window = DIALOG.getWindow();
        window.setContentView(R.layout.dialog_camera_panel);
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.anim_panel_up_from_bottom);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        params.dimAmount = 0.5f;
        window.setAttributes(params);

        window.findViewById(R.id.photodialog_btn_cancel).setOnClickListener(this);
        window.findViewById(R.id.photodialog_btn_take).setOnClickListener(this);
        window.findViewById(R.id.photodialog_btn_native).setOnClickListener(this);
    }

    private String getPhotoName(){
        return FileUtil.getFileNameByTime("IMG","JPG");
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        final String currentPhotoName = getPhotoName();
        final Intent intent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final File tempFile = new File(FileUtil.CAMERA_PHOTO_DIR,currentPhotoName);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
           final ContentValues contentValues = new ContentValues(1);
           contentValues.put(MediaStore.Images.Media.DATA,tempFile.getPath());
           final Uri uri = DELEGATE.getContext().getContentResolver()
                   .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);

           final File realFile = FileUtils.getFileByPath(FileUtil.getRealFilePath(DELEGATE.getContext(),uri));
           final Uri realUri = Uri.fromFile(realFile);
           CameraImageBean.getInstance().setPath(realUri);
           intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        }else{
            final Uri fileUri = Uri.fromFile(tempFile);
            CameraImageBean.getInstance().setPath(fileUri);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,fileUri);
        }
        DELEGATE.startActivityForResult(intent,RequestCodes.TAKE_PHOTO);

    }

    /**
     * 选择相册
     */
    private void pickPhoto() {
        final Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        DELEGATE.startActivityForResult(intent,RequestCodes.PICK_PHOTO);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.photodialog_btn_cancel) {
            DIALOG.cancel();
        } else if (id == R.id.photodialog_btn_take) {
            takePhoto();
            DIALOG.cancel();
        } else if (id == R.id.photodialog_btn_native) {
            pickPhoto();
            DIALOG.cancel();
        }
    }
}
