package com.lalit.bankakassessment.utils;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.lalit.bankakassessment.R;

import java.util.ArrayList;

public class DynamicView {
    Context context;

    public DynamicView(Context context) {
        this.context = context;
    }

    public TextView textView(Context context, String text){
        final ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView(context);
        textView.setLayoutParams(layoutParams);
        textView.setText(text);
        textView.setTextColor(Color.rgb(0,0,0));
        textView.setTextSize(14);
        return textView;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public EditText editText(Context context, String hint, String regex, String type_name){
        final ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final EditText editText = new EditText(context);
        editText.setLayoutParams(layoutParams);
        editText.setHint(hint);
        editText.setTag(regex);
        editText.setTextColor(context.getColor(R.color.black));
        editText.setTextSize(14);
        return editText;
    }

    public MaterialSpinner materialSpinner(Context context, ArrayList<String> spinnerArray){
        final ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final MaterialSpinner spinner =  new MaterialSpinner(context);
        spinner.setLayoutParams(layoutParams);
        spinner.setItems(spinnerArray);
        return spinner;

    }
}
