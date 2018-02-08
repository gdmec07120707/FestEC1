package com.example.latte_core.ui.scanner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.example.latte_core.delegates.LatteDelegate;
import com.example.latte_core.utils.callback.CallbackManager;
import com.example.latte_core.utils.callback.CallbackType;
import com.example.latte_core.utils.callback.IGlobalCallback;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * Created by FANGDINGJIE
 * 2018/2/8.
 */

public class ScannerDelegate extends LatteDelegate implements ZBarScannerView.ResultHandler{

    private ScanView mScanView = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(mScanView == null){
            mScanView = new ScanView(getContext());
        }
        mScanView.setAutoFocus(true);
        mScanView.setResultHandler(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mScanView!=null){
            mScanView.startCamera();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mScanView!=null){
            mScanView.stopCameraPreview();
            mScanView.startCamera();
        }
    }

    @Override
    public Object setLayout() {
        return mScanView;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void handleResult(Result result) {
        final IGlobalCallback<String> callback = CallbackManager.getIntance().
                getCallback(CallbackType.ON_SCAN);
        if(callback!=null){
            callback.executeCallback(result.getContents());
        }
        getSupportDelegate().pop();
    }
}
