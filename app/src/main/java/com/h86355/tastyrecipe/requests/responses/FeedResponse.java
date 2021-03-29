package com.h86355.tastyrecipe.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.h86355.tastyrecipe.models.FeedCollection;

import java.util.List;

public class FeedResponse {
    @SerializedName("results")
    @Expose
    List<FeedCollection> feedCollections;

    public List<FeedCollection> getFeedCollections() {
        return feedCollections;
    }
}
