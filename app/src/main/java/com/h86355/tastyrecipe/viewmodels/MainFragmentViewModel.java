package com.h86355.tastyrecipe.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.h86355.tastyrecipe.models.FeedCollection;
import com.h86355.tastyrecipe.models.Recipe;
import com.h86355.tastyrecipe.models.RecipeCollection;
import com.h86355.tastyrecipe.repositories.RecipeRepository;

import java.util.List;

public class MainFragmentViewModel extends ViewModel {
    private RecipeRepository repository;

    public MainFragmentViewModel() {
        repository = RecipeRepository.getInstance();
    }

    public LiveData<List<RecipeCollection>> getTodayRecipes() {
        return repository.getTodayRecipes();
    }

    public LiveData<List<FeedCollection>> getRecommendedFeeds() {
        return repository.getRecommendedFeeds();
    }

    public LiveData<List<RecipeCollection>> getSearchRecipes() {
        return repository.getSearchRecipes();
    }

    public void getSearchRecipesAPI(int from, int size, String query, String tag) {
        repository.getSearchRecipesAPI(from, size, query, tag);
    }

    public void getRecipesAPI(int from, int size, String query, String tag) {
        repository.getTodayRecipesAPI(from, size, query, tag);
    }

    public void getRecommendedFeedsAPI(int from, int size, String timeZone, boolean vegetarian){
        repository.getRecommendedFeedsAPI(from, size, timeZone, vegetarian);
    }
}
