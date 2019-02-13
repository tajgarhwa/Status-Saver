package android.statussaver.com.statussaver.activities;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.statussaver.com.statussaver.R;
import android.statussaver.com.statussaver.utils.ToastCustom;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class ImageViewActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String DIRECTORY_TO_SAVE_MEDIA_NOW ="/Status_Saver/" ;
    String sessionUrl,state;
    ImageView imageView;
    File sourceFile;

    private FloatingActionButton fabMain, fabFirst, fabMainsecond, fabthired;
    private Animation fabOpen, fabClose, fabForaward, fabBackward;
    private boolean isOpen = false;


//private PhotoViewAttacher photoViewAttacher;]

    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        sessionUrl = getIntent().getStringExtra("image");
        state =getIntent().getStringExtra("state");
        sourceFile = (File)getIntent().getSerializableExtra("file");

        imageView = findViewById(R.id.img_status);

        fabMain = findViewById(R.id.fab_main);
        fabFirst = findViewById(R.id.fab_main_first);
        fabMainsecond = findViewById(R.id.fab_main_download);
        fabthired = findViewById(R.id.fab_main_thired);

        fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close);
        fabForaward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
        fabBackward = AnimationUtils.loadAnimation(this, R.anim.rotate_backward);

        fabMain.setOnClickListener(this);
        fabFirst.setOnClickListener(this);
        fabMainsecond.setOnClickListener(this);
        fabthired.setOnClickListener(this);

      //  photoViewAttacher = new PhotoViewAttacher(imageView);
        Picasso.with(this).load("file://" + sessionUrl).placeholder(R.drawable.placeholder).into(imageView);

        mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

        hideView(state);


    }

    private void hideView(String state) {

        if (state.equalsIgnoreCase("TWO") || state.equalsIgnoreCase("THREE")){

            //fabMainsecond.setVisibility(View.GONE);
            fabMainsecond.hide();
        }

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
//                try {
//                    copyFile(sourceFile, new File(Environment.getExternalStorageDirectory().toString() + DIRECTORY_TO_SAVE_MEDIA_NOW + sourceFile.getName()));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    Log.e("RecyclerV", "onClick: Error:"+e.getMessage() );
//                }
//                ToastCustom.setToast(this, "Image Saved..");
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
        }else {
            Toast.makeText(this,"Tt's already exist!",Toast.LENGTH_LONG).show();
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
    public boolean onTouchEvent(MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }


    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector){
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.1f,
                    Math.min(mScaleFactor, 10.0f));
            imageView.setScaleX(mScaleFactor);
            imageView.setScaleY(mScaleFactor);
            return true;
        }
    }
}
