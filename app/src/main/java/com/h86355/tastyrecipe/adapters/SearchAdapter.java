package com.h86355.tastyrecipe.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.h86355.tastyrecipe.R;
import com.h86355.tastyrecipe.databinding.ItemLoadingBinding;
import com.h86355.tastyrecipe.databinding.ItemRecommenedBinding;
import com.h86355.tastyrecipe.models.RecipeCollection;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.TagHolder> {
    private List<RecipeCollection> searchResults;
    private Context context;
    private ISearchListener listener;
    public static final int ITEM = 1;
    public static final int LOADING = 2;
    public static final int ERROR = 3;

    public SearchAdapter(Context context, ISearchListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TagHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM) {
            return new TagHolder(ItemRecommenedBinding.inflate(
                    LayoutInflater.from(context), parent, false), listener);
        }
        return new TagHolder(ItemLoadingBinding.inflate(
                LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TagHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case ITEM:
                holder.itemBinding.recommenedTitle.setText(searchResults.get(position).getName());
                if (searchResults.get(position).getRecipes() == null) {
                    if (searchResults.get(position).getCredits().get(0).getName() == null) {
                        holder.itemBinding.recommenedItemNumber.setText("TASTY");
                    } else {
                        holder.itemBinding.recommenedItemNumber.setText(
                                searchResults.get(position).getCredits().get(0).getName());
                    }
                } else {
                    holder.itemBinding.recommenedItemNumber.setText(
                            searchResults.get(position).getCredits().get(0).getName() + " | " +
                                    searchResults.get(position).getRecipes().size() + " recipes");
                }
                RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.image_holder);
                Glide.with(context)
                        .applyDefaultRequestOptions(requestOptions)
                        .load(searchResults.get(position).getThumbnail_url())
                        .into(holder.itemBinding.recommenedImage);
                break;
            case ERROR:
                holder.loadingBinding.loadingView.setAnimation(R.raw.error);
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (searchResults != null) {
            return searchResults.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (searchResults.get(0).getName() == "LOADING") {
            return LOADING;
        } else if (searchResults.get(0).getName() == "ERROR") {
            return ERROR;
        } else {
            return ITEM;
        }
    }

    public void displayLoading() {
        RecipeCollection recipeCollection = new RecipeCollection();
        recipeCollection.setName("LOADING");
        List<RecipeCollection> loadingList = new ArrayList<>();
        loadingList.add(recipeCollection);
        searchResults = loadingList;
        notifyDataSetChanged();
    }

    public void displayError() {
        RecipeCollection recipeCollection = new RecipeCollection();
        recipeCollection.setName("ERROR");
        List<RecipeCollection> errorList = new ArrayList<>();
        errorList.add(recipeCollection);
        searchResults = errorList;
        notifyDataSetChanged();
    }

    public void setSearchResults(List<RecipeCollection> results) {
        searchResults = results;
        notifyDataSetChanged();
    }

    public void addSearchResults(List<RecipeCollection> moreResults) {
        if (searchResults != null && searchResults.size() > 0) {
            searchResults.addAll(moreResults);
        }
        notifyDataSetChanged();
    }

    public RecipeCollection getSelectedRecipe(int position) {
        return searchResults.get(position);
    }

    public class TagHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemRecommenedBinding itemBinding;
        private ItemLoadingBinding loadingBinding;
        private ISearchListener listener;

        public TagHolder(ItemRecommenedBinding itemBinding, ISearchListener listener) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        public TagHolder(ItemLoadingBinding loadingBinding) {
            super(loadingBinding.getRoot());
            this.loadingBinding = loadingBinding;
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition());
        }
    }
}
