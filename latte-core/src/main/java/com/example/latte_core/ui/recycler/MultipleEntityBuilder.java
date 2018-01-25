package com.example.latte_core.ui.recycler;

import java.util.LinkedHashMap;

/**
 * Created by mayn on 2018/1/24.
 * MultipleEntity建造者
 */

public class MultipleEntityBuilder {
    private static final LinkedHashMap<Object, Object> FIELDS = new LinkedHashMap<>();

    public MultipleEntityBuilder() {
        //先清除掉之前的数据
        FIELDS.clear();
    }

    /**
     * 设置item的类型
     * @param itemType
     * @return
     */
    public final MultipleEntityBuilder setItemType(int itemType) {
        FIELDS.put(MultipleFields.ITEM_TYPE, itemType);
        return this;
    }

    public final MultipleEntityBuilder setField(Object key,Object value){
        FIELDS.put(key, value);
        return this;
    }

    public final MultipleEntityBuilder setFields(LinkedHashMap<?,?> map){
        FIELDS.putAll(map);
        return this;
    }

    public final MultipleItemEntity build(){
        return new MultipleItemEntity(FIELDS);
    }


}
