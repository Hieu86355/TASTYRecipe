package com.h86355.tastyrecipe.repositories;

import androidx.lifecycle.LiveData;

import com.h86355.tastyrecipe.models.FeedCollection;
import com.h86355.tastyrecipe.models.Recipe;
import com.h86355.tastyrecipe.models.RecipeCollection;
import com.h86355.tastyrecipe.requests.Client;

import java.util.List;

public class RecipeRepository {

    private static RecipeRepository instance;
    private Client client;

    public static RecipeRepository getInstance() {
        if (instance == null) {
            instance = new RecipeRepository();
        }
        return instance;
    }

    public RecipeRepository() {
        client = Client.getInstance();
    }

    public LiveData<List<RecipeCollection>> getTodayRecipes() {
        return client.getTodayRecipes();
    }

    public LiveData<List<FeedCollection>> getRecommendedFeeds() {
        return client.getRecommendedFeeds();
    }

    public LiveData<List<RecipeCollection>> getSearchRecipes() {
        return client.getSearchRecipes();
    }

    public void getSearchRecipesAPI(int from, int size, String query, String tag) {
        client.getSearchRecipesAPI(from, size, query, tag);
    }

    public void getTodayRecipesAPI(int from, int size, String query, String tag) {
        client.getTodayRecipesAPI(from, size, query, tag);
    }

    public void getRecommendedFeedsAPI(int from, int size, String timeZone, boolean vegetarian){
        client.getRecommendedFeedsAPI(from, size, timeZone, vegetarian);
    }
}
