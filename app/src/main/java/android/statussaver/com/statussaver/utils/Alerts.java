package android.statussaver.com.statussaver.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.statussaver.com.statussaver.R;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class Alerts {


    public static void ShowSuccess(Context context, String msg, final Dialog.OnDismissListener onDismissListener, boolean cancelOutSide) {
        Show_Success(context, "", msg, "", onDismissListener, cancelOutSide);
    }

    public static void ShowError(Context context, String msg, final Dialog.OnDismissListener onDismissListener, boolean cancelOutSide) {
        Show_Error(context, "", msg, "", onDismissListener, cancelOutSide);
    }

    public static void Show_Success(Context context, String title, String msg, String feedback, final Dialog.OnDismissListener onDismissListener, boolean cancelOutSide) {
        if (context instanceof Activity && ((Activity) context).isFinishing()) {
            return;
        }
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_success);
        dialog.setCanceledOnTouchOutside(cancelOutSide);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final TextView tv_title = (TextView) dialog.findViewById(R.id.tvTitle);
        final TextView tv_msg = (TextView) dialog.findViewById(R.id.tvMsg);
        final TextView tv_feedback = (TextView) dialog.findViewById(R.id.btnOk);
        tv_title.setText("SUCCESS");
       /* if (TextUtils.isEmpty(title)) {
            tv_title.setText(R.string.successful);
        } else {
            tv_title.setText(title);
        }*/
        if (TextUtils.isEmpty(msg)) {
            tv_msg.setText("");
        } else {
            tv_msg.setText(msg);
        }
        if (TextUtils.isEmpty(feedback)) {
            tv_feedback.setText("OK");
        } else {
            tv_feedback.setText(feedback);
        }
        tv_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDismissListener != null) {
                    dialog.setOnDismissListener(onDismissListener);
                }
                dialog.dismiss();
            }
        });
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.show();
    }

    public static void Show_Error(Context context, String title, String msg, String feedback, final Dialog.OnDismissListener onDismissListener, boolean cancelOutSide) {
        if (context instanceof Activity && ((Activity) context).isFinishing()) {
            return;
        }
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_error);
        dialog.setCanceledOnTouchOutside(cancelOutSide);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final TextView tv_title = (TextView) dialog.findViewById(R.id.tvTitle);
        final TextView tv_msg = (TextView) dialog.findViewById(R.id.tvMsg);
        final TextView tv_feedback = (TextView) dialog.findViewById(R.id.btnOk);
        tv_title.setText("Status Saver And Gallery");
      /*  if (TextUtils.isEmpty(title)) {
            tv_title.setText(R.string.unsuccessful);
        } else {
            tv_title.setText(title);
        }*/
        if (TextUtils.isEmpty(msg)) {
            tv_msg.setText(context.getString(R.string.sww));
        } else {
            tv_msg.setText(msg);
        }
        if (TextUtils.isEmpty(feedback)) {
            tv_feedback.setText("OK");
        } else {
            tv_feedback.setText(feedback);
        }
        tv_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDismissListener != null) {
                    dialog.setOnDismissListener(onDismissListener);
                }
                dialog.dismiss();
            }
        });
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.show();
    }
}
