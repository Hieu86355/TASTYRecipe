package com.h86355.tastyrecipe.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

public class FeedCollection {
    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("items")
    @Expose
    private List<RecipeCollection> recipeCollections;

    @SerializedName("item")
    @Expose
    private RecipeCollection recipeCollection;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("min_items")
    @Expose
    private String min_items;

    public FeedCollection(String type, List<RecipeCollection> recipeCollections, RecipeCollection recipeCollection, String name, String min_items) {
        this.type = type;
        this.recipeCollections = recipeCollections;
        this.recipeCollection = recipeCollection;
        this.name = name;
        this.min_items = min_items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FeedCollection() {
    }

    public String getMin_items() {
        return min_items;
    }

    public void setMin_items(String min_items) {
        this.min_items = min_items;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<RecipeCollection> getRecipeCollections() {
        return recipeCollections;
    }

    public void setRecipeCollections(List<RecipeCollection> recipeCollections) {
        this.recipeCollections = recipeCollections;
    }

    public RecipeCollection getRecipeCollection() {
        return recipeCollection;
    }

    public void setRecipeCollection(RecipeCollection recipeCollection) {
        this.recipeCollection = recipeCollection;
    }
}
