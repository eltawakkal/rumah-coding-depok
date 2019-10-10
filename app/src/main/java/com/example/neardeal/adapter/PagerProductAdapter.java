package com.example.neardeal.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.neardeal.fragment.FragDealProduct;
import com.example.neardeal.fragment.FragProduct;

public class PagerProductAdapter extends FragmentPagerAdapter {

    public PagerProductAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {//
        switch (position) {
            case 0:
                return new FragDealProduct();
            case 1:
                return new FragProduct();
        }

        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0: return "Deal";
            case 1: return "Product";
        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
