package com.statussaver.chamiappslk.statussaver.utils;

import com.statussaver.chamiappslk.statussaver.activities.MainActivity;
import com.statussaver.chamiappslk.statussaver.activities.SplashActivity;

import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class MultiplePermissionListner implements MultiplePermissionsListener {

    private final SplashActivity splashActivity;

    public MultiplePermissionListner(SplashActivity splashActivity) {
        this.splashActivity = splashActivity;
    }

    @Override
    public void onPermissionsChecked(MultiplePermissionsReport report) {

        for (PermissionGrantedResponse response : report.getGrantedPermissionResponses()) {
            splashActivity.showPermissionGranded(response.getPermissionName());
        }

        for (PermissionDeniedResponse response : report.getDeniedPermissionResponses()) {
            splashActivity.showPermissionDenied(response.getPermissionName());

//            if (response.isPermanentlyDenied()){
//                mainActivity.hanlePermissionDenidedPermission(response.getPermissionName());
//                return;
//            }else {
//                mainActivity.showPermissionDenied(response.getPermissionName());
//            }
        }
    }

    @Override
    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

        splashActivity.showPermissionRational(token);
    }
}
