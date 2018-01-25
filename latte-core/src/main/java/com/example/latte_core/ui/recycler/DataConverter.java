package com.example.latte_core.ui.recycler;

import java.util.ArrayList;

/**
 * Created by mayn on 2018/1/24.
 * 基础数据转换器
 */

public abstract class DataConverter {
    protected  final ArrayList<MultipleItemEntity> ENTITES = new ArrayList<>();
    private String mJsonData = null;

    public abstract ArrayList<MultipleItemEntity> convert();

    public DataConverter setJsonData(String json){
        this.mJsonData = json;
        return this;
    }

    protected String getJsonData(){
        if(mJsonData ==null || mJsonData.isEmpty()){
            throw  new NullPointerException("Data is null");
        }
        return mJsonData;
    }

    public void clearData(){
        ENTITES.clear();
    }
}
