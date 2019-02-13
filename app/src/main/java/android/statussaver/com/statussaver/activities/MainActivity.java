package android.statussaver.com.statussaver.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;
import android.statussaver.com.statussaver.R;
import android.statussaver.com.statussaver.adapters.RoundRecyclerviewAdapter;
import android.statussaver.com.statussaver.BaseCompare;
import android.statussaver.com.statussaver.fragments.GifFragment;
import android.statussaver.com.statussaver.fragments.ImageFragment;
import android.statussaver.com.statussaver.fragments.InfoFragment;
import android.statussaver.com.statussaver.fragments.SaveFragment;
import android.statussaver.com.statussaver.fragments.VideoFragment;
import android.statussaver.com.statussaver.models.Status;
import android.statussaver.com.statussaver.service.MediaListenerService;
import android.statussaver.com.statussaver.service.StatusListenerService;
import android.statussaver.com.statussaver.utils.ToastCustom;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int COUNT = 2;
    private RecyclerView recyclerView, reclerMain;
    private RoundRecyclerviewAdapter recyclerviewAdapter;
    private ArrayList<Status> statusList;
    private static final String WHATSAPP_STATUSES_LOCATION = "/WhatsApp/Media/.Statuses";

    private static final int REQUEST_PERMISSION = 10;
    private int backCount =0;

    private static final String JOB_TAG="my_job_tag";
    private FirebaseJobDispatcher jobDispatcher;



    private ImageView imgImage, imgVideo, imgGif, imgSave, imgInfo;
    private RelativeLayout RelImgImage, RelImgVideo, RelImgGif, RelImgSave, RelImgInfo, relRecylerHori;
    private TextView tvrecentStories;

    private ImageFragment imageFragment;
    private VideoFragment videoFragment;
    private GifFragment gifFragment;
    private SaveFragment saveFragment;
    private InfoFragment infoFragment;
    private DrawerLayout drawerLayout;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    MediaPlayer player;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jobDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));

        reclerMain = findViewById(R.id.reclerMain);
        relRecylerHori = findViewById(R.id.relRecylerHori);
        tvrecentStories = findViewById(R.id.tvrecentStories);
        drawerLayout = findViewById(R.id.drawerLayout);
        //imageview
        imgImage = findViewById(R.id.imgImage);
        imgVideo = findViewById(R.id.imgVideo);
        imgGif = findViewById(R.id.imgGif);
        imgSave = findViewById(R.id.imgSave);
        imgInfo = findViewById(R.id.imgInfo);

        //relativelayout
        RelImgImage = findViewById(R.id.RelImgImage);
        RelImgVideo = findViewById(R.id.RelImgVideo);
        RelImgGif = findViewById(R.id.RelImgGif);
        RelImgSave = findViewById(R.id.RelImgSave);
        RelImgInfo = findViewById(R.id.RelImgInfo);

        RelImgImage.setOnClickListener(this);
        RelImgVideo.setOnClickListener(this);
        RelImgGif.setOnClickListener(this);
        RelImgSave.setOnClickListener(this);
        RelImgInfo.setOnClickListener(this);


//        recyclerView = findViewById(R.id.recyclerview);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        setStatusList();
        initSetList();
        setButtonState();

//        requestAppPermissions(new String[]{
//                        Manifest.permission.READ_CONTACTS,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        Manifest.permission.WRITE_CONTACTS},
//                R.string.msg,REQUEST_PERMISSION);

    }
