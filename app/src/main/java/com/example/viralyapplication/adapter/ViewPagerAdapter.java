package com.example.viralyapplication.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.viralyapplication.ui.fragment.NewsFeedFragment;
import com.example.viralyapplication.ui.fragment.NotifyFragment;
import com.example.viralyapplication.ui.fragment.ProfileFragment;
import com.example.viralyapplication.ui.fragment.SearchFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return new ProfileFragment();
            case 2:
                return new NotifyFragment();
            case 3:
                return new SearchFragment();
            default:
                return new NewsFeedFragment();
        }

    }

    @Override
    public int getCount() {
        return 4;
    }
}
