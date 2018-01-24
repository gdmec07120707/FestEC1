package com.example.latte_core.delegates;

/**
 * Created by mayn on 2018/1/17.
 */

public abstract class LatteDelegate extends PermissionCheckerDelegate {
    public <T extends LatteDelegate> T getParentDelegate(){
        return (T) getParentFragment();
    }

}
