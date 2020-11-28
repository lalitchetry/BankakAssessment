package com.lalit.bankakassessment.repository;

import android.text.TextUtils;

import com.lalit.bankakassessment.callback.RepoCallBack;
import com.lalit.bankakassessment.model.TypeResponseModel;
import com.lalit.bankakassessment.remote.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TypeRepo {
    public void getData(RepoCallBack callBack, int type) {
        callBack.onProgress();
        getDataFromAPI(callBack, type);
    }

    private void getDataFromAPI(RepoCallBack callBack, int type) {
        Call<TypeResponseModel> call = ApiClient.getSbAppInterface().gettaskType(type);
        call.enqueue(new Callback<TypeResponseModel>() {
            @Override
            public void onResponse(Call<TypeResponseModel> call, Response<TypeResponseModel> response) {
                TypeResponseModel model = response.body();
                if (model != null) {
                    if (TextUtils.equals(model.getStatus(), "true")) {
                        callBack.onSuccess(true, model, TypeResponseModel.class);
                    } else {
                        callBack.onFailure(true, model.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<TypeResponseModel> call, Throwable t) {
                callBack.onFailure(true, t.getLocalizedMessage());
            }
        });
    }
}
