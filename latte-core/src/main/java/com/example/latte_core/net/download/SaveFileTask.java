package com.example.latte_core.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.latte_core.app.Latte;
import com.example.latte_core.net.callback.IRequest;
import com.example.latte_core.net.callback.ISuccess;
import com.example.latte_core.utils.file.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * Created by mayn on 2018/1/18.
 * 异步任务写入本地文件
 */

public class SaveFileTask extends AsyncTask<Object,Void,File> {

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;

    SaveFileTask(IRequest REQUEST, ISuccess SUCCESS) {
        this.REQUEST = REQUEST;
        this.SUCCESS = SUCCESS;
    }

    @Override
    protected File doInBackground(Object... params) {
        //下载路径
        String downloadDir  = (String) params[0];
        //后缀名
        String extension = (String)params[1];
        //返回体
        final ResponseBody body = (ResponseBody) params[2];
        //名称
        final String name = (String) params[3];
        //获取输入流
        final InputStream is = body.byteStream();
        //判断下载路径是否为空
        if(downloadDir==null || downloadDir.equals("")){
            downloadDir = "down_loads";
        }
        //判断后缀名是否为空
        if(extension==null || extension.equals("")){
            extension = "";
        }
        //判断名称是否为空
        if(name==null){
            //写入本地文件，不指定名称
            return FileUtil.writeToDisk(is,downloadDir,extension.toUpperCase(),extension);
        }else{
            //写入本地文件，指定文件名称
            return FileUtil.writeToDisk(is,downloadDir,name);
        }
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if(SUCCESS!=null){
            SUCCESS.onSuccess(file.getPath());
        }
        if(REQUEST!=null){
            REQUEST.onRequestEnd();
        }
        autoInstallApk(file);
    }

    /**
     * 自动安装
     * @param file
     */
    private void autoInstallApk(File file){
        if(FileUtil.getExtension(file.getPath()).equals("apk")){
            final Intent install = new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
            Latte.getApplicationContext().startActivity(install);
        }
    }
}
