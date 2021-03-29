package com.statussaver.chamiappslk.statussaver.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.statussaver.com.statussaver.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.statussaver.chamiappslk.statussaver.BaseCompare;
import com.statussaver.chamiappslk.statussaver.adapters.Stories.StoriesAdapterVideo;
import com.statussaver.chamiappslk.statussaver.models.Status;
import com.statussaver.chamiappslk.statussaver.utils.ToastCustom;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class VideoFragment extends Fragment implements View.OnClickListener {

//    private RecyclerView recyclerView;
//    private TextView tvVideo;
//    private ArrayList<Status> statusList;
//    private StoriesAdapterVideo recyclerviewAdapter;
//    private static final int COUNT = 2;
//    //private static final String WHATSAPP_STATUSES_LOCATION = "/WhatsApp/Media/WhatsApp Video";
//    private static final String WHATSAPP_STATUSES_LOCATION = "/WhatsApp/Media/.Statuses";
//    private static final String WHATSAPP_STATUSES_LOCATION_SEND = "/WhatsApp/Media/WhatsApp Video/Sent";
//    private static final String WHATSAPP_STATUSES_LOCATION_RECIVED = "/WhatsApp/Media/WhatsApp Video";
//    Handler handler = new Handler();
//
//    private FloatingActionButton fabMain, fabFirst, fabMainsecond, fabthired;
//    private RelativeLayout relFirst,relSecond,relThired;
//    private Animation fabOpen, fabClose, fabForaward, fabBackward;
//    private boolean isOpen = false;
//
//
//    public VideoFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_video, container, false);
//        recyclerView = view.findViewById(R.id.recyclerview);
//       // tvVideo = view.findViewById(R.id.tvVideo);
//
//        fabMain = view.findViewById(R.id.fab_main);
//        fabFirst = view.findViewById(R.id.fab_main_first);
//        fabMainsecond = view.findViewById(R.id.fab_main_download);
//        fabthired = view.findViewById(R.id.fab_main_thired);
//        tvVideo = view.findViewById(R.id.tvVideo);
//        relFirst =view.findViewById(R.id.relFirst);
//        relSecond =view.findViewById(R.id.relSecond);
//        relThired =view.findViewById(R.id.relThired);
//
//        fabOpen = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_open);
//        fabClose = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_close);
//        fabForaward = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_forward);
//        fabBackward = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_backward);
//
//        fabMain.setOnClickListener(this);
//        fabFirst.setOnClickListener(this);
//        fabMainsecond.setOnClickListener(this);
//        fabthired.setOnClickListener(this);
//
//        setStatusList();
//
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (dy > 0 && fabMain.getVisibility() == View.VISIBLE) {
//                    fabMain.hide();
//                } else if (dy < 0 && fabMain.getVisibility() != View.VISIBLE) {
//                    fabMain.show();
//                }
//            }
//        });
//        return view;
//
//    }
//
//    private void setStatusList() {
//
//        statusList = new ArrayList<>();
//        statusList.add(new Status(1, "https://i.ytimg.com/vi/1Ltt4IN1QAQ/maxresdefault.jpg"));
//        statusList.add(new Status(2, "https://cdn.pastemagazine.com/www/articles/2018/08/15/redbox-2018.jpg"));
//        statusList.add(new Status(3, "https://cdn3.movieweb.com/i/movie/1vf613jMI8Ni7ceCPolymZecDPhwVR/384:50/The-Greatest-Showman.jpg"));
//        statusList.add(new Status(4, "https://cdn3.movieweb.com/i/movie/lGg2fMgeBi6yswQpkdBicBtCT2V8D2/384:50/The-Maze-Runner-The-Death-Cure.jpg"));
//        statusList.add(new Status(5, "https://images.summitmedia-digital.com/spotph/images/2018/01/02/2018-must-see-movies.jpg"));
//        statusList.add(new Status(6, "http://knowitandshareit.com/wp-content/uploads/2018/02/Best-Of-january-2018-movies.jpg"));
//        statusList.add(new Status(7, "https://i.pinimg.com/originals/15/24/28/15242875db07ad637cb9c2b68f845778.jpg"));
//        statusList.add(new Status(8, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRsbAEF645n8ljncfEphDnVKMbvgDTH22eXOyFAx9mY3OngURuo"));
//        statusList.add(new Status(9, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRF3EB6H1qkNq7X4HlkZK1bdTmR88hPPSSvvb8aOC5eWuvpKt2M6w"));
//        statusList.add(new Status(10, "http://knowitandshareit.com/wp-content/uploads/2018/02/Best-Of-january-2018-movies.jpg"));
//        initSetList(WHATSAPP_STATUSES_LOCATION,"ONE");
//
//    }
//
//    private ArrayList<File> getListFiles(File parentDir) {
//        final ArrayList<File> inFiles = new ArrayList<File>();
//        File[] files;
//        files = parentDir.listFiles();
//        Arrays.sort(files, new BaseCompare.compare());
//        int i = 0;
//        if (files != null) {
//            for (final File file : files) {
//                if (file.getName().endsWith(".mp4") || file.getName().endsWith(".3gp") || file.getName().endsWith(".mov")) {
//                    if (!inFiles.contains(file)) {
//                        i++;
//                        Log.e("file name"+" "+i, file.getAbsolutePath());
//                        inFiles.add(file);
//                    }
//
//                }else {
//                   // tvVideo.setVisibility(View.VISIBLE);
//                   // Log.e("Files", "No files");
//                   // Toast.makeText(getActivity(), "No video"+file.getAbsolutePath(), Toast.LENGTH_LONG).show();
//                }
//            }
//        } else {
//            tvVideo.setVisibility(View.VISIBLE);
//            Log.e("Files", "No files");
//
//        }
//        return inFiles;
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
//            relFirst.startAnimation(fabClose);
//            relSecond.startAnimation(fabClose);
//            relThired.startAnimation(fabClose);
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
//            relFirst.startAnimation(fabOpen);
//            relSecond.startAnimation(fabOpen);
//            relThired.startAnimation(fabOpen);
//
//            fabFirst.setClickable(true);
//            fabMainsecond.setClickable(true);
//            fabthired.setClickable(true);
//            isOpen = true;
//
//        }
//    }
//
//    public Uri getImageUri(Context inContext, Bitmap inImage) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
//        return Uri.parse(path);
//    }
//
//    private void initSetList(String whatsapp_loaction,String status) {
//
//        recyclerviewAdapter = new StoriesAdapterVideo(this.getListFiles(new File(Environment.getExternalStorageDirectory().toString() + whatsapp_loaction)), getActivity(),status);
//        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(COUNT, LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(staggeredGridLayoutManager);
//        recyclerView.setAdapter(recyclerviewAdapter);
//    }
//
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//
//    }
//
//    @Override
//    public void onClick(View v) {
//
//        switch (v.getId()) {
//            case R.id.fab_main:
//                animatedFab();
//                break;
//            case R.id.fab_main_first:
//                animatedFab();
//                initSetList(WHATSAPP_STATUSES_LOCATION_SEND,"THREE");
//                ToastCustom.setToast(getActivity(), "WhatsApp Sent Images..");
//                break;
//            case R.id.fab_main_download:
//                animatedFab();
//                initSetList(WHATSAPP_STATUSES_LOCATION_RECIVED,"TWO");
//                ToastCustom.setToast(getActivity(), "WhatsApp Received Images..");
//                break;
//            case R.id.fab_main_thired:
//                animatedFab();
//                initSetList(WHATSAPP_STATUSES_LOCATION,"ONE");
//                ToastCustom.setToast(getActivity(), "WhatsApp Stories Images..");
//                break;
//        }
//
//    }


    private RecyclerView recyclerView;
    private TextView tvImage;
    private ImageView img_imoji;
    private ArrayList<Status> statusList;
    private StoriesAdapterVideo recyclerviewAdapter;
    private static final int COUNT = 2;
    private static final String WHATSAPP_STATUSES_LOCATION = "/WhatsApp/Media/.Statuses";
    private static final String WHATSAPP_STATUSES_LOCATION_SEND = "/WhatsApp/Media/WhatsApp Video/Sent";
    private static final String WHATSAPP_STATUSES_LOCATION_RECIVED = "/WhatsApp/Media/WhatsApp Video";

    private FloatingActionButton fabMain, fabFirst, fabMainsecond, fabthired;
    private RelativeLayout relFirst, relSecond, relThired;
    private Animation fabOpen, fabClose, fabForaward, fabBackward;
    private boolean isOpen = false;
    private int tag = -1;

    AsyncTaskRunner3 task3;
    ProgressBar progressDialog;
    ArrayList<File> inFiles = new ArrayList<File>();

    public VideoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        fabMain = view.findViewById(R.id.fab_main);
        fabFirst = view.findViewById(R.id.fab_main_first);
        fabMainsecond = view.findViewById(R.id.fab_main_download);
        fabthired = view.findViewById(R.id.fab_main_thired);
        tvImage = view.findViewById(R.id.tvImage);
        img_imoji = view.findViewById(R.id.img_imoji);
        relFirst = view.findViewById(R.id.relFirst);
        relSecond = view.findViewById(R.id.relSecond);
        relThired = view.findViewById(R.id.relThired);

        progressDialog = view.findViewById(R.id.progres_main);

        fabOpen = AnimationUtils.loadAnimation(getActivity(),R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getActivity(),R.anim.fab_close);
        fabForaward = AnimationUtils.loadAnimation(getActivity(),R.anim.rotate_forward);
        fabBackward = AnimationUtils.loadAnimation(getActivity(),R.anim.rotate_backward);

        fabMain.setOnClickListener(this);
        fabFirst.setOnClickListener(this);
        fabMainsecond.setOnClickListener(this);
        fabthired.setOnClickListener(this);

        // setStatusList();

        inFiles.clear();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                task3 = new AsyncTaskRunner3();
                task3.execute();
            }
        }, 500);

