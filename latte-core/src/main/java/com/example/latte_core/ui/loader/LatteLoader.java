package com.example.latte_core.ui.loader;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.example.latte_core.R;
import com.example.latte_core.utils.demen.DimenUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

/**
 * Created by mayn on 2018/1/18.
 */

public class LatteLoader {
    /*缩放比*/
    private static final int LOADER_SIZE_SCALE = 8;
    /*偏移量*/
    private static final int LOADER_OFFSET_SCALE = 10;
    /*统一管理dialog*/
    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();
    /*默认对话框的样式*/
    private static final String DEFAULT_LOADER = LoaderStyle.BallClipRotatePulseIndicator.name();

    public static void showLoading(Context context,Enum<LoaderStyle> type){
        showLoading(context,type.name());
    }

    /**
     * 显示对话框
     * @param context
     * @param type
     */
    public static void showLoading(Context context, String type){
        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);

        final AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.create(type,context);
        dialog.setContentView(avLoadingIndicatorView);

        int deviceWidth = DimenUtil.getScreenWidth();
        int deviceHeight =DimenUtil.getScreenHeight();

        final Window dialogWindow  = dialog.getWindow();
        if(dialogWindow!=null){
            final WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = deviceWidth/LOADER_SIZE_SCALE;
            lp.height = deviceHeight/LOADER_SIZE_SCALE;
            lp.height = lp.height+deviceHeight/LOADER_OFFSET_SCALE;
            lp.gravity = Gravity.CENTER;
        }
        LOADERS.add(dialog);
        dialog.show();
    }

    /**
     * 关闭对话框
     * @param context
     */
    public static void showLoading(Context context) {
        showLoading(context,DEFAULT_LOADER);
    }

    public static void stopLoading(){
        for(AppCompatDialog dialog:LOADERS){
            if(dialog!=null){
                if(dialog.isShowing()){
                    //cancel会调用回掉函数，dismisson只是单纯的关闭
                    dialog.cancel();
                }
            }
        }
    }

}
