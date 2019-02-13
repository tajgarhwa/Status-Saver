package android.statussaver.com.statussaver.fragments;

import android.os.Bundle;
import android.os.Environment;
import android.statussaver.com.statussaver.BaseCompare;
import android.statussaver.com.statussaver.adapters.Stories.StoriesAdapterImage;
import android.statussaver.com.statussaver.models.Status;
import android.statussaver.com.statussaver.utils.ToastCustom;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.statussaver.com.statussaver.R;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class ImageFragment extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    private TextView tvImage;
    private ArrayList<Status> statusList;
    private StoriesAdapterImage recyclerviewAdapter;
    private static final int COUNT = 2;
    private static final String WHATSAPP_STATUSES_LOCATION = "/WhatsApp/Media/.Statuses";
    private static final String WHATSAPP_STATUSES_LOCATION_SEND = "/WhatsApp/Media/WhatsApp Images/Sent";
    private static final String WHATSAPP_STATUSES_LOCATION_RECIVED = "/WhatsApp/Media/WhatsApp Images";

    private FloatingActionButton fabMain, fabFirst, fabMainsecond, fabthired;
    private RelativeLayout relFirst,relSecond,relThired;
    private Animation fabOpen, fabClose, fabForaward, fabBackward;
    private boolean isOpen = false;

    public ImageFragment() {
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
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        fabMain = view.findViewById(R.id.fab_main);
        fabFirst = view.findViewById(R.id.fab_main_first);
        fabMainsecond = view.findViewById(R.id.fab_main_download);
        fabthired = view.findViewById(R.id.fab_main_thired);
        tvImage = view.findViewById(R.id.tvImage);
        relFirst =view.findViewById(R.id.relFirst);
        relSecond =view.findViewById(R.id.relSecond);
        relThired =view.findViewById(R.id.relThired);

        fabOpen = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_close);
        fabForaward = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_forward);
        fabBackward = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_backward);

        fabMain.setOnClickListener(this);
        fabFirst.setOnClickListener(this);
        fabMainsecond.setOnClickListener(this);
        fabthired.setOnClickListener(this);

        setStatusList();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fabMain.getVisibility() == View.VISIBLE) {
                    fabMain.hide();
                } else if (dy < 0 && fabMain.getVisibility() != View.VISIBLE) {
                    fabMain.show();
                }
            }
        });
        return view;
    }

    private void animatedFab() {

        if (isOpen) {

            fabMain.startAnimation(fabForaward);
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
            fabMain.startAnimation(fabBackward);
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
                        //tvImage.setVisibility(View.VISIBLE);
                    }
                }
            } else {
                tvImage.setVisibility(View.VISIBLE);
            }
        }else {

            Toast.makeText(getActivity(),"Thire is any directory ",Toast.LENGTH_SHORT).show();
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

        recyclerviewAdapter = new StoriesAdapterImage(this.getListFiles(new File(Environment.getExternalStorageDirectory().toString() + whatsapp_loaction)), getActivity(), status);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(COUNT, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(recyclerviewAdapter);
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
                initSetList(WHATSAPP_STATUSES_LOCATION_SEND, "THREE");
                ToastCustom.setToast(getActivity(), "WhatsApp Sent Images..");
                break;
            case R.id.fab_main_download:
                animatedFab();
                initSetList(WHATSAPP_STATUSES_LOCATION_RECIVED, "TWO");
                ToastCustom.setToast(getActivity(), "WhatsApp Received Images..");
                break;
            case R.id.fab_main_thired:
                animatedFab();
                initSetList(WHATSAPP_STATUSES_LOCATION, "ONE");
                ToastCustom.setToast(getActivity(), "WhatsApp Stories Images..");
                break;
        }

    }
}