//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (dy > 0 && fabMain.getVisibility() == View.VISIBLE) {
//                    fabMain.hide();
//                } else if (dy < 0 && fabMain.getVisibility() != View.VISIBLE) {
//                    fabMain.show();
//                }
//            }
//        });


//        recyclerView.addOnScrollListener(new HideShowScrollListener() {
//            @Override
//            public void onHide() {
//                fabMain.hide();
//                ((MainActivity)getActivity()).hideBottomNavigationMenu(true);
//                if (isOpen){
//                    //fabMain.startAnimation(fabForaward);
//                    fabFirst.startAnimation(fabClose);
//                    fabMainsecond.startAnimation(fabClose);
//                    fabthired.startAnimation(fabClose);
//
//                    relFirst.startAnimation(fabClose);
//                    relSecond.startAnimation(fabClose);
//                    relThired.startAnimation(fabClose);
//
//                    fabFirst.setClickable(false);
//                    fabMainsecond.setClickable(false);
//                    fabthired.setClickable(false);
//                    isOpen = false;
//                }
//            }
//
//            @Override
//            public void onShow() {
//                fabMain.show();
//                ((MainActivity)getActivity()).hideBottomNavigationMenu(false);
//            }
//        });
        return view;
    }

    private void animatedFab() {

        if (isOpen) {

            //fabMain.startAnimation(fabForaward);
            fabFirst.startAnimation(fabClose);
            fabMainsecond.startAnimation(fabClose);
            fabthired.startAnimation(fabClose);

            relFirst.startAnimation(fabClose);
            relSecond.startAnimation(fabClose);
            relThired.startAnimation(fabClose);

            fabFirst.setClickable(false);
            fabMainsecond.setClickable(false);
            fabthired.setClickable(false);
            isOpen = false;
        } else {
            //fabMain.startAnimation(fabBackward);
            fabFirst.startAnimation(fabOpen);
            fabMainsecond.startAnimation(fabOpen);
            fabthired.startAnimation(fabOpen);

            relFirst.startAnimation(fabOpen);
            relSecond.startAnimation(fabOpen);
            relThired.startAnimation(fabOpen);

            fabFirst.setClickable(true);
            fabMainsecond.setClickable(true);
            fabthired.setClickable(true);
            isOpen = true;

        }
    }


    private ArrayList<File> getListFiles(File parentDir) {

        File[] files;
        if (parentDir.exists() && parentDir.isDirectory()) {
            files = parentDir.listFiles();
            Arrays.sort(files, new BaseCompare.compare());
            int i = 0;
            if (files.length !=0) {
                for (File file : files) {
                    if (file.getName().endsWith(".mp4") || file.getName().endsWith(".3gp") || file.getName().endsWith(".mov")) {
                        if (!inFiles.contains(file)) {
                            i++;
                            Log.e("file name" + " " + i, file.getAbsolutePath());
                            inFiles.add(file);
                        }

                    } else {
                        // tvImage.setVisibility(View.VISIBLE);
                        Log.e("Files", "No files");
                        //Toast.makeText(getActivity(),"No gif",Toast.LENGTH_LONG).show();
                    }
                }
            } else {
               // tvImage.setVisibility(View.VISIBLE);
            }
        } else {

           //Toast.makeText(getActivity(), "There is any directory ", Toast.LENGTH_SHORT).show();
        }
        return inFiles;
    }


    private void setStatusList() {

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
        initSetList(WHATSAPP_STATUSES_LOCATION, "ONE");

    }


    private void initSetList(String whatsapp_loaction, String status) {
        this.getListFiles(new File(Environment.getExternalStorageDirectory().toString() + whatsapp_loaction));

    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fab_main:
                animatedFab();
                break;
            case R.id.fab_main_first:
                animatedFab();
//                initSetList(WHATSAPP_STATUSES_LOCATION_SEND, "THREE");
//                ToastCustom.setToast(getActivity(), "WhatsApp Sent Images..");
                inFiles.clear();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AsyncTaskRunner2 task2 = new AsyncTaskRunner2();
                        task2.execute();
                    }
                }, 500);
                break;
            case R.id.fab_main_download:
                animatedFab();
                inFiles.clear();
                //initSetList(WHATSAPP_STATUSES_LOCATION_RECIVED, "TWO");
                // ToastCustom.setToast(getActivity(), "WhatsApp Received Images..");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AsyncTaskRunner task = new AsyncTaskRunner();
                        task.execute();
                    }
                }, 500);

                break;
            case R.id.fab_main_thired:
                animatedFab();
                inFiles.clear();
