package com.example.latte.ec.main.personal.address;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.example.latte.ec.R;
import com.example.latte_core.net.RestClient;
import com.example.latte_core.net.callback.ISuccess;
import com.example.latte_core.ui.recycler.MultipleFields;
import com.example.latte_core.ui.recycler.MultipleItemEntity;
import com.example.latte_core.ui.recycler.MultipleRecyclerAdapter;
import com.example.latte_core.ui.recycler.MultipleViewHolder;

import java.util.List;

/**
 * Created by FANGDINGJIE
 * 2018/2/6.
 */

public    class AddressAdapter extends MultipleRecyclerAdapter {
    protected AddressAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(AddressItemType.ITEM_ADDRESS, R.layout.item_address);
    }

    @Override
    protected void convert(final MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        final String name = entity.getField(MultipleFields.NAME);
        final String phone = entity.getField(AddressItemFields.PHONE);
        final String address = entity.getField(AddressItemFields.ADDRESS);
        final boolean isDefault = entity.getField(MultipleFields.TAG);
        final int id = entity.getField(MultipleFields.ID);

        final AppCompatTextView nameText = holder.getView(R.id.tv_address_name);
        final AppCompatTextView phoneText = holder.getView(R.id.tv_address_phone);
        final AppCompatTextView addressText = holder.getView(R.id.tv_address_address);
        final AppCompatTextView deleteTextView = holder.getView(R.id.tv_address_delete);

        deleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RestClient.builder()
                        .url("address.php")
                        .params("id", id)
                        .success(new ISuccess() {
                            @Override
                            public void onSuccess(String response) {
                                remove(holder.getLayoutPosition());
                            }
                        })
                        .build()
                        .post();
            }
        });

        nameText.setText(name);
        phoneText.setText(phone);
        addressText.setText(address);
    }
}
