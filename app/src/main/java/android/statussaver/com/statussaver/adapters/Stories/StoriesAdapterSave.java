package android.statussaver.com.statussaver.adapters.Stories;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.statussaver.com.statussaver.R;
import android.statussaver.com.statussaver.activities.ImageViewActivity;
import android.statussaver.com.statussaver.activities.VideoViewActivity;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.FileDescriptorBitmapDecoder;
import com.bumptech.glide.load.resource.bitmap.VideoBitmapDecoder;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.request.target.Target.SIZE_ORIGINAL;

public class StoriesAdapterSave extends RecyclerView.Adapter<StoriesAdapterSave.ViewHolder> {

    private static final String TAG = "StaggeredRecyclerViewAd";

    //private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<File> statuslist;
    private Context mContext;
    private static final String DIRECTORY_TO_SAVE_MEDIA_NOW ="/Status_Saver/" ;
    private String st;
    private List<File> selectedIds= new ArrayList<>();



    public StoriesAdapterSave(ArrayList<File> statuslist, Context mContext,String st)
    {
        this.statuslist = statuslist;
        this.mContext = mContext;
        this.st =st;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_grid_layout_save, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final File status = getItem(position);
        // Picasso.with(mContext).load("file://" + status.getAbsoluteFile()).placeholder(R.drawable.placeholder).into(holder.statusImage);
//
//        Glide.with(mContext).load("file://" + status.getAbsolutePath())
//                .skipMemoryCache(true)
//                .into(holder.statusImage);

        if (status.getAbsolutePath().endsWith(".mp4") || status.getAbsolutePath().endsWith(".3gp") || status.getAbsolutePath().endsWith(".mov") || status.getAbsolutePath().endsWith("webm")) {
//            ViewGroup.LayoutParams params = holder.statusImage.getLayoutParams();
//            // int a= params.height=ViewGroup.LayoutParams.WRAP_CONTENT;
//            // int b =params.width =ViewGroup.LayoutParams.WRAP_CONTENT;
            BitmapPool bitmapPool = Glide.get(mContext).getBitmapPool();
            int microSecond = 6000000;// 6th second as an example
            VideoBitmapDecoder videoBitmapDecoder = new VideoBitmapDecoder(microSecond);
            FileDescriptorBitmapDecoder fileDescriptorBitmapDecoder = new FileDescriptorBitmapDecoder(videoBitmapDecoder, bitmapPool, DecodeFormat.PREFER_ARGB_8888);
            Glide.with(mContext)
                    .load("file://" + status.getAbsolutePath())
                    .asBitmap().placeholder(R.drawable.placeholder)
                    .override(SIZE_ORIGINAL,SIZE_ORIGINAL)// Example
                    .videoDecoder(fileDescriptorBitmapDecoder)
                    .into(holder.statusImage);

            holder.rel_save_video.setVisibility(View.VISIBLE);
        }
        else if (status.getAbsolutePath().endsWith(".jpg") || status.getAbsolutePath().endsWith(".png")){

            Picasso.with(mContext).load(status.getAbsoluteFile()).placeholder(R.drawable.placeholder).into(holder.statusImage);

            holder.rel_save_video.setVisibility(View.GONE);

        }else if (status.getAbsolutePath().endsWith(".gif")){

            GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(holder.statusImage);
            Glide.with(mContext).load("file://" + status.getAbsoluteFile()).placeholder(R.drawable.placeholder).into(imageViewTarget);
            holder.rel_save_video.setVisibility(View.GONE);
        }

        if (selectedIds.contains(status.getAbsoluteFile())){
            //if item is selected then,set foreground color of FrameLayout.
            holder.layoutSelected.setVisibility(View.VISIBLE);
        }
        else {
            //else remove selected item color.
            //holder.rootView.setForeground(new ColorDrawable(ContextCompat.getColor(context,android.R.color.transparent)));
            holder.layoutSelected.setVisibility(View.GONE);
        }

        Log.e("video path load:", status.getAbsolutePath());

    }

    private void copyFile(File sourceFile, File file) throws IOException {
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();

        if (!file.exists()) {
            file.createNewFile();
        }else {
            Toast.makeText(mContext,"Video is already exist!",Toast.LENGTH_LONG).show();
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

    public void setSelectedIds(List<File> selectedIds) {
        this.selectedIds = selectedIds;
        notifyDataSetChanged();
    }

    public synchronized void setNotify(){
        this.notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView statusImage;
        RelativeLayout rel_save_video,layoutSelected;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.statusImage = itemView.findViewById(R.id.img_status);
//            this.btnDownload = itemView.findViewById(R.id.btnDownload);
//            this.btnShare = itemView.findViewById(R.id.btnShare);
            this.rel_save_video = itemView.findViewById(R.id.rel_save_video);
            this.layoutSelected = itemView.findViewById(R.id.layoutSelected);
        }
    }
}
