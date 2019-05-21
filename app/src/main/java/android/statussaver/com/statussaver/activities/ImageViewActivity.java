package android.statussaver.com.statussaver.activities;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.statussaver.com.statussaver.R;
import android.statussaver.com.statussaver.adapters.ViewPagerAdapter;
import android.statussaver.com.statussaver.utils.Alerts;
import android.statussaver.com.statussaver.utils.ToastCustom;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class ImageViewActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener{

    private static final String DIRECTORY_TO_SAVE_MEDIA_NOW ="/Status_Saver/" ;
    private static final String WHATSAPP_STATUSES_LOCATION = "/WhatsApp/Media/.Statuses";
    String sessionUrl,state;
    //ImageView imageView;
    PhotoView imageView;
    File sourceFile;

    private FloatingActionButton fabMain, fabFirst, fabMainsecond, fabthired,fab_main_share;
    private RelativeLayout relshare,relshare_fab_main_thired,relshare_fab_main_download,relshare_fab_main_first;
    private Animation fabOpen, fabClose, fabForaward, fabBackward;
    private boolean isOpen = false;
    private ViewPagerAdapter adapter;
    private ArrayList<File> documents;
    private String position;
    private int pos;
    private TextView tvCurrentItem;
    private RelativeLayout rel_close;
    private int globPosition;
    private AdRequest adRequest;


//private PhotoViewAttacher photoViewAttacher;]

    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        //sessionUrl = getIntent().getStringExtra("imageList");
        documents = (ArrayList<File>) getIntent().getSerializableExtra("imageList");
        state =getIntent().getStringExtra("state");
        pos = getIntent().getIntExtra("position",0);
        //sourceFile = (File)getIntent().getSerializableExtra("file");

        //pos = Integer.parseInt(position);
        //imageView = findViewById(R.id.img_status);

        globPosition =pos;

        fabMain = findViewById(R.id.fab_main);
        fabFirst = findViewById(R.id.fab_main_first);
        fabMainsecond = findViewById(R.id.fab_main_download);
        fabthired = findViewById(R.id.fab_main_thired);
        fab_main_share = findViewById(R.id.fab_main_share);
        ViewPager viewPager = findViewById(R.id.view_pager);
        tvCurrentItem = findViewById(R.id.tvCurrentItem);
        rel_close = findViewById(R.id.rel_close);

        relshare = findViewById(R.id.relshare);
        relshare_fab_main_thired = findViewById(R.id.relshare_fab_main_thired);
        relshare_fab_main_download = findViewById(R.id.relshare_fab_main_download);
        relshare_fab_main_first = findViewById(R.id.relshare_fab_main_first);
        mAdView = findViewById(R.id.adView);

        fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close);
        fabForaward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
        fabBackward = AnimationUtils.loadAnimation(this, R.anim.rotate_backward);

        fabMain.setOnClickListener(this);
        fabFirst.setOnClickListener(this);
        fabMainsecond.setOnClickListener(this);
        fabthired.setOnClickListener(this);
        fab_main_share.setOnClickListener(this);
        rel_close.setOnClickListener(this);

        adapter = new ViewPagerAdapter(getSupportFragmentManager(), documents);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
      //  photoViewAttacher = new PhotoViewAttacher(imageView);
        //Picasso.with(this).load("file://" + sessionUrl).placeholder(R.drawable.placeholder).into(imageView);

        //mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

        try {
            viewPager.setCurrentItem(pos);
            onPageSelected(pos);
        } catch (Exception e) {
        }

        hideView(state);
        MobileAds.initialize(this, getString(R.string.admob_ad_id));
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            mAdView.setVisibility(View.VISIBLE);
            adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }else {
            mAdView.setVisibility(View.GONE);

        }

    }

    private void hideView(String state) {

        if (state.equalsIgnoreCase("TWO") || state.equalsIgnoreCase("THREE")){

            //fabMainsecond.setVisibility(View.GONE);
            fabMainsecond.hide();
        }

    }

    private void animatedFab() {

        if (isOpen) {

            //fabMain.startAnimation(fabForaward);
            fabFirst.startAnimation(fabClose);
            fabMainsecond.startAnimation(fabClose);
            fabthired.startAnimation(fabClose);
            fab_main_share.startAnimation(fabClose);


            relshare.startAnimation(fabClose);
            relshare_fab_main_thired.startAnimation(fabClose);
            relshare_fab_main_download.startAnimation(fabClose);
            relshare_fab_main_first.startAnimation(fabClose);

            fabFirst.setClickable(false);
            fabMainsecond.setClickable(false);
            fabthired.setClickable(false);
            fab_main_share.setClickable(false);
            isOpen = false;
        } else {
            //fabMain.startAnimation(fabBackward);
            fabFirst.startAnimation(fabOpen);
            fabMainsecond.startAnimation(fabOpen);
            fabthired.startAnimation(fabOpen);
            fab_main_share.startAnimation(fabOpen);

            relshare.startAnimation(fabOpen);
            relshare_fab_main_thired.startAnimation(fabOpen);
            relshare_fab_main_download.startAnimation(fabOpen);
            relshare_fab_main_first.startAnimation(fabOpen);

            fabFirst.setClickable(true);
            fabMainsecond.setClickable(true);
            fabthired.setClickable(true);
            fab_main_share.setClickable(true);
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
                final File filerepost = getItem(globPosition);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                    whatsappIntent.setType("image/*");
                    whatsappIntent.setPackage("com.whatsapp");
                    final File photoFile = new File(Environment.getExternalStorageDirectory().toString() + WHATSAPP_STATUSES_LOCATION, filerepost.getName());
                    Uri photoUri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", photoFile);
                    //whatsappIntent.putExtra(Intent.EXTRA_TEXT, "The text you wanted to share");
                    whatsappIntent.putExtra(Intent.EXTRA_STREAM, photoUri);
                    whatsappIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    try {
                        getApplicationContext().startActivity(whatsappIntent);
                        ToastCustom.setToast(getApplicationContext(),"Repost!");
                    } catch (android.content.ActivityNotFoundException ex) {
                        // ToastHelper.MakeShortText("Whatsapp have not been installed.");
                        ToastCustom.setToast(getApplicationContext(),"Whatsapp have not been installed.");
                    }
                }else {
                    Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                    whatsappIntent.setType("image/*");
                    whatsappIntent.setPackage("com.whatsapp");
                    final File photoFile = new File(Environment.getExternalStorageDirectory().toString() + WHATSAPP_STATUSES_LOCATION, filerepost.getName());
                    //whatsappIntent.putExtra(Intent.EXTRA_TEXT, "The text you wanted to share");
                    whatsappIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(photoFile));
                    whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    whatsappIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    try {
                        getApplicationContext().startActivity(whatsappIntent);
                        ToastCustom.setToast(getApplicationContext(),"Repost!");
                    } catch (android.content.ActivityNotFoundException ex) {
                        // ToastHelper.MakeShortText("Whatsapp have not been installed.");
                        ToastCustom.setToast(getApplicationContext(),"Whatsapp have not been installed.");
                    }
                }
                animatedFab();
                break;
            case R.id.fab_main_download:
                animatedFab();
                final File file = getItem(globPosition);
                try {
                    copyFile(file, new File(Environment.getExternalStorageDirectory().toString() + DIRECTORY_TO_SAVE_MEDIA_NOW + file.getName()));
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("RecyclerV", "onClick: Error:"+e.getMessage() );
                }
                break;
            case R.id.fab_main_thired:
                final File filewallaper = getItem(globPosition);
                Alerts.ShowYesOrNo(this, "Are you sure you want to set it as wallpaper?", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                WallpaperManager myWallpaperManager = WallpaperManager.getInstance(getApplicationContext());
                                Bitmap myBitmap =  BitmapFactory.decodeFile(filewallaper.getAbsolutePath());
                                try {
                                    myWallpaperManager.setBitmap(myBitmap);
                                    ToastCustom.setToast(getApplicationContext(),"Wallpaper set successfully!");
                                } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        },true);




                animatedFab();
                break;

            case R.id.fab_main_share:
                final File fileshare = getItem(globPosition);
                if (fileshare.getName().endsWith(".jpg") || fileshare.getName().endsWith(".png")) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        final Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("image/*");
                        final File photoFile = new File(Environment.getExternalStorageDirectory().toString() + WHATSAPP_STATUSES_LOCATION, fileshare.getName());
                        Uri photoUri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", photoFile);
                        shareIntent.putExtra(Intent.EXTRA_TEXT, "Download Status Saver and Gallery App on - https://play.google.com/store/apps/details?id=android.statussaver.com.statussaver");
                        shareIntent.putExtra(Intent.EXTRA_STREAM, photoUri);
                        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        this.startActivity(Intent.createChooser(shareIntent, "Share image using"));
                    } else {
                        final Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("image/*");
                        final File photoFile = new File(Environment.getExternalStorageDirectory().toString() + WHATSAPP_STATUSES_LOCATION, fileshare.getName());
                        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(photoFile));
                        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        shareIntent.putExtra(Intent.EXTRA_TEXT, "Download Status Saver and Gallery App on - https://play.google.com/store/apps/details?id=android.statussaver.com.statussaver");
                        this.startActivity(Intent.createChooser(shareIntent, "Share image using"));
                    }
                }else {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        final Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("video/*");
                        final File photoFile = new File(Environment.getExternalStorageDirectory().toString() + WHATSAPP_STATUSES_LOCATION, fileshare.getName());
                        Uri photoUri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", photoFile);
                        shareIntent.putExtra(Intent.EXTRA_STREAM, photoUri);
                        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        this.startActivity(Intent.createChooser(shareIntent, "Share Video using"));
                    }else {
                        final Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("video/*");
                        final File photoFile = new File(Environment.getExternalStorageDirectory().toString() + WHATSAPP_STATUSES_LOCATION, fileshare.getName());
                        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(photoFile));
                        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        this.startActivity(Intent.createChooser(shareIntent, "Share Video using"));
                    }
                }
                animatedFab();
                break;

            case R.id.rel_close:
                finish();
                break;
        }
    }

    private void copyFile(File sourceFile, File file) throws IOException {
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();

        if (!file.exists()) {
            file.createNewFile();
            ToastCustom.setToast(getApplicationContext(),"Downloaded");
        }else {
            ToastCustom.setToast(getApplicationContext(),"It's already exist!");
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
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        mScaleGestureDetector.onTouchEvent(event);
//        return true;
//    }

    @Override
    public void onBackPressed() {
        finish();
    }


//    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
//        @Override
//        public boolean onScale(ScaleGestureDetector scaleGestureDetector){
//            mScaleFactor *= scaleGestureDetector.getScaleFactor();
//            mScaleFactor = Math.max(0.1f,
//                    Math.min(mScaleFactor, 10.0f));
//            imageView.setScaleX(mScaleFactor);
//            imageView.setScaleY(mScaleFactor);
//            return true;
//        }
//    }

    public File getItem(int position) {
        return documents.get(position);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        globPosition =position;
        try {
            tvCurrentItem.setText((position + 1) + "/" + documents.size());
        } catch (Exception e) {
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
