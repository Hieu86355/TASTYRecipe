package com.h86355.tastyrecipe.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieDrawable;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.h86355.tastyrecipe.R;
import com.h86355.tastyrecipe.databinding.FragmentVideoBinding;
import com.h86355.tastyrecipe.models.Recipe;
import com.h86355.tastyrecipe.models.RecipeCollection;
import com.h86355.tastyrecipe.viewmodels.DetailFragmentViewModel;

import static com.google.android.exoplayer2.Player.STATE_ENDED;
import static com.google.android.exoplayer2.Player.STATE_READY;

public class VideoFragment extends Fragment {
    private FragmentVideoBinding binding;
    private DetailFragmentViewModel detailFragmentViewModel;
    private ExoPlayer player;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentVideoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        detailFragmentViewModel = new ViewModelProvider(requireActivity()).get(DetailFragmentViewModel.class);
        subscribeObservers();
    }

    private void subscribeObservers() {
        detailFragmentViewModel.getSelectedRecipeCollection().observe(getViewLifecycleOwner(), new Observer<RecipeCollection>() {
            @Override
            public void onChanged(RecipeCollection recipeCollection) {
                subscribeUi(recipeCollection);
            }
        });
    }

    private void subscribeUi(RecipeCollection recipeCollection) {
        if (recipeCollection.getRecipes() == null) {
            if (recipeCollection.getVideo_url() == null) {
                binding.errorView.setAnimation(R.raw.empty);
                binding.errorView.setRepeatCount(LottieDrawable.INFINITE);

            } else if (!recipeCollection.getVideo_url().isEmpty()){
                binding.errorView.setAnimation(R.raw.loading_food);
                binding.errorView.setRepeatCount(LottieDrawable.INFINITE);
                MediaItem mediaItem = MediaItem.fromUri(recipeCollection.getVideo_url());
                player.setMediaItem(mediaItem);
                player.prepare();
            }
        } else if (detailFragmentViewModel.getPosition() != -1) {
            int position = detailFragmentViewModel.getPosition();
            if (recipeCollection.getVideo_url() == null) {
                binding.errorView.setAnimation(R.raw.empty);

            } else if (!recipeCollection.getVideo_url().isEmpty()){
                binding.errorView.setAnimation(R.raw.loading_food);
                binding.errorView.setRepeatCount(LottieDrawable.INFINITE);
                MediaItem mediaItem = MediaItem.fromUri(recipeCollection.getRecipes().get(position).getVideo_url());
                player.setMediaItem(mediaItem);
                player.prepare();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        intPlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (player != null && player.isPlaying()) {
            player.pause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    private void intPlayer() {
        player = new SimpleExoPlayer.Builder(getActivity()).build();
        binding.playerView.setPlayer(player);
        player.addListener(new Player.EventListener() {
            @Override
            public void onPlaybackStateChanged(int state) {
                if (state == STATE_READY) {
                    binding.playerView.setVisibility(View.VISIBLE);
                    binding.errorView.setVisibility(View.GONE);
                }
            }
        });
    }
}
