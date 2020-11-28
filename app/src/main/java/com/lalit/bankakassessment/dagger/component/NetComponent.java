package com.lalit.bankakassessment.dagger.component;


import com.lalit.bankakassessment.dagger.module.APIModule;
import com.lalit.bankakassessment.dagger.module.AppModule;
import com.lalit.bankakassessment.dagger.module.NetModule;
import com.lalit.bankakassessment.utils.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetModule.class, APIModule.class})

public interface NetComponent {
    void inject(BaseActivity baseActivity);
}
