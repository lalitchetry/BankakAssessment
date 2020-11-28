package com.lalit.bankakassessment.remote;

import com.lalit.bankakassessment.model.TypeResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SBAppInterface {
    @GET("task/{type}")
    Call<TypeResponseModel> gettaskType(@Path("type") int type);
}