package com.h86355.tastyrecipe.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.h86355.tastyrecipe.models.Recipe;
import com.h86355.tastyrecipe.models.RecipeCollection;

import java.util.List;

public class RecipeResponse {
    @SerializedName("count")
    @Expose
    private int count;

    @SerializedName("results")
    @Expose
    private List<RecipeCollection> recipeCollections;

    public int getCount() {
        return count;
    }

    public List<RecipeCollection> getRecipeCollections() {
        return recipeCollections;
    }
}
