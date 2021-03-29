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
import com.h86355.tastyrecipe.databinding.ItemRecommenedBinding;
import com.h86355.tastyrecipe.models.Credit;
import com.h86355.tastyrecipe.models.Recipe;
import com.h86355.tastyrecipe.models.RecipeCollection;

import java.util.List;

public class FeedsAdapter extends RecyclerView.Adapter<FeedsAdapter.FeedHolder>{
    private IFeedListListener listener;
    private Context context;
    private List<RecipeCollection> recipeCollections;

    public FeedsAdapter(Context context, IFeedListListener listener) {
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public FeedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FeedHolder(ItemRecommenedBinding.inflate(LayoutInflater.from(context),
                parent, false), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedHolder holder, int position) {
        holder.binding.recommenedTitle.setText(recipeCollections.get(position).getName());
        if (recipeCollections.get(position).getRecipes() == null) {
            String credits = creditNamesFormat(recipeCollections.get(position));
            holder.binding.recommenedItemNumber.setText(credits);
        } else {
            holder.binding.recommenedItemNumber.setText(recipeCollections.get(position).getRecipes().size() + " recipes");
        }
        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.image_holder);
        Glide.with(context)
                .applyDefaultRequestOptions(requestOptions)
                .load(recipeCollections.get(position).getThumbnail_url())
                .into(holder.binding.recommenedImage);
    }

    private String creditNamesFormat(RecipeCollection recipeCollection) {
        String creditNames = "";
        if (recipeCollection.getCredits() == null || recipeCollection.getCredits().size() == 0) {
            creditNames = "TASTY";
        } else {
            StringBuilder builder = new StringBuilder();
            for (Credit cr : recipeCollection.getCredits()) {
                if (cr.getName() != null && !cr.getName().equals("")) {
                    builder.append(cr.getName() + ", ");
                }
            }
            if (builder.length() > 0) {
                creditNames=builder.replace(builder.length()-2, builder.length(), "").toString();
            } else {
                creditNames = "TASTY";
            }
        }
        return creditNames;
    }

    @Override
    public int getItemCount() {
        if (recipeCollections != null) {
            return recipeCollections.size();
        }
        return 0;
    }


    public void setRecipeCollections(List<RecipeCollection> collections) {
        recipeCollections = collections;
        notifyDataSetChanged();
    }

    public RecipeCollection getSellectedRecipeCollection(int position) {
        return recipeCollections.get(position);
    }

    public class FeedHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemRecommenedBinding binding;
        private IFeedListListener listener;
        public FeedHolder(ItemRecommenedBinding binding, IFeedListListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            itemView.setOnClickListener(this);
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            listener.onRecipeCollectionClick(getAdapterPosition());
        }
    }
}
