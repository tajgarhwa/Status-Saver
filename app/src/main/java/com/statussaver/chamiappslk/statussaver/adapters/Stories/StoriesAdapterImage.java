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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class StoriesAdapterImage extends RecyclerView.Adapter<StoriesAdapterImage.ViewHolder> {

    private static final String TAG = "StaggeredRecyclerViewAd";

    //private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<File> statuslist;
    private Context mContext;
    private static final String DIRECTORY_TO_SAVE_MEDIA_NOW ="/Status_Saver/";
    private String st;
    ArrayList<File> searchList = new ArrayList<File>();
    ArrayList<String> trueList = new ArrayList<String>();
    private static final String WHATSAPP_STATUSES_LOCATION = "/WhatsApp/Media/.Statuses";

    public void setTrueList(ArrayList<String> trueList) {
        this.trueList = trueList;
    }



    public StoriesAdapterImage(ArrayList<File> statuslist, Context mContext,String st) {
        this.statuslist = statuslist;
        this.mContext = mContext;
        this.st =st;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_grid_layout, parent, false);
        //setSearchList(DIRECTORY_TO_SAVE_MEDIA_NOW, "ONE");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final File status = getItem(position);
        Picasso.with(mContext).load(status.getAbsoluteFile()).placeholder(R.drawable.placeholder).into(holder.statusImage);
//        try {
//            if (status.getName().equals(trueList.get(position))) {
//                holder.btnDownload.setVisibility(View.GONE);
//            } else {
//                holder.btnDownload.setVisibility(View.VISIBLE);
//            }
//        }catch (IndexOutOfBoundsException e){
//            holder.btnDownload.setVisibility(View.VISIBLE);
//        }

       //Log.d("in files", status.getName());
        //Log.d("search files", trueList.get(position));

        if (st.equalsIgnoreCase("TWO") || st.equalsIgnoreCase("THREE")){

            holder.relfuctions.setVisibility(View.GONE);
        }

        Log.d("position", String.valueOf(position));

        holder.statusImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ImageViewActivity.class);
                intent.putExtra("imageList",statuslist);
                intent.putExtra("file",status);
                intent.putExtra("state",st);
                intent.putExtra("position",position);
                intent.putExtra("DirectFrom", "storiesImage");
                mContext.startActivity(intent);
            }
        });

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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    final Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("image/*");
                    final File photoFile = new File(Environment.getExternalStorageDirectory().toString() + WHATSAPP_STATUSES_LOCATION, status.getName());
                    Uri photoUri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".provider", photoFile);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Download Status Saver and Gallery App on - https://play.google.com/store/apps/details?id=android.statussaver.com.statussaver");
                    shareIntent.putExtra(Intent.EXTRA_STREAM, photoUri);
                    mContext.startActivity(Intent.createChooser(shareIntent, "Share image using"));
                }else {
                    final Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("image/*");
                    final File photoFile = new File(Environment.getExternalStorageDirectory().toString() + WHATSAPP_STATUSES_LOCATION, status.getName());
                    shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(photoFile));
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Download Status Saver and Gallery App on - https://play.google.com/store/apps/details?id=android.statussaver.com.statussaver");
                    mContext.startActivity(Intent.createChooser(shareIntent, "Share image using"));
                }

            }
        });

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
            //Toast.makeText(mContext,"Image is already exist!",Toast.LENGTH_LONG).show();
            ToastCustom.setToast(mContext,"Image is already exist!");
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
            this.btn = itemView.findViewById(R.id.btn);
            this.tvDownload = itemView.findViewById(R.id.tv_download);
        }
    }
}
