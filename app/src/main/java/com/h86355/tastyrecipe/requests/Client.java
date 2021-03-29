package com.h86355.tastyrecipe.requests;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.h86355.tastyrecipe.models.FeedCollection;
import com.h86355.tastyrecipe.models.Recipe;
import com.h86355.tastyrecipe.models.RecipeCollection;
import com.h86355.tastyrecipe.requests.responses.FeedResponse;
import com.h86355.tastyrecipe.requests.responses.RecipeResponse;
import com.h86355.tastyrecipe.utils.AppExecutor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

import static com.h86355.tastyrecipe.utils.Constants.NETWORK_TIMEOUT;

public class Client {
    private static final String TAG = "CLIENT";
    private static Client instance;
    private MutableLiveData<List<RecipeCollection>> todayRecipes;
    private MutableLiveData<List<FeedCollection>> recommendedFeeds;
    private MutableLiveData<List<RecipeCollection>> searchRecipes;
    private RetrieveTodayRecipesRunnable todayRecipesRunnable;
    private RetriveRecommenedRunnable recommenedRunnable;
    private RetrieveSearchRecipesRunnable searchRecipesRunnable;

    public static Client getInstance() {
        if (instance == null) {
            instance = new Client();
        }
        return instance;
    }

    public Client() {
        todayRecipes = new MutableLiveData<>();
        recommendedFeeds = new MutableLiveData<>();
        searchRecipes = new MutableLiveData<>();
    }

    public LiveData<List<RecipeCollection>> getTodayRecipes() {
        return todayRecipes;
    }

    public LiveData<List<FeedCollection>> getRecommendedFeeds() {
        return recommendedFeeds;
    }

    public LiveData<List<RecipeCollection>> getSearchRecipes() {
        return searchRecipes;
    }

    public void getTodayRecipesAPI(int from, int size, String query, String tag) {
        if (todayRecipesRunnable != null) {
            todayRecipesRunnable = null;
        }
        todayRecipesRunnable = new RetrieveTodayRecipesRunnable(from, size, query, tag);
        final Future handler = AppExecutor.getInstance().netWorkIO().submit(todayRecipesRunnable);
        AppExecutor.getInstance().netWorkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
                if (todayRecipes.getValue() == null) {
                    todayRecipes.postValue(null);
                }
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    public void getRecommendedFeedsAPI(int from, int size, String timeZone, boolean vegetarian) {
        if (recommenedRunnable != null) {
            recommenedRunnable = null;
        }
        recommenedRunnable = new RetriveRecommenedRunnable(from, size, timeZone, vegetarian);
        final Future handler = AppExecutor.getInstance().netWorkIO().submit(recommenedRunnable);
        AppExecutor.getInstance().netWorkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
                if (recommendedFeeds.getValue() == null) {
                    recommendedFeeds.postValue(null);
                }
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    public void getSearchRecipesAPI(int from, int size, String query, String tag) {
        if (searchRecipesRunnable != null) {
            searchRecipesRunnable = null;
        }
        searchRecipesRunnable = new RetrieveSearchRecipesRunnable(from, size, query, tag);
        final Future handler = AppExecutor.getInstance().netWorkIO().submit(searchRecipesRunnable);
        AppExecutor.getInstance().netWorkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    /*
     * Get Recipes List
     */
    public class RetrieveTodayRecipesRunnable implements Runnable {
        private int from;
        private int size;
        private String query;
        private String tag;
        private boolean cancelRequest;

        public RetrieveTodayRecipesRunnable(int from, int size, String query, String tag) {
            this.from = from;
            this.size = size;
            this.query = query;
            this.tag = tag;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getRecipes(from, size, query, tag).execute();
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {
                    List<RecipeCollection> collections = new ArrayList<>(((RecipeResponse)response.body()).getRecipeCollections());
                    List<RecipeCollection> todayList = new ArrayList<>();
                    for (RecipeCollection collection : collections) {
                        if (collection.getRecipes() == null) {
                            todayList.add(collection);
                        }
                    }
                    todayRecipes.postValue(todayList);
                } else {
                    todayRecipes.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                todayRecipes.postValue(null);
            }
        }

        private Call<RecipeResponse> getRecipes(int from, int size, String query, String tag) {
            return ServiceGenerator.getRequestAPI().getRecipes(from, size, query, tag);
        }

        private void cancelRequest() {
            cancelRequest = true;
            Log.d(TAG, "cancelRequest: called");
        }
    }

    /*
    * Get Feed List
    */
    public class RetriveRecommenedRunnable implements Runnable {
        private int from;
        private int size;
        private String timeZone;
        private boolean vegetarian;
        private boolean cancelRequest;

        public RetriveRecommenedRunnable(int from, int size, String timeZone, boolean vegetarian) {
            this.from = from;
            this.size = size;
            this.timeZone = timeZone;
            this.vegetarian = vegetarian;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getFeeds(from, size, timeZone, vegetarian).execute();
                if (response.code() == 200) {
                    List<FeedCollection> feedCollections = new ArrayList<>(((FeedResponse) response.body()).getFeedCollections());
                    List<FeedCollection> recommendeds = new ArrayList<>();
                    for (FeedCollection collection : feedCollections) {
                        if (collection.getRecipeCollections() != null && collection.getName() != null) {
                            recommendeds.add(collection);
                        }
                    }
                    recommendedFeeds.postValue(recommendeds);
                } else {
                    recommendedFeeds.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                recommendedFeeds.postValue(null);
            }
        }

        private Call<FeedResponse> getFeeds(int from, int size, String timeZone, boolean vegetarian) {
            return ServiceGenerator.getRequestAPI().getFeeds(from, size, timeZone, vegetarian);
        }

        private void cancelRequest() {
            cancelRequest = true;
            Log.d(TAG, "cancelRequest: called");
        }
    }

    /*
    * Search Recipes
    */
    public class RetrieveSearchRecipesRunnable implements Runnable {
        private int from;
        private int size;
        private String query;
        private String tag;
        private boolean cancelRequest;

        public RetrieveSearchRecipesRunnable(int from, int size, String query, String tag) {
            this.from = from;
            this.size = size;
            this.query = query;
            this.tag = tag;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getRecipes(from, size, query, tag).execute();
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {
                    int count = ((RecipeResponse)response.body()).getCount();
                    if (count == 0) {
                        searchRecipes.postValue(null);
                    } else {
                        List<RecipeCollection> collections = new ArrayList<>(((RecipeResponse)response.body()).getRecipeCollections());
                        searchRecipes.postValue(collections);
                    }
                } else {
                    searchRecipes.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                searchRecipes.postValue(null);
            }
        }

        private Call<RecipeResponse> getRecipes(int from, int size, String query, String tag) {
            return ServiceGenerator.getRequestAPI().getRecipes(from, size, query, tag);
        }

        private void cancelRequest() {
            cancelRequest = true;
            Log.d(TAG, "cancelRequest: called");
        }
    }
}
