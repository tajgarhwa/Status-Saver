package com.statussaver.chamiappslk.statussaver.utils;

import android.content.Context;
import android.statussaver.com.statussaver.R;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ToastCustom {
    public static void setToast(Context context, String msg) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.toast_green, null);
        TextView toastText = (TextView) view.findViewById(R.id.toastText);
        toastText.setText(msg);
        Toast toast = new Toast(context);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0, 100);
        //toast.setMargin(60,60);
        toast.show();
    }
}
