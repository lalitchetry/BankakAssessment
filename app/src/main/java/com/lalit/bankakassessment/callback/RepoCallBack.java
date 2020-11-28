package com.lalit.bankakassessment.callback;

import androidx.annotation.Nullable;

public interface RepoCallBack {
    void onProgress();

    <T> void onSuccess(boolean remote, @Nullable T localData, Class<T> type);

    void onFailure(boolean remote, @Nullable String error);
}
