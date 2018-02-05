package com.example.latte_core.ui.camera;

import android.net.Uri;

/**
 * Created by FANGDINGJIE
 * 2018/2/5.
 */

public  final   class CameraImageBean   {

    private Uri mPath = null;

    private static final CameraImageBean INSTANCE = new CameraImageBean();

    public static CameraImageBean getInstance(){
        return INSTANCE;
    }

    public Uri getPath() {
        return mPath;
    }

    public void setPath(Uri mPath) {
        this.mPath = mPath;
    }
}
