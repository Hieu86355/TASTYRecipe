package com.h86355.tastyrecipe.fragments;

import android.os.Bundle;
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
import com.h86355.tastyrecipe.adapters.IRecipesListener;
import com.h86355.tastyrecipe.adapters.RecipesAdapter;
import com.h86355.tastyrecipe.databinding.FragmentRecipeListBinding;
import com.h86355.tastyrecipe.models.Recipe;
import com.h86355.tastyrecipe.models.RecipeCollection;
import com.h86355.tastyrecipe.utils.DividerItemDecorator;
import com.h86355.tastyrecipe.viewmodels.DetailFragmentViewModel;

public class RecipeListFragment extends Fragment implements IRecipesListener {
    private static final String TAG = "RECIPE_LIST_FRAGMENT";
    private FragmentRecipeListBinding binding;
    private DetailFragmentViewModel detailFragmentViewModel;
    private RecipesAdapter recipesAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRecipeListBinding.inflate(inflater, container,false);
        Window window = getActivity().getWindow();
        window.setStatusBarColor(getActivity().getResources().getColor(R.color.background, getActivity().getTheme()));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        detailFragmentViewModel = new ViewModelProvider(requireActivity()).get(DetailFragmentViewModel.class);
        subscribeObservers();
        initRecyclerView();
        handleClick();

    }

    private void handleClick() {
        binding.recipesBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
    }

    private void initRecyclerView() {
        recipesAdapter = new RecipesAdapter(getActivity(), this);
        binding.recipesRv.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        binding.recipesRv.setAdapter(recipesAdapter);

        RecyclerView.ItemDecoration dividerItemDecoration =
                new DividerItemDecorator(ContextCompat.getDrawable(getActivity(), R.drawable.item_divider));
        binding.recipesRv.addItemDecoration(dividerItemDecoration);
    }

    private void subscribeObservers() {
       detailFragmentViewModel.getSelectedRecipeCollection().observe(this, new Observer<RecipeCollection>() {
           @Override
           public void onChanged(RecipeCollection recipeCollection) {
               recipesAdapter.setRecipes(recipeCollection.getRecipes());
               binding.recipesTitle.setText(recipeCollection.getName());
           }
       });
    }

    @Override
    public void onRecipeClick(int position) {
        detailFragmentViewModel.setSelectedRecipePosition(position);
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                        R.anim.enter_from_left, R.anim.exit_to_right)
                .replace(R.id.layout_main_container, new DetailFragment())
                .addToBackStack(null)
                .commit();
    }
}
