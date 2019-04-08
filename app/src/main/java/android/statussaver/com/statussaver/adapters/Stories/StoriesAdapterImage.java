package android.statussaver.com.statussaver.adapters.Stories;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.statussaver.com.statussaver.R;
import android.statussaver.com.statussaver.activities.ImageViewActivity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
    private static final String DIRECTORY_TO_SAVE_MEDIA_NOW ="/Status_Saver/" ;
    private String st;

    public StoriesAdapterImage(ArrayList<File> statuslist, Context mContext,String st) {
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
        Picasso.with(mContext).load(status.getAbsoluteFile()).placeholder(R.drawable.placeholder).resize(400,400).into(holder.statusImage);

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
        }else {
            Toast.makeText(mContext,"Image is already exist!",Toast.LENGTH_LONG).show();
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
