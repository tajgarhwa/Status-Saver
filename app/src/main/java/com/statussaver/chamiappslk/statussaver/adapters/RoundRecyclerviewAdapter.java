package com.statussaver.com.statussaver.adapters;

import android.content.Context;
import android.content.Intent;
import android.statussaver.com.statussaver.R;
import android.statussaver.com.statussaver.activities.ImageViewActivity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.FileDescriptorBitmapDecoder;
import com.bumptech.glide.load.resource.bitmap.VideoBitmapDecoder;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import static com.bumptech.glide.request.target.Target.SIZE_ORIGINAL;

public class RoundRecyclerviewAdapter extends RecyclerView.Adapter<RoundRecyclerviewAdapter.ViewHolder> {

    private static final String TAG = "StaggeredRecyclerViewAd";

    //private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<File> statuslist;
    private Context mContext;

    public RoundRecyclerviewAdapter(ArrayList<File> statuslist, Context mContext) {
        this.statuslist = statuslist;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(com.statussaver.com.statussaver.R.layout.item_horizontal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final File status = getItem(position);
            if (status.getAbsolutePath().endsWith(".mp4") || status.getAbsolutePath().endsWith(".3gp") || status.getAbsolutePath().endsWith(".mov")) {
                holder.rel_video.setVisibility(View.VISIBLE);

                ViewGroup.LayoutParams params = holder.profile_image.getLayoutParams();
                // int a= params.height=ViewGroup.LayoutParams.WRAP_CONTENT;
                // int b =params.width =ViewGroup.LayoutParams.WRAP_CONTENT;
                BitmapPool bitmapPool = Glide.get(mContext).getBitmapPool();
                int microSecond = 6000000;// 6th second as an example
                VideoBitmapDecoder videoBitmapDecoder = new VideoBitmapDecoder(microSecond);
                FileDescriptorBitmapDecoder fileDescriptorBitmapDecoder = new FileDescriptorBitmapDecoder(videoBitmapDecoder, bitmapPool, DecodeFormat.PREFER_ARGB_8888);
                Glide.with(mContext)
                        .load("file://" + status.getAbsolutePath())
                        .asBitmap().placeholder(com.statussaver.com.statussaver.R.drawable.placeholder)
                        .override(SIZE_ORIGINAL, SIZE_ORIGINAL)// Example
                        .videoDecoder(fileDescriptorBitmapDecoder).override(100, 100)
                        .into(holder.profile_image);
            } else {
                holder.rel_video.setVisibility(View.INVISIBLE);
                Picasso.with(mContext).load(status.getAbsoluteFile()).placeholder(com.statussaver.com.statussaver.R.drawable.placeholder).resize(100, 100).into(holder.profile_image);
            }


        holder.relround.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ImageViewActivity.class);
                intent.putExtra("imageList",statuslist);
                intent.putExtra("file",status);
                intent.putExtra("state","ONE");
                intent.putExtra("position",position);
                intent.putExtra("DirectFrom", "roundFragment");
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return statuslist.size();
    }

    public File getItem(int position) {
        return statuslist.get(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView statusImage, profile_image;
        RelativeLayout relround, rel_video;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.statusImage = itemView.findViewById(com.statussaver.com.statussaver.R.id.img_status);
            this.profile_image = itemView.findViewById(com.statussaver.com.statussaver.R.id.profile_image);
            this.relround = itemView.findViewById(com.statussaver.com.statussaver.R.id.relround);
            this.rel_video = itemView.findViewById(com.statussaver.com.statussaver.R.id.rel_video);
        }
    }
}
