package com.lalit.bankakassessment.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.lalit.bankakassessment.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    //show simple message print for bottom of screen
    public void snackbar(View coordinatorLayout, String message) {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    //create custom loader
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


    //validation of all the empty field and regex
    public String validate(EditText editText, String regex) {
        String message = "";
        String result = editText.getText().toString().trim();
        if (TextUtils.isEmpty(result)) {
            message = "Please fill all data";
            return message;
        } else {
            if (!TextUtils.isEmpty(regex)) {
                regex = regex.replace("/", "").replace("s", "")
                        .replace("[]", "\\[\\]").replace("d", "\\d");
                try {
                    Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(result);
                    if (!matcher.matches()) {
                        message = "Please provide correct format";
                        return message;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return message;
    }
}
