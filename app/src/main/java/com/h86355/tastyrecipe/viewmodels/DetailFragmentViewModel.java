package com.h86355.tastyrecipe.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.h86355.tastyrecipe.models.FeedCollection;
import com.h86355.tastyrecipe.models.Recipe;
import com.h86355.tastyrecipe.models.RecipeCollection;

import java.util.List;

public class DetailFragmentViewModel extends ViewModel {
    private final MutableLiveData<RecipeCollection> selectedRecipeCollection = new MutableLiveData<>();
    private final MutableLiveData<FeedCollection> selectedFeedCollection = new MutableLiveData<>();
    private int position = -1;

    public void setSelectedRecipePosition(int position) {
        this.position = position;
    }
    public int getPosition() {
        return position;
    }

    public LiveData<RecipeCollection> getSelectedRecipeCollection() {
        return selectedRecipeCollection;
    }
    public void setSelectedRecipeCollection(RecipeCollection recipeCollection) {
        selectedRecipeCollection.setValue(recipeCollection);
    }

    public LiveData<FeedCollection> getSelectedFeedCollection() {
        return selectedFeedCollection;
    }
    public void setSelectedFeedCollection(FeedCollection feedCollection) {
        selectedFeedCollection.setValue(feedCollection);
    }



}
