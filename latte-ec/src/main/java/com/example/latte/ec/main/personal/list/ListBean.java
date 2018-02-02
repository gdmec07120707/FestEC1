package com.example.latte.ec.main.personal.list;

import android.widget.CompoundButton;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.latte_core.delegates.LatteDelegate;

/**
 * Created by FANGDINGJIE
 * 2018/2/2.
 */

public class ListBean implements MultiItemEntity {
    private int mItemType =0;
    private String mImageUrl = null;
    private String mText =null;
    private String mValue = null;
    private int mId = 0;
    private LatteDelegate mDelegate = null;
    private CompoundButton.OnCheckedChangeListener mOnCheckChangeListener;

    public ListBean(int mItemType, String mImageUrl, String mText, String mValue, int mId, LatteDelegate mDelegate, CompoundButton.OnCheckedChangeListener mOnCheckChangeListener) {
        this.mItemType = mItemType;
        this.mImageUrl = mImageUrl;
        this.mText = mText;
        this.mValue = mValue;
        this.mId = mId;
        this.mDelegate = mDelegate;
        this.mOnCheckChangeListener = mOnCheckChangeListener;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getText() {
        if (mText == null) {
            return "";
        }
        return mText;
    }

    public String getValue() {
        if (mValue == null) {
            return "";
        }
        return mValue;
    }

    public int getId() {
        return mId;
    }

    public LatteDelegate getDelegate() {
        return mDelegate;
    }

    public CompoundButton.OnCheckedChangeListener getOnCheckChangeListener() {
        return mOnCheckChangeListener;
    }

    @Override
    public int getItemType() {
        return mItemType;
    }

    public static final class Builder{
        private int id = 0;
        private int itemType = 0;
        private String imageUrl = null;
        private String text = null;
        private String value = null;
        private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = null;
        private LatteDelegate delegate = null;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setItemType(int itemType) {
            this.itemType = itemType;
            return this;
        }

        public Builder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setValue(String value) {
            this.value = value;
            return this;
        }

        public Builder setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
            this.onCheckedChangeListener = onCheckedChangeListener;
            return this;
        }

        public Builder setDelegate(LatteDelegate delegate) {
            this.delegate = delegate;
            return this;
        }

        public ListBean build(){
            return new ListBean(itemType, imageUrl, text, value, id, delegate, onCheckedChangeListener);
        }
    }


}
