package android.statussaver.com.statussaver.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.statussaver.com.statussaver.BaseCompare;
import android.statussaver.com.statussaver.DialogFragment.DeleteDialogFragment;
import android.statussaver.com.statussaver.DialogFragment.ExitDialogFragment;
import android.statussaver.com.statussaver.DialogFragment.ShareDialogFragment;
import android.statussaver.com.statussaver.activities.ImageViewActivity;
import android.statussaver.com.statussaver.activities.MainActivity;
import android.statussaver.com.statussaver.adapters.Stories.StoriesAdapterSave;
import android.statussaver.com.statussaver.models.Status;
import android.statussaver.com.statussaver.utils.Alerts;
import android.statussaver.com.statussaver.utils.HideShowScrollListener;
import android.statussaver.com.statussaver.utils.RecyclerItemClickListener;
import android.statussaver.com.statussaver.utils.ToastCustom;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.statussaver.com.statussaver.R;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SaveFragment extends Fragment implements View.OnClickListener, ShareDialogFragment.MyDialogListener,DeleteDialogFragment.MyDialogDeleteListener {

    private RecyclerView recyclerView;
    private TextView tvImage;
    private ImageView img_imoji;
    private ArrayList<Status> statusList;
    private StoriesAdapterSave recyclerviewAdapter;
    private static final int COUNT = 2;
    private static final String WHATSAPP_STATUSES_LOCATION = "/Status_Saver";
    private FloatingActionButton fabMain, fabFirst, fabMainsecond, fabthired;
    private RelativeLayout relFirst, relSecond, relThired;
    private Animation fabOpen, fabClose, fabForaward, fabBackward;
    private boolean isOpen = false;
    private int tag = -1;

    AsyncTaskRunner3 task3;
    ProgressBar progressDialog;
    ArrayList<File> inFiles = new ArrayList<File>();

    private ActionMode actionMode;
    private boolean isMultiSelect = false;
    private List<File> selectedIds = new ArrayList<>();
    private static final String DIRECTORY_TO_SAVE_MEDIA_NOW = "/Status_Saver/";
    private Uri photoUri;

    public SaveFragment() {
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
        View view = inflater.inflate(R.layout.fragment_save, container, false);
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

        fabOpen = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_close);
        fabForaward = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_forward);
        fabBackward = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_backward);

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
//                    ((MainActivity)getActivity()).hideBottomNavigationMenu(true);
//                } else if (dy < 0 && fabMain.getVisibility() != View.VISIBLE) {
//                    fabMain.show();
//                    ((MainActivity)getActivity()).hideBottomNavigationMenu(false);
//                }
//            }
//        });

        recyclerView.addOnScrollListener(new HideShowScrollListener() {
            @Override
            public void onHide() {
                //fabMain.hide();
                ((MainActivity) getActivity()).hideBottomNavigationMenu(true);
                //animatedFab();
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
            }

            @Override
            public void onShow() {
                //fabMain.show();
                ((MainActivity) getActivity()).hideBottomNavigationMenu(false);
                //animatedFab();
            }
        });
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
            // fabMain.startAnimation(fabBackward);
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
            if (files.length != 0) {
                for (File file : files) {

                    if (file.getName().endsWith(".jpg") || file.getName().endsWith(".png") || file.getName().endsWith(".mp4") || file.getName().endsWith(".gif") || file.getName().endsWith(".3gp") || file.getName().endsWith(".mov") || file.getName().endsWith(".webm")) {
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
                //tvImage.setVisibility(View.VISIBLE);
            }
        } else {

            //Toast.makeText(getActivity(), "Thire is any directory ", Toast.LENGTH_SHORT).show();
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
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        AsyncTaskRunner2 task2 = new AsyncTaskRunner2();
//                        task2.execute();
//                    }
//                },500);
                break;
            case R.id.fab_main_download:
                animatedFab();
                inFiles.clear();
                //initSetList(WHATSAPP_STATUSES_LOCATION_RECIVED, "TWO");
                // ToastCustom.setToast(getActivity(), "WhatsApp Received Images..");
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        AsyncTaskRunner task = new AsyncTaskRunner();
//                        task.execute();
//                    }
//                },500);

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

    public Context passContext() {
        return getActivity();
    }

    @Override
    public void onLeftClicked(DialogFragment dialogFragment) {

        dialogFragment.dismiss();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            final Intent shareIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
            shareIntent.setType("*/*");

            final ArrayList<Uri> files = new ArrayList<Uri>();
            for (int i = 0; i < selectedIds.size(); i++) {
                //File fileSend = new File(selectedIds.get(i).getAbsolutePath());
//                                                    final File fileSend = new File(selectedIds.get(i).getAbsolutePath());
//                                                    Uri photoUri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() + ".provider", fileSend);
//                                                    files.add(photoUri);

                final File photoFile = new File(Environment.getExternalStorageDirectory().toString() + DIRECTORY_TO_SAVE_MEDIA_NOW, selectedIds.get(i).getName());
                photoUri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() + ".provider", photoFile);
                files.add(photoUri);
                //shareIntent.putExtra(Intent.EXTRA_STREAM, photoUri);
            }

            //shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);
            //shareIntent.putExtra(Intent.EXTRA_STREAM, files);
            shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Download Status Saver and Gallery App on - https://play.google.com/store/apps/details?id=android.statussaver.com.statussaver");
            getActivity().startActivity(Intent.createChooser(shareIntent, "Share files using"));


            if (actionMode != null) {
                actionMode.finish();
            }
            isMultiSelect = false;
            selectedIds.clear();


            //final File photoFile = new File(Environment.getExternalStorageDirectory().toString() + DIRECTORY_TO_SAVE_MEDIA_NOW, status.getName());
            //Uri photoUri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".provider", photoFile);
            //shareIntent.putExtra(Intent.EXTRA_STREAM, photoUri);
            //mContext.startActivity(Intent.createChooser(shareIntent, "Share image using"));
        } else {
//                                                final Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                                                shareIntent.setType("image/*");
//                                                final File photoFile = new File(Environment.getExternalStorageDirectory().toString() + DIRECTORY_TO_SAVE_MEDIA_NOW, status.getName());
//                                                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(photoFile));
//                                                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                                                mContext.startActivity(Intent.createChooser(shareIntent, "Share image using"));

            final Intent shareIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
            shareIntent.setType("*/*");

            final ArrayList<Uri> files = new ArrayList<Uri>();
            for (int i = 0; i < selectedIds.size(); i++) {
                //File fileSend = new File(selectedIds.get(i).getAbsolutePath());
//                                                    final File fileSend = new File(selectedIds.get(i).getAbsolutePath());
//                                                    Uri photoUri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() + ".provider", fileSend);
//                                                    files.add(photoUri);

                final File photoFile = new File(Environment.getExternalStorageDirectory().toString() + DIRECTORY_TO_SAVE_MEDIA_NOW, selectedIds.get(i).getName());
                photoUri = Uri.fromFile(photoFile);
                files.add(photoUri);
                //shareIntent.putExtra(Intent.EXTRA_STREAM, photoUri);
            }

            //shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);
            //shareIntent.putExtra(Intent.EXTRA_STREAM, files);
            shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Download Status Saver and Gallery App on - https://play.google.com/store/apps/details?id=android.statussaver.com.statussaver");
            getActivity().startActivity(Intent.createChooser(shareIntent, "Share files using"));

            if (actionMode != null) {
                actionMode.finish();
            }
            isMultiSelect = false;
            selectedIds.clear();
        }
    }

    @Override
    public void onRightClicked(DialogFragment dialogFragment) {
        dialogFragment.dismiss();
    }

    @Override
    public void onLeftDeleteClicked(DialogFragment dialogFragment) {
        dialogFragment.dismiss();

        final StringBuilder stringBuilder = new StringBuilder();
        File filedelete;
        for (int i = 0; i < selectedIds.size(); i++) {
            for (int j = 0; j < inFiles.size(); j++) {
                if (selectedIds.get(i).getAbsolutePath().contains(inFiles.get(j).getAbsolutePath())) {
                    stringBuilder.append("\n").append(selectedIds.get(i).getAbsolutePath());
                    filedelete = new File(selectedIds.get(i).getAbsolutePath());
                    if (filedelete.exists()) {
                        inFiles.remove(j);
                        filedelete.delete();
                    }
                }
            }
        }
        if (actionMode != null) {
            actionMode.finish();
        }
        isMultiSelect = false;
        selectedIds.clear();
        progressDialog.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //to refresh
                int resId = R.anim.layout_animation_slide_down;
                recyclerviewAdapter = new StoriesAdapterSave(inFiles, getActivity(), "ONE");
                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(COUNT, LinearLayoutManager.VERTICAL);
                LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getActivity(), resId);
                recyclerView.setLayoutAnimation(animation);
                recyclerView.setLayoutManager(staggeredGridLayoutManager);
                recyclerView.setAdapter(recyclerviewAdapter);

                // Toast.makeText(getActivity(), "Selected items are :"+stringBuilder.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.setVisibility(View.GONE);
            }
        }, 1500);
    }

    @Override
    public void onRightDeletClicked(DialogFragment dialogFragment) {
        dialogFragment.dismiss();
        if (actionMode != null) {
            actionMode.finish();
        }
        isMultiSelect = false;
        selectedIds.clear();
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
            //recyclerviewAdapter = new StoriesAdapterSave(inFiles, getActivity(), "ONE",passListner());
            recyclerviewAdapter = new StoriesAdapterSave(inFiles, getActivity(), "ONE");
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

            recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(passContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                    if (isMultiSelect) {
                        Log.e("TAG:", "normal mode selected");
                        multiSelect(position);
                    } else {
                        Intent intent = new Intent(getActivity(), ImageViewActivity.class);
                        intent.putExtra("imageList", inFiles);
                        intent.putExtra("state", "ONE");
                        intent.putExtra("position", position);
                        getActivity().startActivity(intent);
                    }
                }

                @Override
                public void onItemLongClick(View view, int position) {
                    if (!isMultiSelect) {
                        selectedIds = new ArrayList<>();
                        isMultiSelect = true;

                        if (actionMode == null) {
                            actionMode = getActivity().startActionMode(new ActionMode.Callback() {
                                @Override
                                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                                    MenuInflater inflater = mode.getMenuInflater();
                                    inflater.inflate(R.menu.menu_select, menu);
                                    return true;
                                }

                                @Override
                                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                                    return false;
                                }

                                @Override
                                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                                    switch (item.getItemId()) {
                                        case R.id.action_delete:

                                            DeleteDialogFragment deleteeDialogFragment = new DeleteDialogFragment();
                                            deleteeDialogFragment.setContext(getActivity());
                                            deleteeDialogFragment.setListner(SaveFragment.this);
                                            deleteeDialogFragment.show(getFragmentManager(), "DeleteDialogFragment");
                                            return true;

                                        case R.id.action_share:

                                            ShareDialogFragment shareDialogFragment = new ShareDialogFragment();
                                            shareDialogFragment.setContext(getActivity());
                                            shareDialogFragment.setListner(SaveFragment.this);
                                            shareDialogFragment.show(getFragmentManager(), "ShareDialogFragment");
                                            return true;
                                    }
                                    return false;
                                }

                                @Override
                                public void onDestroyActionMode(ActionMode mode) {

                                    actionMode = null;
                                    isMultiSelect = false;
                                    selectedIds = new ArrayList<>();
                                    recyclerviewAdapter.setSelectedIds(new ArrayList<File>());

                                }
                            });//show ActionMode.
                        }
                    }

                    multiSelect(position);

                }
            }));
        }

        private void multiSelect(int position) {
            File data = getItem(position);
            if (data != null) {
                if (actionMode != null) {
                    if (selectedIds.contains(data.getAbsoluteFile())) {
                        for (int i = 0; i < selectedIds.size(); i++) {
                            if (selectedIds.get(i).getAbsolutePath().equalsIgnoreCase(data.getAbsolutePath())) {
                                selectedIds.remove(i);
                            }
                        }
                    } else {
                        if (selectedIds.size() < 10) {
                            selectedIds.add(data.getAbsoluteFile());
                        } else {
                            Toast.makeText(getActivity(), "Sorry you can select only 10 items" + String.valueOf(selectedIds.size()), Toast.LENGTH_LONG).show();
                        }
                    }

                    if (selectedIds.size() > 0)
                        actionMode.setTitle(String.valueOf(selectedIds.size()) + " Selected"); //show selected item count on action mode.
                    else {
                        actionMode.setTitle(""); //remove item count from action mode.
                        actionMode.finish(); //hide action mode.
                    }
                    recyclerviewAdapter.setSelectedIds(selectedIds);

                }
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

    public File getItem(int position) {
        return inFiles.get(position);
    }

    public void updateContextMenu() {
        if (actionMode != null) {
            actionMode.finish();
        }
        isMultiSelect = false;
        selectedIds.clear();
    }

}
