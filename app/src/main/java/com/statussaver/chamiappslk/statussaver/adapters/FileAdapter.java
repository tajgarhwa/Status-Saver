package com.statussaver.chamiappslk.statussaver.adapters;

import android.content.Context;
import com.statussaver.chamiappslk.statussaver.models.FileDetails;

import android.statussaver.com.statussaver.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {

    private ArrayList<FileDetails> statuslist;
    private Context mContext;
    private String st;
    private ItemClickListener itemClickListener;

    public interface ItemClickListener {
        void onItemClick(int position, String message);
    }


    public FileAdapter(ArrayList<FileDetails> statuslist, Context mContext, String st, ItemClickListener itemClickListener) {
        this.statuslist = statuslist;
        this.mContext = mContext;
        this.st =st;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_file_path, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final FileDetails status = getItem(position);
        holder.tv1.setText(status.getFileName());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(position,status.getAbsolutePatheName());

            }
        });
    }


    @Override
    public int getItemCount() {
        return statuslist.size();
    }

    public FileDetails getItem(int position)
    {
        return statuslist.get(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv1,tv2;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv1 = itemView.findViewById(R.id.tv_first);
            this.linearLayout = itemView.findViewById(R.id.lin1);

        }
    }
}
