package com.h86355.tastyrecipe.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.h86355.tastyrecipe.R;
import com.h86355.tastyrecipe.databinding.ItemLoadingBinding;
import com.h86355.tastyrecipe.databinding.ItemTodayRecipesBinding;
import com.h86355.tastyrecipe.models.Credit;
import com.h86355.tastyrecipe.models.RecipeCollection;

import java.util.ArrayList;
import java.util.List;

public class TodayRecipesAdapter extends RecyclerView.Adapter<TodayRecipesAdapter.TodayRecipesHolder> {
    private List<RecipeCollection> recipeCollections;
    private Context context;
    private IMainFragmentListener listener;
    private static final int ITEM = 1;
    private static final int LOADING = 2;
    public static final int ERROR = 3;

    public TodayRecipesAdapter(Context context, IMainFragmentListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TodayRecipesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM) {
            return new TodayRecipesHolder(
                    ItemTodayRecipesBinding.inflate(
                            LayoutInflater.from(context), parent, false), listener);
        }
        return new TodayRecipesHolder(
                ItemLoadingBinding.inflate(
                        LayoutInflater.from(context), parent, false), listener);

    }

    @Override
    public void onBindViewHolder(@NonNull TodayRecipesHolder holder, int position) {
        if (holder.getItemViewType() == ITEM) {
            holder.binding.todayTitle.setText(recipeCollections.get(position).getName());
            RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.image_holder);
            Glide.with(context)
                    .applyDefaultRequestOptions(requestOptions)
                    .load(recipeCollections.get(position).getThumbnail_url())
                    .into(holder.binding.todayImage);
        } else if (holder.getItemViewType() == ERROR) {
            holder.loadingBinding.loadingView.setAnimation(R.raw.error);
            holder.loadingBinding.refresh.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if (recipeCollections != null) {
            return recipeCollections.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (recipeCollections.get(0).getName() == "LOADING") {
            return LOADING;
        } else if (recipeCollections.get(0).getName() == "ERROR") {
            return ERROR;
        } else {
            return ITEM;
        }
    }

    public void displayLoading() {
        RecipeCollection collection = new RecipeCollection();
        collection.setName("LOADING");
        List<RecipeCollection> loadingList = new ArrayList<>();
        loadingList.add(collection);
        recipeCollections = loadingList;
        notifyDataSetChanged();
    }

    public void displayError() {
        RecipeCollection collection = new RecipeCollection();
        collection.setName("ERROR");
        List<RecipeCollection> errorList = new ArrayList<>();
        errorList.add(collection);
        recipeCollections = errorList;
        notifyDataSetChanged();
    }

    public void setRecipes(List<RecipeCollection> collections) {
        this.recipeCollections = collections;
        notifyDataSetChanged();
    }

    public RecipeCollection getSelectedTodayRecipe(int position) {
        if (recipeCollections != null && recipeCollections.size() > 0) {
            return recipeCollections.get(position);
        }
        return null;
    }

    public class TodayRecipesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemTodayRecipesBinding binding;
        private ItemLoadingBinding loadingBinding;
        private IMainFragmentListener listener;

        public TodayRecipesHolder(ItemTodayRecipesBinding binding, IMainFragmentListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            itemView.setOnClickListener(this);
            this.listener = listener;
        }

        public TodayRecipesHolder(ItemLoadingBinding loadingBinding, IMainFragmentListener listener) {
            super(loadingBinding.getRoot());
            this.loadingBinding = loadingBinding;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onRefreshClick("today_recipes");
                }
            });
        }

        @Override
        public void onClick(View v) {
            listener.onRecipeTodayClick(getAdapterPosition());
        }
    }

}
