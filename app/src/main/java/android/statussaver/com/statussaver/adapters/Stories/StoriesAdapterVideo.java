package android.statussaver.com.statussaver.adapters.Stories;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.statussaver.com.statussaver.R;
import android.statussaver.com.statussaver.activities.ImageViewActivity;
import android.statussaver.com.statussaver.activities.VideoViewActivity;
import android.support.annotation.NonNull;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import static com.bumptech.glide.request.target.Target.SIZE_ORIGINAL;

public class StoriesAdapterVideo extends RecyclerView.Adapter<StoriesAdapterVideo.ViewHolder> {

    private static final String TAG = "StaggeredRecyclerViewAd";

    //private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<File> statuslist;
    private Context mContext;
    private static final String DIRECTORY_TO_SAVE_MEDIA_NOW ="/Status_Saver/" ;
    private String st;

    public StoriesAdapterVideo(ArrayList<File> statuslist, Context mContext,String st) {
        this.statuslist = statuslist;
        this.mContext = mContext;
        this.st =st;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_grid_layout, parent, false);
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

        ViewGroup.LayoutParams params = holder.statusImage.getLayoutParams();
       // int a= params.height=ViewGroup.LayoutParams.WRAP_CONTENT;
       // int b =params.width =ViewGroup.LayoutParams.WRAP_CONTENT;
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

        holder.statusImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mContext, VideoViewActivity.class);
//                intent.putExtra("url","file://" + status.getAbsolutePath());
//                mContext.startActivity(intent);

                Intent intent = new Intent(mContext, ImageViewActivity.class);
                intent.putExtra("imageList",statuslist);
                intent.putExtra("file",status);
                intent.putExtra("state",st);
                intent.putExtra("position",position);
                mContext.startActivity(intent);
            }
        });

        if (st.equalsIgnoreCase("TWO") || st.equalsIgnoreCase("THREE")){

            holder.relfuctions.setVisibility(View.GONE);
        }


//        holder.statusImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, ImageViewActivity.class);
//                intent.putExtra("image",status.getAbsolutePath());
//                intent.putExtra("file",status);
//                mContext.startActivity(intent);
//            }
//        });

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
                shareIntent.setType("video/*");
                final File photoFile = new File(Environment.getExternalStorageDirectory().toString() + DIRECTORY_TO_SAVE_MEDIA_NOW, status.getName());
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(photoFile));
                mContext.startActivity(Intent.createChooser(shareIntent, "Share image using"));
            }
        });

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


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView statusImage;
        RelativeLayout btnDownload,btnShare,relfuctions;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.statusImage = itemView.findViewById(R.id.img_status);
            this.btnDownload = itemView.findViewById(R.id.btnDownload);
            this.btnShare = itemView.findViewById(R.id.btnShare);
            this.relfuctions = itemView.findViewById(R.id.relfuctions);
        }
    }
}
