package com.example.latte.ec.main.index.search;

import com.alibaba.fastjson.JSONArray;
import com.example.latte_core.ui.recycler.DataConverter;
import com.example.latte_core.ui.recycler.MultipleFields;
import com.example.latte_core.ui.recycler.MultipleItemEntity;
import com.example.latte_core.utils.storage.LattePreference;


import java.util.ArrayList;

/**
 * Created by FANGDINGJIE
 * 2018/2/8.
 */

public class SearchDataConverter extends DataConverter {

    public static final String TAG_SEARCH_HISTORY = "search_history";

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final String jsonStr =
                LattePreference.getCustomAppProfile(TAG_SEARCH_HISTORY);
        if(!jsonStr.equals("")){
            final JSONArray array = JSONArray.parseArray(jsonStr);
            final int size = array.size();
            for(int i=0; i <size ;i++){
                final String hostoryItemText = array.getString(i);
                final MultipleItemEntity entity = MultipleItemEntity.builder()
                        .setItemType(SearchItemType.ITEM_SEARCH)
                        .setField(MultipleFields.TEXT, hostoryItemText)
                        .build();
                ENTITES.add(entity);
            }
        }
        return ENTITES;
    }
}
