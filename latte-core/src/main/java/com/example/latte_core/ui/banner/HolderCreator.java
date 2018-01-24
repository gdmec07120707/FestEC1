package com.example.latte_core.ui.banner;

import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

/**
 * Created by mayn on 2018/1/24.
 */

public class HolderCreator implements CBViewHolderCreator<ImageHolder>{
    @Override
    public ImageHolder createHolder() {
        return new ImageHolder();
    }
}
