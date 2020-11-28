package com.lalit.bankakassessment.utils;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.lalit.bankakassessment.remote.SBAppInterface;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class BaseActivity extends AppCompatActivity {

    @Inject
    public CommonUtils commonUtils;
    @Inject
    public
    SBAppInterface sbAppInterface;
    @Inject
    public
    Gson gson;
    @Inject
    Retrofit retrofit;
    public Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        ((SBApp) getApplication()).getmNetComponent().inject(this);
    }



    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }
}
