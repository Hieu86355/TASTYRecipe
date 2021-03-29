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
import com.h86355.tastyrecipe.databinding.ItemRecommenedBinding;
import com.h86355.tastyrecipe.models.Recipe;

import java.util.List;

import okhttp3.Request;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesHolder>{
    private List<Recipe> recipes;
    private Context context;
    private IRecipesListener listener;

    public RecipesAdapter(Context context, IRecipesListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecipesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecipesHolder(
                ItemRecommenedBinding.inflate(LayoutInflater.from(context), parent, false), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesHolder holder, int position) {
        holder.binding.recommenedTitle.setText(recipes.get(position).getName());
        if (recipes.get(position).getCredits().get(0).getName() == null) {
            holder.binding.recommenedItemNumber.setText("TASTY");
        } else {
            holder.binding.recommenedItemNumber.setText(
                    recipes.get(position).getCredits().get(0).getName());
        }

        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.image_holder);
        Glide.with(context)
                .applyDefaultRequestOptions(requestOptions)
                .load(recipes.get(position).getThumbnail_url())
                .into(holder.binding.recommenedImage);
    }

    @Override
    public int getItemCount() {
        if (recipes != null) {
            return recipes.size();
        }
        return 0;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    public Recipe getSelectedRecipe(int position) {
        return recipes.get(position);
    }

    public class RecipesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemRecommenedBinding binding;
        private IRecipesListener listener;
        public RecipesHolder(ItemRecommenedBinding binding, IRecipesListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onRecipeClick(getAdapterPosition());
        }
    }
}
