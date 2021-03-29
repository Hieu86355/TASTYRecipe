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
import com.h86355.tastyrecipe.databinding.ItemRecommenedBinding;
import com.h86355.tastyrecipe.models.FeedCollection;

import java.util.ArrayList;
import java.util.List;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.RecommenedViewHolder> {
    private List<FeedCollection> feedCollections;
    private Context context;
    private IMainFragmentListener listener;
    private static final int ITEM = 1;
    private static final int LOADING = 2;
    public static final int ERROR = 3;

    public RecommendAdapter(Context context, IMainFragmentListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecommenedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM) {
            return new RecommenedViewHolder(
                    ItemRecommenedBinding.inflate(LayoutInflater.from(context), parent, false), listener);
        }
        return new RecommenedViewHolder(
                ItemLoadingBinding.inflate(LayoutInflater.from(context), parent, false), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommenedViewHolder holder, int position) {
        if (holder.getItemViewType() == ITEM) {
            holder.binding.recommenedTitle.setText(feedCollections.get(position).getName());
            holder.binding.recommenedItemNumber.setText(feedCollections.get(position).getMin_items() + " meals");
            RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.image_holder);
            Glide.with(context)
                    .applyDefaultRequestOptions(requestOptions)
                    .load(feedCollections.get(position).getRecipeCollections().get(0).getThumbnail_url())
                    .into(holder.binding.recommenedImage);
        } else if (holder.getItemViewType() == ERROR) {
            holder.loadingBinding.loadingView.setAnimation(R.raw.error);
            holder.loadingBinding.refresh.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if (feedCollections != null) {
            return feedCollections.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (feedCollections.get(0).getName() == "LOADING") {
            return LOADING;
        } else if (feedCollections.get(0).getName() == "ERROR") {
            return ERROR;
        } else {
            return ITEM;
        }
    }

    public void displayLoading() {
        FeedCollection feedCollection = new FeedCollection();
        feedCollection.setName("LOADING");
        List<FeedCollection> loadingList = new ArrayList<>();
        loadingList.add(feedCollection);
        feedCollections = loadingList;
        notifyDataSetChanged();
    }

    public void displayError() {
        FeedCollection feedCollection = new FeedCollection();
        feedCollection.setName("ERROR");
        List<FeedCollection> errorList = new ArrayList<>();
        errorList.add(feedCollection);
        feedCollections = errorList;
        notifyDataSetChanged();
    }

    public void setFeedCollections(List<FeedCollection> feedCollections) {
        this.feedCollections = feedCollections;
        notifyDataSetChanged();
    }

    public FeedCollection getSelectedFeedCollection(int position) {
        return feedCollections.get(position);
    }

    public class RecommenedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemRecommenedBinding binding;
        private ItemLoadingBinding loadingBinding;
        private IMainFragmentListener listener;

        public RecommenedViewHolder(ItemRecommenedBinding binding, IMainFragmentListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        public RecommenedViewHolder(ItemLoadingBinding loadingBinding, IMainFragmentListener listener) {
            super(loadingBinding.getRoot());
            this.loadingBinding = loadingBinding;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onRefreshClick("recommened_feeds");
                }
            });
        }

        @Override
        public void onClick(View v) {
            listener.onFeedCollectionClick(getAdapterPosition());
        }
    }
}
