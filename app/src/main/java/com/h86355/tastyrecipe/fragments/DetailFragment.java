package com.h86355.tastyrecipe.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.h86355.tastyrecipe.R;
import com.h86355.tastyrecipe.adapters.DetailPagerAdapter;
import com.h86355.tastyrecipe.databinding.FragmentDetailBinding;
import com.h86355.tastyrecipe.models.Recipe;
import com.h86355.tastyrecipe.models.RecipeCollection;
import com.h86355.tastyrecipe.viewmodels.DetailFragmentViewModel;

public class DetailFragment extends Fragment {
    private static final String TAG = "DETAIL_FRAGMENT";
    private FragmentDetailBinding binding;
    private DetailPagerAdapter pagerAdapter;
    private DetailFragmentViewModel detailFragmentViewModel;
    private final Observer<Recipe> observer = new Observer<Recipe>() {
        @Override
        public void onChanged(Recipe recipe) {
            Log.d("AAA", "onChanged: " + recipe.getName());
        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(inflater, container, false);
        Window window = getActivity().getWindow();
        window.setStatusBarColor(getActivity().getResources().getColor(android.R.color.transparent, getActivity().getTheme()));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        detailFragmentViewModel = new ViewModelProvider(requireActivity()).get(DetailFragmentViewModel.class);
        initToolbar();
        initViewPager();
        subscribeObservers();
    }

    private void subscribeObservers() {
        detailFragmentViewModel.getSelectedRecipeCollection().observe(this, new Observer<RecipeCollection>() {
            @Override
            public void onChanged(RecipeCollection recipeCollection) {
                subscribeUi(recipeCollection);
            }
        });

    }

    private void initViewPager() {
        pagerAdapter = new DetailPagerAdapter(getChildFragmentManager(),
                getLifecycle());
        binding.viewPager.setAdapter(pagerAdapter);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(binding.tabLayout, binding.viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("INGREDIENTS");
                        break;
                    case 1:
                        tab.setText("INSTRUCTIONS");
                        break;
                    case 2:
                        tab.setText("VIDEO");
                        break;

                }
            }
        });
        tabLayoutMediator.attach();

        View child = binding.viewPager.getChildAt(0);
        if (child instanceof RecyclerView) {
            child.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
    }


    private void initToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_white);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        binding.appbarLayout.addOnOffsetChangedListener(new AppBarLayout.BaseOnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) - appBarLayout.getTotalScrollRange() == 0) {
                    //  Collapsed
                    ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_black);
                    Window window = getActivity().getWindow();
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                } else {
                    //Expanded
                    ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_white);
                    Window window = getActivity().getWindow();
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                }
            }
        });

    }

    private void subscribeUi(RecipeCollection recipeCollection) {
        if (recipeCollection.getRecipes() == null) {
            binding.collapsingtoolbarlayout.setTitle(recipeCollection.getName());
            Glide.with(getActivity())
                    .load(recipeCollection.getThumbnail_url())
                    .addListener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            binding.detailImage.setVisibility(View.VISIBLE);
                            return false;
                        }
                    })
                    .placeholder(R.drawable.image_holder)
                    .into(binding.detailImage);
        } else if (detailFragmentViewModel.getPosition() != -1) {
            int selectedPosition = detailFragmentViewModel.getPosition();
            binding.collapsingtoolbarlayout.setTitle(recipeCollection.getRecipes().get(selectedPosition).getName());
            Glide.with(getActivity())
                    .load(recipeCollection.getRecipes().get(selectedPosition).getThumbnail_url())
                    .addListener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            binding.detailImage.setVisibility(View.VISIBLE);
                            return false;
                        }
                    })
                    .placeholder(R.drawable.image_holder)
                    .into(binding.detailImage);

        }
    }

}
