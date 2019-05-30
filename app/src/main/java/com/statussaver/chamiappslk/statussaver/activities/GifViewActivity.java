package com.statussaver.chamiappslk.statussaver.activities;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.statussaver.com.statussaver.R;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.squareup.picasso.Picasso;
import com.statussaver.chamiappslk.statussaver.utils.ToastCustom;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class GifViewActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String DIRECTORY_TO_SAVE_MEDIA_NOW = "/Status_Saver/";
    String sessionUrl;
    VideoView videoView;
    File sourceFile;

    private FloatingActionButton fabMain, fabFirst, fabMainsecond, fabthired;
    private Animation fabOpen, fabClose, fabForaward, fabBackward;
    private boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_gif_view);


        sessionUrl = getIntent().getStringExtra("gif");
        sourceFile = (File) getIntent().getSerializableExtra("file");

        videoView = findViewById(R.id.img_status);

        fabMain = findViewById(R.id.fab_main);
        fabFirst = findViewById(R.id.fab_main_first);
        fabMainsecond = findViewById(R.id.fab_main_download);
        fabthired = findViewById(R.id.fab_main_thired);

        fabOpen = AnimationUtils.loadAnimation(this,R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(this,R.anim.fab_close);
        fabForaward = AnimationUtils.loadAnimation(this,R.anim.rotate_forward);
        fabBackward = AnimationUtils.loadAnimation(this,R.anim.rotate_backward);

        fabMain.setOnClickListener(this);
        fabFirst.setOnClickListener(this);
        fabMainsecond.setOnClickListener(this);
        fabthired.setOnClickListener(this);

        // Picasso.with(this).load("file://" + sessionUrl).placeholder(R.drawable.placeholder).into(imageView);

       // GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
        //Glide.with(this).load("file://" + sessionUrl).asGif().placeholder(R.drawable.placeholder).into(imageView);


       // Uri uri = Uri.parse("file://" + sessionUrl);

        videoView.setVideoPath("file://" + sessionUrl);
        videoView.requestFocus();
        videoView.start();

        videoView.setOnPreparedListener (new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

    }

    private void animatedFab() {

        if (isOpen) {

            fabMain.startAnimation(fabForaward);
            fabFirst.startAnimation(fabClose);
            fabMainsecond.startAnimation(fabClose);
            fabthired.startAnimation(fabClose);

            fabFirst.setClickable(false);
            fabMainsecond.setClickable(false);
            fabthired.setClickable(false);
            isOpen = false;
        } else {
            fabMain.startAnimation(fabBackward);
            fabFirst.startAnimation(fabOpen);
            fabMainsecond.startAnimation(fabOpen);
            fabthired.startAnimation(fabOpen);

            fabFirst.setClickable(true);
            fabMainsecond.setClickable(true);
            fabthired.setClickable(true);
            isOpen = true;

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_main:
                animatedFab();
                break;
            case R.id.fab_main_first:
                animatedFab();
                break;
            case R.id.fab_main_download:
                animatedFab();
                try {
                    copyFile(sourceFile, new File(Environment.getExternalStorageDirectory().toString() + DIRECTORY_TO_SAVE_MEDIA_NOW + sourceFile.getName()));
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("RecyclerV", "onClick: Error:" + e.getMessage());
                }
                ToastCustom.setToast(this, "Image Saved..");
                break;
            case R.id.fab_main_thired:
                animatedFab();
                break;
        }
    }

    private void copyFile(File sourceFile, File file) throws IOException {
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();

        if (!file.exists()) {
            file.createNewFile();
        } else {
            Toast.makeText(this, "Tt's already exist!", Toast.LENGTH_LONG).show();
            return;
        }
        FileChannel source = null;
        FileChannel destination = null;
        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(file).getChannel();
            destination.transferFrom(source, 0, source.size());
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

