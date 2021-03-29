package com.h86355.tastyrecipe.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.h86355.tastyrecipe.databinding.FragmentIngredientsBinding;
import com.h86355.tastyrecipe.models.Recipe;
import com.h86355.tastyrecipe.models.RecipeCollection;
import com.h86355.tastyrecipe.models.Section;
import com.h86355.tastyrecipe.viewmodels.DetailFragmentViewModel;

public class IngredientsFragment extends Fragment {
    private FragmentIngredientsBinding binding;
    private DetailFragmentViewModel detailFragmentViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentIngredientsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        detailFragmentViewModel = new ViewModelProvider(requireActivity()).get(DetailFragmentViewModel.class);
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

    private void subscribeUi(RecipeCollection recipeCollection) {
        if (recipeCollection.getRecipes() == null) {
            int number = 1;
            for (Section section : recipeCollection.getSections()) {
                for (Section.SectionComponent component : section.getComponents()) {
                    binding.textIngredients.append(
                            number + ".  " + component.getRaw_text() + ".\n"
                    );
                    number++;
                }
            }
        } else if (detailFragmentViewModel.getPosition() != -1) {
            int position = detailFragmentViewModel.getPosition();
            int number = 1;
            for (Section section : recipeCollection.getRecipes().get(position).getSections()) {
                for (Section.SectionComponent component : section.getComponents()) {
                    binding.textIngredients.append(
                            number + ".  " + component.getRaw_text() + ".\n"
                    );
                    number++;
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
