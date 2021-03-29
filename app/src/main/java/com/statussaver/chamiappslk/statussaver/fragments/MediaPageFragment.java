package com.statussaver.chamiappslk.statussaver.fragments;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.statussaver.com.statussaver.R;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.io.File;

public class MediaPageFragment extends Fragment {

    private File message;

    private FrameLayout layoutVideo;
    private VideoView videoView;
    private FrameLayout layoutPhoto;
    private PhotoView photoView;
    private MediaController mediaController;
    private boolean activityCreated = false;
    private boolean activityVisible = false;

    public MediaPageFragment() {
    }

    public static MediaPageFragment newInstance(File message) {
        Bundle args = new Bundle();
        args.putSerializable("message", message);
        MediaPageFragment fragment = new MediaPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_media_page, container, false);
        layoutVideo = view.findViewById(R.id.layoutVideo);
        videoView = view.findViewById(R.id.videoView);
        layoutPhoto = view.findViewById(R.id.layoutPhoto);
        photoView = view.findViewById(R.id.photoView);
        message = (File) getArguments().getSerializable("message");
        updateUi();
        return view;
    }

    //
    private void updateUi() {
        if (message != null) {
            if (message.getAbsolutePath().endsWith(".jpg") || message.getAbsolutePath().endsWith(".png")) {
                layoutVideo.setVisibility(View.GONE);
                layoutPhoto.setVisibility(View.VISIBLE);
                String url = message.getAbsolutePath();
                Picasso.with(getActivity()).load("file://" + url).placeholder(R.drawable.placeholder).into(photoView);
            } else {
                layoutVideo.setVisibility(View.VISIBLE);
                layoutPhoto.setVisibility(View.GONE);
                //String url = "file:///" + message.getLocalData();
                //ImageLoader.getInstance().displayImage(url, photoView);
                String url = message.getAbsolutePath();
                Picasso.with(getActivity()).load("file://" + url).placeholder(R.drawable.placeholder).into(photoView);

            }
        }
    }

//    @Override
//    protected void onVisibilityChange(boolean visible) {
//        super.onVisibilityChange(visible);
//        if (message.getMessageType().equalsIgnoreCase(Message.TYPE_VIDEO)) {
//            if (visible) {
//                try {
//                    mediaController = new MediaController(getActivity());
//                    videoView.setMediaController(mediaController);
//                    videoView.setVideoPath(message.getLocalData());
//                    videoView.setOnPreparedListener(mp -> {
//                        videoView.seekTo(1);
//                        //    videoView.start(); //auto play video
//                        mediaController.show(900000000);
//                        videoView.seekTo(1);
//                    });
//                } catch (Exception e) {
//                    e.printStackTrace();
//
//                    Log.e("error from video", e.getMessage());
//                }
//            } else {
//                try {
//                    videoView.stopPlayback();
//                } catch (Exception e) {
//                }
//            }
//        }
//    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        checkVisibility();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activityCreated = true;
        checkVisibility();
    }

    private void checkVisibility() {
        if (activityCreated) {
            onVisibilityChange(getUserVisibleHint());
        }
    }

    protected void onVisibilityChange(boolean visible) {

        this.activityVisible = visible;
        if (activityVisible) {
            try {

                mediaController = new MediaController(getActivity());
                videoView.setMediaController(mediaController);
                videoView.setVideoPath(message.getAbsolutePath());
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        videoView.seekTo(1);
                        //    videoView.start(); //auto play video
                        mediaController.show(900000000);
                        videoView.seekTo(1);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();

                Log.e("error from video", e.getMessage());
            }
        } else {
            try {
                videoView.stopPlayback();
            } catch (Exception e) {

            }
        }
    }

}
