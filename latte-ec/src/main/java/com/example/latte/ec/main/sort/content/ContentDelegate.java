package com.example.latte.ec.main.sort.content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.example.latte.ec.R;
import com.example.latte.ec.R2;
import com.example.latte_core.delegates.LatteDelegate;
import com.example.latte_core.net.RestClient;
import com.example.latte_core.net.callback.ISuccess;

import java.util.List;

import butterknife.BindView;

/**
 * Created by mayn on 2018/1/24.
 *
 */

public class ContentDelegate extends LatteDelegate {
    /*存入的key*/
    private static final String ARG_CONTENT_ID = "CONTENT_ID";
    /*商品id值*/
    private int mContentId = -1;

    @BindView(R2.id.rv_list_content)
    RecyclerView mRecyclerView = null;

    private List<SectionBean> mData = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            mContentId = args.getInt(ARG_CONTENT_ID);
        }
    }

    public static ContentDelegate newInstance(int contentId) {
        Bundle args = new Bundle();
        args.putInt(ARG_CONTENT_ID, contentId);
        final ContentDelegate delegate = new ContentDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_list_content;
    }

    private void initData() {
        RestClient.builder()
                .url("sort_content_list.php?contentId=" + mContentId)
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        //接受到数据后想到Converter中转化成列别需要的数据格式，传入到适配器中
                        mData = new SectionDataConverter().convert(response);
                        final SectionAdapter sectionAdapter = new SectionAdapter(R.layout.item_section_content,
                                R.layout.item_section_header, mData);
                        mRecyclerView.setAdapter(sectionAdapter);
                    }
                })
                .build().get();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        initData();
    }
}
