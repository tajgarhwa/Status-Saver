package android.statussaver.com.statussaver.adapters;

import android.content.Context;
import android.statussaver.com.statussaver.R;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_horizontal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        File status = getItem(position);
        Picasso.with(mContext).load(status.getAbsoluteFile()).placeholder(R.drawable.placeholder).into(holder.profile_image);

    }

    @Override
    public int getItemCount() {
        return statuslist.size();
    }

    public File getItem(int position) {
        return statuslist.get(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView statusImage,profile_image;
        RelativeLayout relround;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.statusImage = itemView.findViewById(R.id.img_status);
            this.profile_image = itemView.findViewById(R.id.profile_image);
            this.relround =itemView.findViewById(R.id.relround);
        }
    }
}
