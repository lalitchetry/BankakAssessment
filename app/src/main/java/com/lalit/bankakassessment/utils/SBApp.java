package com.lalit.bankakassessment.utils;

import android.app.Application;
import android.content.Context;

import com.lalit.bankakassessment.dagger.component.DaggerNetComponent;
import com.lalit.bankakassessment.dagger.component.NetComponent;
import com.lalit.bankakassessment.dagger.module.APIModule;
import com.lalit.bankakassessment.dagger.module.AppModule;
import com.lalit.bankakassessment.dagger.module.NetModule;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import static com.lalit.bankakassessment.utils.Constants.BASE_URL;


public class SBApp extends Application {
    public NetComponent mNetComponent;
    private static SBApp mInstance;
    private static OkHttpClient okHttpClient;
    int YOUR_CUSTOM_TIMEOUT = 30;

    public NetComponent getmNetComponent() {
        return mNetComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        mInstance = this;
        initOkHttpObject();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    private void init() {
        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(BASE_URL))
                .aPIModule(new APIModule())
                .build();
    }

    public static OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }


    public void initOkHttpObject() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(YOUR_CUSTOM_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(YOUR_CUSTOM_TIMEOUT, TimeUnit.SECONDS);
        builder.connectTimeout(YOUR_CUSTOM_TIMEOUT, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(false);
        builder.addNetworkInterceptor(logging);
        okHttpClient = builder.build();
    }

    public static synchronized SBApp getInstance() {
        return mInstance;
    }
}
