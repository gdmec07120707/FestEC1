package com.example.latte.ec.main.personal.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.latte.ec.R;
import com.example.latte.ec.R2;
import com.example.latte.ec.main.personal.PersonalDelegate;
import com.example.latte_core.delegates.LatteDelegate;
import com.example.latte_core.net.RestClient;
import com.example.latte_core.net.callback.ISuccess;
import com.example.latte_core.ui.recycler.MultipleItemEntity;

import java.util.List;

import butterknife.BindView;

/**
 * Created by FANGDINGJIE
 * 2018/2/2.
 */

public class OrderListDelegate extends LatteDelegate {

    @BindView(R2.id.rv_order_list)
    RecyclerView mRecyclerView;

    private String mType = null;


    @Override
    public Object setLayout() {
        return R.layout.delegate_order_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mType = args.getString(PersonalDelegate.ORDER_TYPE);
        Toast.makeText(getContext(),mType,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder()
                .url("order_list.php")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        final List<MultipleItemEntity> data = new OrderListDataConverter().setJsonData(response).convert();
                        OrderListAdapter mAdapter = new OrderListAdapter(data);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                })
                .build().get();
    }
}
