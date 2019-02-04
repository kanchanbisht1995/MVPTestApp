package com.mvptestapp.infra.webservices.apihelpers.helpers;

import android.content.Context;

import com.mvptestapp.core.callbacks.RestApiCallback;
import com.mvptestapp.core.model.UserListResponseModel;
import com.mvptestapp.infra.webservices.apihelpers.BaseAPIHelper;

public class UserListHelper extends BaseAPIHelper<UserListResponseModel> {

    private final String[] params;

    public UserListHelper(Context context, int retryCount, RestApiCallback<UserListResponseModel> callback, String[] params) {
        super(context, retryCount, callback);
        this.params = params;
    }

    @Override
    protected void createRequestBody() {
    }

    @Override
    public void prepareApiRequest() {
        callAPI(getRetrofit().getUserList());
    }

    @Override
    public void parseResponse(UserListResponseModel response) {
        try {


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
