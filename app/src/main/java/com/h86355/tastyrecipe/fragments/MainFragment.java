package com.h86355.tastyrecipe.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.h86355.tastyrecipe.R;
import com.h86355.tastyrecipe.adapters.IMainFragmentListener;
import com.h86355.tastyrecipe.adapters.RecommendAdapter;
import com.h86355.tastyrecipe.adapters.TodayRecipesAdapter;
import com.h86355.tastyrecipe.databinding.FragmentMainBinding;
import com.h86355.tastyrecipe.models.FeedCollection;
import com.h86355.tastyrecipe.models.RecipeCollection;
import com.h86355.tastyrecipe.utils.DividerItemDecorator;
import com.h86355.tastyrecipe.viewmodels.DetailFragmentViewModel;
import com.h86355.tastyrecipe.viewmodels.MainFragmentViewModel;

import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class MainFragment extends Fragment implements IMainFragmentListener {
    private static final String TAG = "MAIN_FRAGMENT";
    private FragmentMainBinding binding;
    private MainFragmentViewModel mainFragmentViewModel;
    private DetailFragmentViewModel detailFragmentViewModel;
    private TodayRecipesAdapter todayRecipesAdapter;
    private RecommendAdapter recommendAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        Window window = getActivity().getWindow();
        window.setStatusBarColor(getActivity().getResources().getColor(R.color.background, getActivity().getTheme()));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        detailFragmentViewModel = new ViewModelProvider(requireActivity()).get(DetailFragmentViewModel.class);
        mainFragmentViewModel = new ViewModelProvider(requireActivity()).get(MainFragmentViewModel.class);
        initRecyclerView();
        subscribeObservers();
        handleClick();
    }

    private void handleClick() {
        binding.mainBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                                R.anim.enter_from_left, R.anim.exit_to_right)
                        .replace(R.id.layout_main_container, new SearchFragment(), getString(R.string.fragment_search_tag))
                        .addToBackStack(getString(R.string.fragment_search_tag))
                        .commit();
            }
        });

        binding.seeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchFragment searchFragment = new SearchFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean("isLoadMore", true);
                searchFragment.setArguments(bundle);
                getFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                                R.anim.enter_from_left, R.anim.exit_to_right)
                        .replace(R.id.layout_main_container, searchFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void initRecyclerView() {
        todayRecipesAdapter = new TodayRecipesAdapter(getActivity(), this);
        binding.rvTodayRecipe.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        binding.rvTodayRecipe.setAdapter(todayRecipesAdapter);
        binding.rvTodayRecipe.setNestedScrollingEnabled(false);
        todayRecipesAdapter.displayLoading();

        recommendAdapter = new RecommendAdapter(getActivity(), this);
        binding.rvRecommened.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        binding.rvRecommened.setAdapter(recommendAdapter);
        binding.rvRecommened.setNestedScrollingEnabled(false);
        recommendAdapter.displayLoading();

        RecyclerView.ItemDecoration dividerItemDecoration =
                new DividerItemDecorator(ContextCompat.getDrawable(getActivity(), R.drawable.item_divider));
        binding.rvRecommened.addItemDecoration(dividerItemDecoration);
    }

    private void subscribeObservers() {
        mainFragmentViewModel.getTodayRecipes().observe(this, new Observer<List<RecipeCollection>>() {
            @Override
            public void onChanged(List<RecipeCollection> recipeCollections) {
                if (recipeCollections == null) {
                    if (todayRecipesAdapter.getItemViewType(0) != TodayRecipesAdapter.ERROR){
                        todayRecipesAdapter.displayError();
                    }
                } else {
                    todayRecipesAdapter.setRecipes(recipeCollections);
                }
            }
        });

        mainFragmentViewModel.getRecommendedFeeds().observe(getViewLifecycleOwner(), new Observer<List<FeedCollection>>() {
            @Override
            public void onChanged(List<FeedCollection> feedCollections) {
                if (feedCollections == null) {
                    if (recommendAdapter.getItemViewType(0) != RecommendAdapter.ERROR){
                        recommendAdapter.displayError();
                    }
                } else {
                    recommendAdapter.setFeedCollections(feedCollections);
                }
            }
        });

    }

    @Override
    public void onRecipeTodayClick(int position) {
        //Today Click
        detailFragmentViewModel.setSelectedRecipeCollection(todayRecipesAdapter.getSelectedTodayRecipe(position));
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                        R.anim.enter_from_left, R.anim.exit_to_right)
                .replace(R.id.layout_main_container, new DetailFragment(), getString(R.string.fragment_detail_tag))
                .addToBackStack(getString(R.string.fragment_detail_tag))
                .commit();
    }

    @Override
    public void onFeedCollectionClick(int position) {
        //Recommened click
        detailFragmentViewModel.setSelectedFeedCollection(recommendAdapter.getSelectedFeedCollection(position));
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                        R.anim.enter_from_left, R.anim.exit_to_right)
                .replace(R.id.layout_main_container, new FeedListFragment(), getString(R.string.fragment_feeds_tag))
                .addToBackStack(getString(R.string.fragment_feeds_tag))
                .commit();
    }

    @Override
    public void onRefreshClick(String refreshTag) {
        if (refreshTag.equals("today_recipes")) {
            mainFragmentViewModel.getRecipesAPI(0, 5, "", "");
            todayRecipesAdapter.displayLoading();
        } else if (refreshTag.equals("recommened_feeds")) {
            String timeZone = "";
            long timeZoneHour = TimeUnit.HOURS.convert(TimeZone.getDefault().getRawOffset(), TimeUnit.MILLISECONDS);
            if (timeZoneHour < 0 && timeZoneHour > -10) {
                timeZone = "-" + "0" + timeZoneHour + "00";
            } else if (timeZoneHour < 0 && timeZoneHour <= -10) {
                timeZone = "-" + timeZoneHour + "00";
            } else if (timeZoneHour >= 0 && timeZoneHour < 10) {
                timeZone = "+" + "0" + timeZoneHour + "00";
            } else {
                timeZone = "+" + timeZoneHour + "00";
            }
            mainFragmentViewModel.getRecommendedFeedsAPI(0, 1, timeZone, false);
            recommendAdapter.displayLoading();
        }
    }
}
