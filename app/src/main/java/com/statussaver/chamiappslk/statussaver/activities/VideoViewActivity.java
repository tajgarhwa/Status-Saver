package com.statussaver.chamiappslk.statussaver.activities;


import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class VideoViewActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    public void onClick(View v) {

    }

//    private MKPlayer player;
//
//    private FloatingActionButton fabMain, fabFirst, fabMainsecond, fabthired;
//    private Animation fabOpen, fabClose, fabForaward, fabBackward;
//    private boolean isOpen = false;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_video_view);
//
//
//        fabMain = findViewById(R.id.fab_main);
//        fabFirst = findViewById(R.id.fab_main_first);
//        fabMainsecond = findViewById(R.id.fab_main_download);
//        fabthired = findViewById(R.id.fab_main_thired);
//
//        fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open);
//        fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close);
//        fabForaward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
//        fabBackward = AnimationUtils.loadAnimation(this, R.anim.rotate_backward);
//
//        fabMain.setOnClickListener(this);
//        fabFirst.setOnClickListener(this);
//        fabMainsecond.setOnClickListener(this);
//        fabthired.setOnClickListener(this);
//
//        String sessionUrl = getIntent().getStringExtra("url");
//
//        player = new MKPlayer(this);
//        player.play(sessionUrl);
//        player.setTitle("WhatsApp Video");
//
//        player.onComplete(new Runnable() {
//            @Override
//            public void run() {
//                fabMain.show();
//            }
//        });
//
//        if (player.isPlaying()) {
//
//            fabMain.hide();
//        }
//        player.setPlayerCallbacks(new MKPlayer.playerCallbacks() {
//            @Override
//            public void onNextClick() {
//                player.forward(0.1f);
//            }
//
//            @Override
//            public void onPreviousClick() {
//                player.forward(-0.1f);
//            }
//        });
//    }
//
//    private void animatedFab() {
//
//        if (isOpen) {
//
//            fabMain.startAnimation(fabForaward);
//            fabFirst.startAnimation(fabClose);
//            fabMainsecond.startAnimation(fabClose);
//            fabthired.startAnimation(fabClose);
//
//            fabFirst.setClickable(false);
//            fabMainsecond.setClickable(false);
//            fabthired.setClickable(false);
//            isOpen = false;
//        } else {
//            fabMain.startAnimation(fabBackward);
//            fabFirst.startAnimation(fabOpen);
//            fabMainsecond.startAnimation(fabOpen);
//            fabthired.startAnimation(fabOpen);
//
//            fabFirst.setClickable(true);
//            fabMainsecond.setClickable(true);
//            fabthired.setClickable(true);
//            isOpen = true;
//
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.fab_main:
//                animatedFab();
//                break;
//            case R.id.fab_main_first:
//                animatedFab();
//                break;
//            case R.id.fab_main_download:
//                animatedFab();
////                try {
////                    copyFile(sourceFile, new File(Environment.getExternalStorageDirectory().toString() + DIRECTORY_TO_SAVE_MEDIA_NOW + sourceFile.getName()));
////                } catch (IOException e) {
////                    e.printStackTrace();
////                    Log.e("RecyclerV", "onClick: Error:"+e.getMessage() );
////                }
////                ToastCustom.setToast(this, "Image Saved..");
//                break;
//            case R.id.fab_main_thired:
//                animatedFab();
//                break;
//        }
//    }
//
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (player != null) {
//            player.onPause();
//            fabMain.hide();
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (player != null) {
//            player.onResume();
//            fabMain.show();
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (player != null) {
//            player.onDestroy();
//        }
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        if (player != null) {
//            player.onConfigurationChanged(newConfig);
//        }
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (player != null && player.onBackPressed()) {
//            return;
//        }
//        super.onBackPressed();
//    }
}
