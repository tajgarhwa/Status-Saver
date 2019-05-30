package com.statussaver.chamiappslk.statussaver.adapters.Others;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.statussaver.com.statussaver.R;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.statussaver.chamiappslk.statussaver.activities.GifViewActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class StoriesAdapterOtherGif extends RecyclerView.Adapter<StoriesAdapterOtherGif.ViewHolder> {

    private static final String TAG = "StaggeredRecyclerViewAd";

    //private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<File> statuslist;
    private Context mContext;
    private static final String DIRECTORY_TO_SAVE_MEDIA_NOW = "/Status_Saver/";

    public StoriesAdapterOtherGif(ArrayList<File> statuslist, Context mContext) {
        this.statuslist = statuslist;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_grid_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final File status = getItem(position);
        // Picasso.with(mContext).load("file://" + status.getAbsoluteFile()).placeholder(R.drawable.placeholder).into(holder.statusImage);
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(holder.statusImage);
        Glide.with(mContext).load("file://" + status.getAbsoluteFile()).placeholder(R.drawable.placeholder).into(imageViewTarget);

        holder.statusImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GifViewActivity.class);
                intent.putExtra("gif", status.getAbsolutePath());
                intent.putExtra("file", status);
                mContext.startActivity(intent);
            }
        });

        holder.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    copyFile(status, new File(Environment.getExternalStorageDirectory().toString() + DIRECTORY_TO_SAVE_MEDIA_NOW + status.getName()));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/*");
                final File photoFile = new File(Environment.getExternalStorageDirectory().toString() + DIRECTORY_TO_SAVE_MEDIA_NOW, status.getName());
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(photoFile));
                mContext.startActivity(Intent.createChooser(shareIntent, "Share image using"));
            }
        });
    }

    private void copyFile(File sourceFile, File file) throws IOException {
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();

        if (!file.exists()) {
            file.createNewFile();
        } else {
            Toast.makeText(mContext, "GIF is already exist!", Toast.LENGTH_LONG).show();
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
    public int getItemCount() {
        return statuslist.size();
    }

    public File getItem(int position) {
        return statuslist.get(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView statusImage;
        RelativeLayout btnDownload, btnShare;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.statusImage = itemView.findViewById(R.id.img_status);
            this.btnDownload = itemView.findViewById(R.id.btnDownload);
            this.btnShare = itemView.findViewById(R.id.btnShare);
        }
    }
}
