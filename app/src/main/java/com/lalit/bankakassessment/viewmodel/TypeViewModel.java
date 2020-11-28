package com.lalit.bankakassessment.viewmodel;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lalit.bankakassessment.callback.RepoCallBack;
import com.lalit.bankakassessment.model.TypeResponseModel;
import com.lalit.bankakassessment.repository.TypeRepo;

public class TypeViewModel extends ViewModel implements RepoCallBack {
    public static final String TAG = "TypeViewModel";
    public MutableLiveData<TypeResponseModel> dataList = new MutableLiveData<>();
    public MutableLiveData<Integer> status = new MutableLiveData<>(); //0 for progress //1 for success //-1 for error
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();//this is for showing error messages
    private TypeRepo typeRepo;

    public void init() {
        if (typeRepo != null) {
            return;
        }
        typeRepo = new TypeRepo();
    }

    public void reloadData(int type) {
        if (typeRepo == null) throw new NullPointerException("Please call init() first");
        typeRepo.getData(this, type);
    }

    //returning LiveData instead of MutableLiveData cause we dont want this data to be changed from activity
    public LiveData<TypeResponseModel> getDataList() {
        return dataList;
    }

    public LiveData<Integer> getStatus() {
        return status;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    @Override
    public void onProgress() {
        status.postValue(0);
    }

    @Override
    public <T> void onSuccess(boolean remote, @Nullable T localData, Class<T> type) {
        TypeResponseModel data = (TypeResponseModel) localData;
        dataList.postValue(data);
        status.postValue(1);
    }

    @Override
    public void onFailure(boolean remote, @Nullable String error) {
        status.postValue(-1);
        errorMessage.postValue(error);
    }
}
