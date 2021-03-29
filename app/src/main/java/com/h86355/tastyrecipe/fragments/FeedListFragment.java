package com.h86355.tastyrecipe.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.h86355.tastyrecipe.R;
import com.h86355.tastyrecipe.adapters.FeedsAdapter;
import com.h86355.tastyrecipe.adapters.IFeedListListener;
import com.h86355.tastyrecipe.databinding.FragmentFeedListBinding;
import com.h86355.tastyrecipe.models.FeedCollection;
import com.h86355.tastyrecipe.models.RecipeCollection;
import com.h86355.tastyrecipe.utils.DividerItemDecorator;
import com.h86355.tastyrecipe.viewmodels.DetailFragmentViewModel;

import java.util.List;

public class FeedListFragment extends Fragment implements IFeedListListener {
    private static final String TAG = "FEED_LIST_FRAGMENT";
    private FragmentFeedListBinding binding;
    private DetailFragmentViewModel detailFragmentViewModel;
    private FeedsAdapter feedsAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFeedListBinding.inflate(inflater, container, false);
        Window window = getActivity().getWindow();
        window.setStatusBarColor(getActivity().getResources().getColor(R.color.background, getActivity().getTheme()));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        detailFragmentViewModel = new ViewModelProvider(requireActivity()).get(DetailFragmentViewModel.class);
        subscibeObservers();
        initRecyclerView();
        handleClick();
    }

    private void handleClick() {
        binding.feedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
    }

    private void initRecyclerView() {
        feedsAdapter = new FeedsAdapter(getActivity(), this);
        binding.feedRv.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        binding.feedRv.setAdapter(feedsAdapter);

        RecyclerView.ItemDecoration dividerItemDecoration =
                new DividerItemDecorator(ContextCompat.getDrawable(getActivity(), R.drawable.item_divider));
        binding.feedRv.addItemDecoration(dividerItemDecoration);
    }

    private void subscibeObservers() {
        detailFragmentViewModel.getSelectedFeedCollection().observe(this, new Observer<FeedCollection>() {
            @Override
            public void onChanged(FeedCollection feedCollection) {
                binding.feedTitle.setText(feedCollection.getName());
                feedsAdapter.setRecipeCollections(feedCollection.getRecipeCollections());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onRecipeCollectionClick(int position) {
        RecipeCollection recipeCollection = feedsAdapter.getSellectedRecipeCollection(position);
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