//
//    @Override
//    public void onPermissionsGranted(int requestCode) {
//        Toast.makeText(getApplicationContext(), "Permission granted"+requestCode, Toast.LENGTH_LONG).show();
//    }

    private void setStatusList() {

        Intent myIntent = new Intent(getApplicationContext(), MediaListenerService.class);
        ContextCompat.startForegroundService(this,myIntent);

//        Job job = jobDispatcher.newJobBuilder().
//                setService(StatusListenerService.class).
//                setLifetime(Lifetime.FOREVER).
//                setRecurring(true).
//                setTag(JOB_TAG).
//                setTrigger(Trigger.executionWindow(0,5)).
//                setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL).
//                setReplaceCurrent(false)
//                .setConstraints(Constraint.ON_UNMETERED_NETWORK)
//                .build();
//
//         jobDispatcher.mustSchedule(job);
//
//         Toast.makeText(this,"Job Sheduled",Toast.LENGTH_LONG).show();


        statusList = new ArrayList<>();
        statusList.add(new Status(1, "https://i.ytimg.com/vi/1Ltt4IN1QAQ/maxresdefault.jpg"));
        statusList.add(new Status(2, "https://cdn.pastemagazine.com/www/articles/2018/08/15/redbox-2018.jpg"));
        statusList.add(new Status(3, "https://cdn3.movieweb.com/i/movie/1vf613jMI8Ni7ceCPolymZecDPhwVR/384:50/The-Greatest-Showman.jpg"));
        statusList.add(new Status(4, "https://cdn3.movieweb.com/i/movie/lGg2fMgeBi6yswQpkdBicBtCT2V8D2/384:50/The-Maze-Runner-The-Death-Cure.jpg"));
        statusList.add(new Status(5, "https://images.summitmedia-digital.com/spotph/images/2018/01/02/2018-must-see-movies.jpg"));
        statusList.add(new Status(6, "http://knowitandshareit.com/wp-content/uploads/2018/02/Best-Of-january-2018-movies.jpg"));
        statusList.add(new Status(7, "https://i.pinimg.com/originals/15/24/28/15242875db07ad637cb9c2b68f845778.jpg"));
        statusList.add(new Status(8, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRsbAEF645n8ljncfEphDnVKMbvgDTH22eXOyFAx9mY3OngURuo"));
        statusList.add(new Status(9, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRF3EB6H1qkNq7X4HlkZK1bdTmR88hPPSSvvb8aOC5eWuvpKt2M6w"));
        statusList.add(new Status(10, "http://knowitandshareit.com/wp-content/uploads/2018/02/Best-Of-january-2018-movies.jpg"));
       // initSetList();

    }


    private void initSetList() {

        recyclerviewAdapter = new RoundRecyclerviewAdapter(this.getListFiles(new File(Environment.getExternalStorageDirectory().toString() + WHATSAPP_STATUSES_LOCATION)), this);
        //StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(COUNT, LinearLayoutManager.VERTICAL);
        reclerMain.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        reclerMain.setAdapter(recyclerviewAdapter);


    }

    private ArrayList<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<File>();
        File[] files;
        if (parentDir.exists() && parentDir.isDirectory()) {
            files = parentDir.listFiles();
            Arrays.sort(files, new BaseCompare.compare());
            int i = 0;
            if (files != null) {
                for (File file : files) {
                    if (file.getName().endsWith(".jpg") || file.getName().endsWith(".png")) {
                        if (!inFiles.contains(file)) {
                            i++;
                            Log.e("file name" + " " + i, file.getAbsolutePath());
                            inFiles.add(file);
                        }
                    } else {
                        relRecylerHori.setVisibility(View.GONE);
                        tvrecentStories.setVisibility(View.GONE);
                    }
                }
            } else {
                //tvImage.setVisibility(View.VISIBLE);
            }

        }else {
            Toast.makeText(getApplicationContext(),"Thire is any directory ",Toast.LENGTH_SHORT).show();
        }
        return inFiles;
    }

    private ImageFragment getImageFragment() {
        if (imageFragment == null) {
            imageFragment = new ImageFragment();
        }
        return imageFragment;
    }

    public VideoFragment getVideoFragment() {
        if (videoFragment == null) {
            videoFragment = new VideoFragment();
        }
        return videoFragment;
    }

    public GifFragment getGifFragment() {
        if (gifFragment == null) {
            gifFragment = new GifFragment();
        }
        return gifFragment;
    }

    public SaveFragment getSaveFragment() {
        if (saveFragment == null) {
            saveFragment = new SaveFragment();
        }
        return saveFragment;
    }

    public InfoFragment getInfoFragment() {
        if (infoFragment == null) {
            infoFragment = new InfoFragment();
        }
        return infoFragment;
    }

    protected void showSelectedFragment(@NonNull Fragment fragment) {
        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }



    public void setButtonState() {

        showSelectedFragment(getImageFragment());
        imgImage.setImageResource(R.drawable.ic_action_image_selected);
        imgVideo.setImageResource(R.drawable.ic_action_video);
        imgGif.setImageResource(R.drawable.ic_action_gif);
        imgSave.setImageResource(R.drawable.ic_action_save);
        imgInfo.setImageResource(R.drawable.item_drawer);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.RelImgImage:

                showSelectedFragment(getImageFragment());

                imgImage.setImageResource(R.drawable.ic_action_image_selected);
                imgVideo.setImageResource(R.drawable.ic_action_video);
                imgGif.setImageResource(R.drawable.ic_action_gif);
                imgSave.setImageResource(R.drawable.ic_action_save);
                imgInfo.setImageResource(R.drawable.ic_action_info);

                break;

            case R.id.RelImgVideo:
                showSelectedFragment(getVideoFragment());

                imgImage.setImageResource(R.drawable.ic_action_image);
                imgVideo.setImageResource(R.drawable.ic_action_video_selected);
                imgGif.setImageResource(R.drawable.ic_action_gif);
                imgSave.setImageResource(R.drawable.ic_action_save);
                imgInfo.setImageResource(R.drawable.ic_action_info);

                break;

            case R.id.RelImgGif:

                showSelectedFragment(getGifFragment());

                imgImage.setImageResource(R.drawable.ic_action_image);
                imgVideo.setImageResource(R.drawable.ic_action_video);
                imgGif.setImageResource(R.drawable.ic_action_gif_selected);
                imgSave.setImageResource(R.drawable.ic_action_save);
                imgInfo.setImageResource(R.drawable.ic_action_info);
                break;

            case R.id.RelImgSave:

                showSelectedFragment(getSaveFragment());

                imgImage.setImageResource(R.drawable.ic_action_image);
                imgVideo.setImageResource(R.drawable.ic_action_video);
                imgGif.setImageResource(R.drawable.ic_action_gif);
                imgSave.setImageResource(R.drawable.ic_action_save_selected);
                imgInfo.setImageResource(R.drawable.ic_action_info);

                break;

            case R.id.RelImgInfo:
                drawerLayout.openDrawer(Gravity.RIGHT);
                //showSelectedFragment(getInfoFragment());

                imgImage.setImageResource(R.drawable.ic_action_image);
                imgVideo.setImageResource(R.drawable.ic_action_video);
                imgGif.setImageResource(R.drawable.ic_action_gif);
                imgSave.setImageResource(R.drawable.ic_action_save);
                imgInfo.setImageResource(R.drawable.item_drawer_selected);

                break;


        }

    }

//    @Override
//    public void onBackPressed() {
////        if (fragmentManager.getBackStackEntryCount() > 1) {
////            //moveTaskToBack(false);
////            //showSelectedFragment(getImageFragment());
////            super.onBackPressed();
////            //fragmentManager.popBackStack();
////        } else {
////            //fragmentManager.popBackStack();
////            //super.onBackPressed();
////            //moveTaskToBack(false);
////            finish();
////        }
//    }

    @Override
    public void onBackPressed() {
//        if (fragmentManager.getBackStackEntryCount() == 1) {
//            moveTaskToBack(false);
//        } else {
//            super.onBackPressed();
//        }
     showExitPrompt();
    }

    Runnable runnableExit = new Runnable() {
        @Override
        public void run() {
            backCount = 0;
        }
    };
    Handler handlerExit = new Handler();


    public void showExitPrompt() {
        ++backCount;
        handlerExit.postDelayed(runnableExit, 5000);
        if (backCount >= 2) {
            handlerExit.removeCallbacks(runnableExit);
            this.finish();
        } else {
            ToastCustom.setToast(MainActivity.this, "Tap Again to Exit.");
        }

    }




}
