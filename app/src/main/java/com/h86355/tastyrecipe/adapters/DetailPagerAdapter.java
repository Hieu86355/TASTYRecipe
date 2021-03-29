package com.h86355.tastyrecipe.adapters;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.h86355.tastyrecipe.fragments.IngredientsFragment;
import com.h86355.tastyrecipe.fragments.InstructionsFragment;
import com.h86355.tastyrecipe.fragments.VideoFragment;


public class DetailPagerAdapter extends FragmentStateAdapter {

    public DetailPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new IngredientsFragment();
            case 1:
                return new InstructionsFragment();
            case 2:
                return new VideoFragment();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
