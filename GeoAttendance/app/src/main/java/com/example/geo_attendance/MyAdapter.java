package com.example.geo_attendance;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;
    public MyAdapter(Context c, FragmentManager fm, int totalTabs) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {


        Fragment fragment = null;
        if (position == 0) {
            fragment = new Hr();
        } else if (position == 1) {
            fragment = new Employeesignup();
        }

        return fragment;
    }
    @Override
    public int getCount() {
        return totalTabs;
    }
}

