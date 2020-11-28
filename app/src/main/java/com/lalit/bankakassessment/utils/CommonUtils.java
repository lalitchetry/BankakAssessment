package com.lalit.bankakassessment.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;
import com.lalit.bankakassessment.R;

public class CommonUtils {

    Context mContext;

    public CommonUtils(Context mContext) {
        this.mContext = mContext;
    }

    //check internet connection
    public boolean isNetworkAvailable() {
        /* getting systems Service connectivity manager */
        ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (mConnectivityManager != null) {
            NetworkInfo[] mNetworkInfos = mConnectivityManager.getAllNetworkInfo();
            for (NetworkInfo mNetworkInfo : mNetworkInfos) {
                if (mNetworkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    //checks if the location is enabled
    public static boolean isLocationEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    //checks for location permission
    public boolean checkPermission(Context context) {
        return ((ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) &&
                (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED));
    }

    //asks for location permission
    public void askPermission(Activity activity, int REQUEST_CODE_LOCATION_PERMISSION) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_LOCATION_PERMISSION);
    }

    //show simple message print for bottom of screen
    public void snackbar(View coordinatorLayout, String message) {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    //create custome loader
    public Dialog createCustomLoader(Context mContext, boolean isCancelable) {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(isCancelable);
        dialog.setContentView(R.layout.loader_progress_dialog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        return dialog;
    }

    //show custom dialog
    public void showCustomDialog(Dialog dialog, Context context) {
        if (dialog != null) {
            if (!dialog.isShowing())
                if (!((Activity) context).isFinishing()) {
                    dialog.show();
                }
        }
    }

    //hide custom dialog
    public void dismissCustomDialog(Dialog dialog) {
        if (dialog != null) {
            if (dialog.isShowing())
                dialog.dismiss();
        }
    }

}
