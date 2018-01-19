package com.gogo.festec.example;
import com.example.latte_core.activities.ProxyActivity;
import com.example.latte_core.delegates.LatteDelegate;


public class MainActivity extends ProxyActivity {

    @Override
    public LatteDelegate setRootDelegate() {
        return new ExampleDelegate();
    }





}
