package com.statussaver.chamiappslk.statussaver.DialogFragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.statussaver.com.statussaver.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.statussaver.chamiappslk.statussaver.adapters.FileAdapter;
import com.statussaver.chamiappslk.statussaver.models.FileDetails;

import java.io.File;
import java.util.ArrayList;

public class SelectDirectoryDialogFragment extends DialogFragment implements View.OnClickListener, FileAdapter.ItemClickListener {


    private RecyclerView recyclerView;
    private ArrayList<FileDetails> myList;
    private String root_sd;
    private FileAdapter fileAdapter;
    private File file;
    private int CAMREA_CODE = 1;
    private FileDetails fileDetails;
    private String stRoot = "null";
    private Context context;
    private ImageView back,close;


    public void setContext(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
        //progressDialog = new CustomProgressDialog(getActivity());
        View view = inflater.inflate(R.layout.dialogfragment_directory, container, false);
        recyclerView = view.findViewById(R.id.recylerview);
        back =view.findViewById(R.id.back);
        close =view.findViewById(R.id.close);
        root_sd = Environment.getExternalStorageDirectory().toString();

        myList = new ArrayList<>();

        fileGet();

        back.setOnClickListener(this);
        close.setOnClickListener(this);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setGravity(Gravity.BOTTOM);
        }
    }

    private void fileGet() {
        stRoot = root_sd;
        file = new File(root_sd);
        File list[] = file.listFiles();

        myList.clear();

        for (int i = 0; i < list.length; i++) {
            if (list[i].isDirectory() && !list[i].isHidden() && list[i].exists()) {
//                myList.add(list[i].getName());
//
//                myList.get(i).setId(""+i);
//                myList.get(i).setFileName(list[i].getName());
//                myList.get(i).setAbsolutePatheName(list[i].getAbsolutePath());

                fileDetails = new FileDetails("" + i, list[i].getName(), list[i].getAbsolutePath());
                myList.add(fileDetails);

            } else {
                Log.e("unAuthroizedfiles", "unAuthroizedfiles:" + list[i].getName());
            }
        }
        fileAdapter = new FileAdapter(myList, context, "one", this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(fileAdapter);

        fileAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position, String message) {


        fileGetnew(message);
        stRoot = message;
    }


    private void fileGetnew(String msg) {


        stRoot = msg;

        Log.e("MESSAGE", "Message:" + msg);
        file = new File(msg);
        File list[] = file.listFiles();

        myList.clear();

        for (int i = 0; i < list.length; i++) {
            if (list[i].isDirectory() && !list[i].isHidden() && list[i].exists()) {
                fileDetails = new FileDetails("" + i, list[i].getName(), list[i].getAbsolutePath());
                Log.e("Authorized_files", "Authorized files:" + list[i].getName());
                myList.add(fileDetails);
            } else {
                Log.e("unAuthroizedfiles", "unAuthroizedfiles:" + list[i].getName());
            }
        }


        fileAdapter = new FileAdapter(myList, context, "one", this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(fileAdapter);

        fileAdapter.notifyDataSetChanged();

    }

    public void backfunction(){
        if (!stRoot.equals("/storage/emulated/0/")) {
            String newpathArray[] = stRoot.split("/");
            int sizeArray = newpathArray.length;
            StringBuilder s = new StringBuilder(10000);

            for (int i = 0; i < sizeArray; i++) {
                if (i != (sizeArray - 1)) {
                    s.append(newpathArray[i]).append("/");
                }
            }
            Log.e("StringFile", "StringFile" + s.toString());

            fileGetnew(s.toString());


        } else {
           SelectDirectoryDialogFragment selectDirectoryDialogFragment = new SelectDirectoryDialogFragment();
           selectDirectoryDialogFragment.dismiss();
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.back:
                backfunction();
                break;

            case R.id.close:
                Dialog dialog = getDialog();
                dialog.dismiss();
                break;

        }
    }
}
