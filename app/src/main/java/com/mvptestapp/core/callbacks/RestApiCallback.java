package com.mvptestapp.core.callbacks;


import com.mvptestapp.core.model.BaseResponseModel;


public interface RestApiCallback<T extends BaseResponseModel> {
    void onResponse(T response);

    void onFailure(Throwable t);
}