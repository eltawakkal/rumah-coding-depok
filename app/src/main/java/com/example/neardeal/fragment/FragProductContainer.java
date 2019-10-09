package com.example.neardeal.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.neardeal.R;
import com.example.neardeal.adapter.PagerProductAdapter;
import com.google.android.material.tabs.TabLayout;

public class FragProductContainer extends Fragment {

    ViewPager pagerProduct;
    TabLayout tabProduct;

    PagerProductAdapter pagerProductAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_product_container, container, false);

        pagerProduct = view.findViewById(R.id.pager_product_container);
        tabProduct = view.findViewById(R.id.tab_product_container);

        pagerProductAdapter = new PagerProductAdapter(getFragmentManager());

        pagerProduct.setAdapter(pagerProductAdapter);

        tabProduct.setupWithViewPager(pagerProduct);

        return view;
    }
}
