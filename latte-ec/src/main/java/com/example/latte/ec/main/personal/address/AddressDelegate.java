package com.example.latte.ec.main.personal.address;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.latte.ec.R;
import com.example.latte.ec.R2;
import com.example.latte_core.delegates.LatteDelegate;
import com.example.latte_core.net.RestClient;
import com.example.latte_core.net.callback.ISuccess;
import com.example.latte_core.ui.recycler.MultipleItemEntity;

import java.util.List;

import butterknife.BindView;

/**
 * Created by FANGDINGJIE
 * 2018/2/6.
 */

public class AddressDelegate extends LatteDelegate implements ISuccess{

    @BindView(R2.id.rv_address)
    RecyclerView recyclerView;

    @Override
    public Object setLayout() {
        return R.layout.delegate_address;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        RestClient.builder()
                .url("address.php")
                .loader(getContext())
                .success(this)
                .build()
                .get();

    }

    @Override
    public void onSuccess(String response) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final List<MultipleItemEntity> data = new AddressDataConvert().setJsonData(response).convert();
        AddressAdapter mAdapter = new AddressAdapter(data);
        recyclerView.setAdapter(mAdapter);


    }
}
