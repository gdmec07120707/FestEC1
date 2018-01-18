package com.gogo.festec.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.latte_core.app.Latte;
import com.example.latte_core.app.activities.ProxyActivity;
import com.example.latte_core.app.delegates.LatteDelegate;
import com.example.latte_core.app.net.RestClient;
import com.example.latte_core.app.net.callback.IError;
import com.example.latte_core.app.net.callback.IFailure;
import com.example.latte_core.app.net.callback.ISuccess;

public class MainActivity extends ProxyActivity {

    @Override
    public LatteDelegate setRootDelegate() {
        return new ExampleDelegate();
    }





}
