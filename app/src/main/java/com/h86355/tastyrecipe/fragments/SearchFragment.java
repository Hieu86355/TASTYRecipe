package com.h86355.tastyrecipe.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.h86355.tastyrecipe.R;
import com.h86355.tastyrecipe.adapters.ISearchListener;
import com.h86355.tastyrecipe.adapters.SearchAdapter;
import com.h86355.tastyrecipe.databinding.FragmentSearchBinding;
import com.h86355.tastyrecipe.models.RecipeCollection;
import com.h86355.tastyrecipe.utils.SearchTags;
import com.h86355.tastyrecipe.viewmodels.DetailFragmentViewModel;
import com.h86355.tastyrecipe.viewmodels.MainFragmentViewModel;

import java.util.List;

import co.lujun.androidtagview.TagView;

public class SearchFragment extends Fragment implements ISearchListener {
    private static final String TAG = "SEARCH_FRAGMENT";
    private FragmentSearchBinding binding;
    private SearchAdapter searchAdapter;
    private MainFragmentViewModel mainFragmentViewModel;
    private DetailFragmentViewModel detailFragmentViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainFragmentViewModel = new ViewModelProvider(requireActivity()).get(MainFragmentViewModel.class);
        detailFragmentViewModel = new ViewModelProvider(requireActivity()).get(DetailFragmentViewModel.class);
        subscribeObserver();
        initSearchView();
        initTags();
        initRecyclerView();
        handleClick();
        Bundle bundle = getArguments();
        if (bundle!= null && bundle.getBoolean("isLoadMore")) {
            mainFragmentViewModel.getSearchRecipesAPI(0, 10,"", "");
            binding.tagView.setVisibility(View.GONE);
            binding.searchRv.setVisibility(View.VISIBLE);
            searchAdapter.displayLoading();
            binding.searchView.clearFocus();
        }
    }

    private void handleClick() {
        binding.searchBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.searchView.isFocused()) {
                    binding.searchView.clearFocus();
                }
                getFragmentManager().popBackStack();
            }
        });
    }

    private void subscribeObserver() {
        mainFragmentViewModel.getSearchRecipes().observe(getViewLifecycleOwner(), new Observer<List<RecipeCollection>>() {
            @Override
            public void onChanged(List<RecipeCollection> recipeCollections) {
                if (recipeCollections == null) {
                    Log.d(TAG, "onChanged: null recipes");
                    if (searchAdapter.getItemViewType(0) != SearchAdapter.ERROR) {
                        binding.tagView.setVisibility(View.GONE);
                        binding.searchRv.setVisibility(View.VISIBLE);
                        searchAdapter.displayError();
                    }
                } else {
                    Log.d(TAG, "onChanged: not null recipes");
                    searchAdapter.setSearchResults(recipeCollections);
                }
            }
        });
    }

    private void initRecyclerView() {
        searchAdapter = new SearchAdapter(getActivity(), this);
        binding.searchRv.setAdapter(searchAdapter);
        binding.searchRv.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
    }

    private void initSearchView() {
        binding.searchView.setFocusable(true);
        binding.searchView.setIconified(false);
        binding.searchView.requestFocusFromTouch();

        binding.searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                binding.searchView.setQueryHint("Search");
                binding.searchRv.setVisibility(View.INVISIBLE);
                binding.tagView.setVisibility(View.VISIBLE);
                return true;
            }
        });

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                binding.searchView.setQueryHint(query);
                binding.tagView.setVisibility(View.GONE);
                binding.searchRv.setVisibility(View.VISIBLE);
                searchAdapter.displayLoading();
                mainFragmentViewModel.getSearchRecipesAPI(0, 10, query, "");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void initTags() {
        binding.tagView.setTags(SearchTags.getTags());
        binding.tagView.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                binding.searchView.setQueryHint(text);
                binding.searchView.clearFocus();
                binding.searchRv.setVisibility(View.VISIBLE);
                binding.tagView.setVisibility(View.GONE);
                searchAdapter.displayLoading();
                mainFragmentViewModel.getSearchRecipesAPI(0, 10, "", text);
            }

            @Override
            public void onTagLongClick(int position, String text) {

            }

            @Override
            public void onSelectedTagDrag(int position, String text) {

            }

            @Override
            public void onTagCrossClick(int position) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onItemClick(int position) {
        RecipeCollection recipeCollection = searchAdapter.getSelectedRecipe(position);
        detailFragmentViewModel.setSelectedRecipeCollection(recipeCollection);
        if (recipeCollection.getRecipes() == null) {
            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                            R.anim.enter_from_left, R.anim.exit_to_right)
                    .replace(R.id.layout_main_container, new DetailFragment())
                    .addToBackStack(null)
                    .commit();
        } else {
            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                            R.anim.enter_from_left, R.anim.exit_to_right)
                    .replace(R.id.layout_main_container, new RecipeListFragment(), getString(R.string.fragment_recipes_tag))
                    .addToBackStack(getString(R.string.fragment_recipes_tag))
                    .commit();
        }
    }

}
