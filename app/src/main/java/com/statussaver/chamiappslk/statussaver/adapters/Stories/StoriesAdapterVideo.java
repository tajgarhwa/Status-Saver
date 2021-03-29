package com.statussaver.chamiappslk.statussaver.adapters.Stories;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import com.statussaver.chamiappslk.statussaver.activities.ImageViewActivity;
import com.statussaver.chamiappslk.statussaver.utils.ToastCustom;

import android.statussaver.com.statussaver.R;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    private static final String WHATSAPP_STATUSES_LOCATION = "/WhatsApp/Media/.Statuses";

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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

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
                intent.putExtra("DirectFrom", "storiesVideos");
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
                    copyFile(status, new File(Environment.getExternalStorageDirectory().toString() + DIRECTORY_TO_SAVE_MEDIA_NOW + status.getName()),holder);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                final Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                shareIntent.setType("video/*");
//                final File photoFile = new File(Environment.getExternalStorageDirectory().toString() + DIRECTORY_TO_SAVE_MEDIA_NOW, status.getName());
//                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(photoFile));
//                mContext.startActivity(Intent.createChooser(shareIntent, "Share image using"));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    final Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("video/*");
                    final File photoFile = new File(Environment.getExternalStorageDirectory().toString() + WHATSAPP_STATUSES_LOCATION, status.getName());
                    Uri photoUri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".provider", photoFile);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, photoUri);
                    mContext.startActivity(Intent.createChooser(shareIntent, "Share Video using"));
                }else {
                    final Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("video/*");
                    final File photoFile = new File(Environment.getExternalStorageDirectory().toString() + WHATSAPP_STATUSES_LOCATION, status.getName());
                    shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(photoFile));
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    mContext.startActivity(Intent.createChooser(shareIntent, "Share Video using"));
                }
            }
        });

        //Log.e("video path load:", status.getAbsolutePath());

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {


                    Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                    whatsappIntent.setType("image/*");
                    whatsappIntent.setPackage("com.whatsapp");
                    final File photoFile = new File(Environment.getExternalStorageDirectory().toString() + WHATSAPP_STATUSES_LOCATION, status.getName());
                    Uri photoUri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".provider", photoFile);
                    //whatsappIntent.putExtra(Intent.EXTRA_TEXT, "The text you wanted to share");
                    whatsappIntent.putExtra(Intent.EXTRA_STREAM, photoUri);
                    //whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    try {
                        mContext.startActivity(whatsappIntent);
                        ToastCustom.setToast(mContext,"Repost!");
                    } catch (android.content.ActivityNotFoundException ex) {
                        // ToastHelper.MakeShortText("Whatsapp have not been installed.");
                        ToastCustom.setToast(mContext,"Whatsapp have not been installed.");
                    }
                }else {
                    Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                    whatsappIntent.setType("image/*");
                    whatsappIntent.setPackage("com.whatsapp");
                    final File photoFile = new File(Environment.getExternalStorageDirectory().toString() + WHATSAPP_STATUSES_LOCATION, status.getName());
                    //whatsappIntent.putExtra(Intent.EXTRA_TEXT, "The text you wanted to share");
                    whatsappIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(photoFile));
                    whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    try {
                        mContext.startActivity(whatsappIntent);
                        ToastCustom.setToast(mContext,"Repost!");
                    } catch (android.content.ActivityNotFoundException ex) {
                        // ToastHelper.MakeShortText("Whatsapp have not been installed.");
                        ToastCustom.setToast(mContext,"Whatsapp have not been installed.");
                    }
                }


            }
        });

    }

    private void copyFile(File sourceFile, File file,ViewHolder viewHolder) throws IOException {
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();

        if (!file.exists()) {
            file.createNewFile();
            final int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.tvDownload.setBackgroundResource(R.drawable.ic_done);
            } else {
                viewHolder.tvDownload.setBackgroundResource(R.drawable.ic_done);
            }
        }else {
            //Toast.makeText(mContext,"Video is already exist!",Toast.LENGTH_LONG).show();
            ToastCustom.setToast(mContext,"Video is already exist!");
            final int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.tvDownload.setBackgroundResource(R.drawable.ic_done_all);
            } else {
                viewHolder.tvDownload.setBackgroundResource(R.drawable.ic_done_all);
            }
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
        RelativeLayout btnDownload,btnShare,relfuctions,btn;
        TextView tvDownload;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.statusImage = itemView.findViewById(R.id.img_status);
            this.btnDownload = itemView.findViewById(R.id.btnDownload);
            this.btnShare = itemView.findViewById(R.id.btnShare);
            this.relfuctions = itemView.findViewById(R.id.relfuctions);
            this.btn =itemView.findViewById(R.id.btn);
            this.tvDownload = itemView.findViewById(R.id.tv_download);
        }
    }
}
