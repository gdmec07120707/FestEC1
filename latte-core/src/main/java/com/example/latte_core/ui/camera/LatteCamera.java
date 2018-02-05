package com.example.latte_core.ui.camera;

import android.net.Uri;

import com.example.latte_core.delegates.PermissionCheckerDelegate;
import com.example.latte_core.utils.file.FileUtil;

/**
 * Created by FANGDINGJIE
 * 2018/2/5.
 */

public class LatteCamera {

    public static Uri createCropFile() {
        return Uri.parse
                (FileUtil.createFile("crop_image",
                        FileUtil.getFileNameByTime("IMG", "jpg")).getPath());
    }

    public static void start(PermissionCheckerDelegate delegate){
        new CameraHandler(delegate).beginCameraDialog();
    }
}
