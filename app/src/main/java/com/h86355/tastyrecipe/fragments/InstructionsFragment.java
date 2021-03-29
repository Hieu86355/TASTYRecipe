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

import com.h86355.tastyrecipe.databinding.FragmentInstructionsBinding;
import com.h86355.tastyrecipe.models.Instruction;
import com.h86355.tastyrecipe.models.Recipe;
import com.h86355.tastyrecipe.models.RecipeCollection;
import com.h86355.tastyrecipe.viewmodels.DetailFragmentViewModel;

public class InstructionsFragment extends Fragment {
    private FragmentInstructionsBinding binding;
    private DetailFragmentViewModel detailFragmentViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentInstructionsBinding.inflate(inflater, container, false);
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
            for (Instruction instruction : recipeCollection.getInstructions()) {
                binding.textInstructions.append(
                        instruction.getPosition() + ".  " + instruction.getDisplay_text() + ".\n\n"
                );
            }
        } else if (detailFragmentViewModel.getPosition() != -1) {
            int position = detailFragmentViewModel.getPosition();
            for (Instruction instruction : recipeCollection.getRecipes().get(position).getInstructions()) {
                binding.textInstructions.append(
                        instruction.getPosition() + ".  " + instruction.getDisplay_text() + ".\n\n"
                );
            }
        }
    }
}
