package com.example.bakingApp.fragments;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bakingApp.R;
import com.example.bakingApp.objects.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;



public class StepDetailsFragment extends Fragment{
    private static final String STEP_SAVED = "step_saved";
    private static final String TIME_SAVED = "time_saved";
    private Step stepOnFragment;
    private SimpleExoPlayer exoPlayer;
    private Long currentTime;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_details, container, false);
        TextView desc = rootView.findViewById(R.id.step_detail_desc);
        PlayerView playerView = rootView.findViewById(R.id.player_view);

        if (savedInstanceState != null) {
            stepOnFragment = (Step) savedInstanceState.getSerializable(STEP_SAVED);
            currentTime = savedInstanceState.getLong(TIME_SAVED);
            if (stepOnFragment != null) {
                playerCheck(stepOnFragment, playerView);
                exoPlayer.seekTo(currentTime);
            }
        }

        assert stepOnFragment != null;
        desc.setText(stepOnFragment.getDesc());
        playerCheck(stepOnFragment, playerView);
        return rootView;
    }

    public void setStepDetailData(Step stepToSet) {stepOnFragment = stepToSet;}

    private void playerCheck(Step stepOnFragment, PlayerView playerView) {
        if (stepOnFragment.getVideoUrl() != null && !stepOnFragment.getVideoUrl().equals("")) {
            initializePlayer(playerView);
        } else {
            playerView.setVisibility(View.GONE);
        }
    }

    private void initializePlayer(PlayerView simpleExoPlayerView) {
        if (exoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            simpleExoPlayerView.setPlayer(exoPlayer);
            MediaSource mediaSource = new ExtractorMediaSource
                    .Factory(new DefaultHttpDataSourceFactory("step_details_fragment"))
                    .createMediaSource(Uri.parse(stepOnFragment.getVideoUrl()));
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        exoPlayer.stop();
        exoPlayer.release();
        exoPlayer = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (exoPlayer != null) {
            releasePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            releasePlayer();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(STEP_SAVED, stepOnFragment);
        currentTime = exoPlayer.getCurrentPosition();
        outState.putLong(TIME_SAVED, exoPlayer.getCurrentPosition());
        super.onSaveInstanceState(outState);
    }
}
