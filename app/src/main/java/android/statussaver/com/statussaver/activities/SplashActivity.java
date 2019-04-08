package android.statussaver.com.statussaver.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.statussaver.com.statussaver.R;
import android.statussaver.com.statussaver.utils.Alerts;
import android.statussaver.com.statussaver.utils.MultiplePermissionListner;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

public class SplashActivity extends AppCompatActivity {

    private MultiplePermissionsListener multiplePermissionsListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        multiplePermissionsListener = new MultiplePermissionListner(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Dexter.withActivity(SplashActivity.this).withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE).withListener(multiplePermissionsListener).check();
            }
            },500);
    }

    public void showPermissionGranded(String permissionName) {

        if (permissionName.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            //setStatusList();
            boolean whatsAppFound = isAppInstalled(this, "com.whatsapp");
            if (whatsAppFound) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                this.finish();
            } else {

                //Toast.makeText(getApplicationContext(), "Please Install WhatsApp To Continue!", Toast.LENGTH_SHORT).show();
                Alerts.ShowError(SplashActivity.this, "We detected your mobile phone is not installed WhatsApp so please install WhatsApp to continue!", new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        dialog.dismiss();
                        SplashActivity.this.finish();
                    }
                }, false);
            }
            //startService(new Intent(getBaseContext(), MediaListenerService.class));


        }
    }

    public void showPermissionDenied(String permissionName) {
        if (permissionName.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(getApplicationContext(), "WRITE_EXTERNAL_STORAGE Permission Denied", Toast.LENGTH_LONG).show();
            this.finish();
        }

    }

    public void showPermissionRational(final PermissionToken permissionToken) {
        new AlertDialog.Builder(this).setTitle("We need permissions")
                .setMessage("Please allow permissions to continue")
                .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        permissionToken.continuePermissionRequest();
                        dialog.dismiss();
                    }
                }).

                setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        permissionToken.cancelPermissionRequest();
                        dialog.dismiss();
                    }
                })

                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        permissionToken.cancelPermissionRequest();
                    }
                }).show();
    }

    public void hanlePermissionDenidedPermission(String permissionName) {

        switch (permissionName) {

            case Manifest.permission.CAMERA:
//                tv1.setText("Permission permanently Denied");
//                tv1.setTextColor(ContextCompat.getColor(this, R.color.permissionDeniedPermanent));
                break;

            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
//                tv2.setText("Permission permanently Denied");
//                tv2.setTextColor(ContextCompat.getColor(this, R.color.permissionDeniedPermanent));
                break;

            case Manifest.permission.READ_CONTACTS:
//                tv3.setText("Permission permanently Denied");
//                tv3.setTextColor(ContextCompat.getColor(this, R.color.permissionDeniedPermanent));
                break;

        }

        new AlertDialog.Builder(this).setTitle("We need this permission")
                .setMessage("Please allow this permission from app settings page")
                .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openSettings();
                        dialog.dismiss();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    public void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }


    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
