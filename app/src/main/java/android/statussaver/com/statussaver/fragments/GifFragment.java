package android.statussaver.com.statussaver.fragments;

import android.os.Bundle;
import android.os.Environment;
import android.statussaver.com.statussaver.BaseCompare;
import android.statussaver.com.statussaver.adapters.Stories.StoriesAdapterGif;
import android.statussaver.com.statussaver.models.Status;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.statussaver.com.statussaver.R;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class GifFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView tvVideo;
    private ArrayList<Status> statusList;
    private StoriesAdapterGif recyclerviewAdapter;
    private static final int COUNT = 2;
    private static final String WHATSAPP_STATUSES_LOCATION = "/WhatsApp/Media/WhatsApp Animated Gifs";

    public GifFragment() {
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
        View view = inflater.inflate(R.layout.fragment_gif, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        tvVideo = view.findViewById(R.id.tvVideo);

        setStatusList();
        return view;

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
        initSetList();

    }

    private ArrayList<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<File>();
        File[] files;
        files = parentDir.listFiles();
        Arrays.sort(files, new BaseCompare.compare());
        int i = 0;
        if (files != null) {
            for (File file : files) {

                if (file.getName().endsWith(".mp4") || file.getName().endsWith(".gif")) {
                    if (!inFiles.contains(file)) {
                        i++;
                        Log.e("file name" + " " + i, file.getAbsolutePath());
                        inFiles.add(file);
                    }

                } else {
                    tvVideo.setVisibility(View.VISIBLE);
                    Log.e("Files", "No files");
                    Toast.makeText(getActivity(),"No gif",Toast.LENGTH_LONG).show();
                }
            }
        } else {


        }
        return inFiles;
    }

    private void initSetList() {

        recyclerviewAdapter = new StoriesAdapterGif(this.getListFiles(new File(Environment.getExternalStorageDirectory().toString() + WHATSAPP_STATUSES_LOCATION)), getActivity());
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(COUNT, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(recyclerviewAdapter);
    }


    @Override
    public void onDetach() {
        super.onDetach();

    }
}
