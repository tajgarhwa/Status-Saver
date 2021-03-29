package com.statussaver.chamiappslk.statussaver.adapters;


import android.content.Context;
import com.statussaver.chamiappslk.statussaver.fragments.MediaPageFragment;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.io.File;
import java.util.ArrayList;


public class ViewPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private String[] imageUrls;
    private ArrayList<File> messages;

    public ViewPagerAdapter(FragmentManager fm, ArrayList<File> messages) {
        super(fm);
        this.messages = messages;
    }

    @Override
    public MediaPageFragment getItem(int position) {
        File message = messages.get(position);
        return MediaPageFragment.newInstance(message);
    }

    @Override
    public int getCount() {
        return messages.size();
    }


//    @Override
//    public int getCount() {
//        return imageUrls.length;
//    }
//
//    @Override
//    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
//        return view == object;
//    }
//
//    @NonNull
//    @Override
//    public Object instantiateItem(@NonNull ViewGroup container, int position) {
//        ImageView imageView = new ImageView(context);
////        Picasso.get()
////                .load(imageUrls[position])
////                .fit()
////                .centerCrop()
////                .into(imageView);
////        container.addView(imageView);
//
//        return imageView;
//    }
//
//    @Override
//    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        container.removeView((View) object);
//    }
}