//                initSetList(WHATSAPP_STATUSES_LOCATION, "ONE");
//                ToastCustom.setToast(getActivity(), "WhatsApp Stories Images..");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        task3 = new AsyncTaskRunner3();
                        task3.execute();

                    }
                }, 500);
                break;
        }

    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            if (inFiles.size() == 0) {
                tvImage.setVisibility(View.INVISIBLE);
                img_imoji.setVisibility(View.INVISIBLE);
            }
            initSetList(WHATSAPP_STATUSES_LOCATION_RECIVED, "TWO");
            return "";
        }


        @Override
        protected void onPostExecute(String result) {

            int resId = R.anim.layout_animation_slide_down;
            recyclerviewAdapter = new StoriesAdapterVideo(inFiles, getActivity(), "TWO");
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(COUNT, LinearLayoutManager.VERTICAL);

            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getActivity(), resId);
            recyclerView.setLayoutAnimation(animation);
            recyclerView.setLayoutManager(staggeredGridLayoutManager);
            recyclerView.setAdapter(recyclerviewAdapter);


            progressDialog.setVisibility(View.GONE);
            ToastCustom.setToast(getActivity(), "WhatsApp Received Images..");

            if (inFiles.size() == 0) {
                tvImage.setVisibility(View.VISIBLE);
                img_imoji.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onProgressUpdate(String... text) {

        }
    }

    private class AsyncTaskRunner2 extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            if (inFiles.size() == 0) {
                tvImage.setVisibility(View.INVISIBLE);
                img_imoji.setVisibility(View.INVISIBLE);

            }
            initSetList(WHATSAPP_STATUSES_LOCATION_SEND, "THREE");
            return "";
        }


        @Override
        protected void onPostExecute(String result) {


            int resId = R.anim.layout_animation_slide_down;
            recyclerviewAdapter = new StoriesAdapterVideo(inFiles, getActivity(), "THREE");
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(COUNT, LinearLayoutManager.VERTICAL);

            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getActivity(), resId);
            recyclerView.setLayoutAnimation(animation);
            recyclerView.setLayoutManager(staggeredGridLayoutManager);
            recyclerView.setAdapter(recyclerviewAdapter);

            progressDialog.setVisibility(View.GONE);
            ToastCustom.setToast(getActivity(), "WhatsApp Sent Images..");

            if (inFiles.size() == 0) {
                tvImage.setVisibility(View.VISIBLE);
                img_imoji.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected void onPreExecute() {

            progressDialog.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onProgressUpdate(String... text) {

        }
    }


    private class AsyncTaskRunner3 extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {

            if (inFiles.size() == 0) {
                tvImage.setVisibility(View.INVISIBLE);
                img_imoji.setVisibility(View.INVISIBLE);
            }
            initSetList(WHATSAPP_STATUSES_LOCATION, "ONE");


            return "";
        }


        @Override
        protected void onPostExecute(String result) {

            int resId = R.anim.layout_animation_slide_down;
            recyclerviewAdapter = new StoriesAdapterVideo(inFiles, getActivity(), "ONE");
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(COUNT, LinearLayoutManager.VERTICAL);

            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getActivity(), resId);
            recyclerView.setLayoutAnimation(animation);
            recyclerView.setLayoutManager(staggeredGridLayoutManager);
            recyclerView.setAdapter(recyclerviewAdapter);

            progressDialog.setVisibility(View.GONE);
            ToastCustom.setToast(getActivity(), "WhatsApp Stories Images..");

            if (inFiles.size() == 0) {

                tvImage.setVisibility(View.VISIBLE);
                img_imoji.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onProgressUpdate(String... text) {

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("Tag", "ImageFragment.onDestroyView() has been called.");
    }


}
