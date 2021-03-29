package com.h86355.tastyrecipe.requests;

import com.h86355.tastyrecipe.requests.responses.FeedResponse;
import com.h86355.tastyrecipe.requests.responses.RecipeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RequestAPI {

    @GET("recipes/list")
    Call<RecipeResponse> getRecipes(
            @Query("from") int from,
            @Query("size") int size,
            @Query("q") String query,
            @Query("tags") String tags
    );

    @GET("feeds/list")
    Call<FeedResponse> getFeeds(
            @Query("from") int from,
            @Query("size") int size,
            @Query("timezone") String timezone,
            @Query("vegetarian") boolean Vegetarian
    );

}
