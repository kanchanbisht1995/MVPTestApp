package com.mvptestapp.infra.webservices.apihelpers.helpers;

import android.content.Context;

import com.mvptestapp.core.callbacks.RestApiCallback;
import com.mvptestapp.core.model.LoginResponseModel;
import com.mvptestapp.infra.utils.AppConstants;
import com.mvptestapp.infra.webservices.apihelpers.BaseAPIHelper;

import java.util.HashMap;

public class PostLoginHelper extends BaseAPIHelper<LoginResponseModel> {

    private final String[] params;

    public PostLoginHelper(Context context, int retryCount, RestApiCallback<LoginResponseModel> callback, String[] params) {
        super(context, retryCount, callback);
        this.params = params;
    }

    @Override
    protected void createRequestBody() {
        requestBody = new HashMap<>();
        requestBody.put(AppConstants.email,params[0]);
        requestBody.put(AppConstants.password,params[1]);

    }

    @Override
    public void prepareApiRequest() {
        createRequestBody();
        callAPI(getRetrofit().login(requestBody));
    }

    @Override
    public void parseResponse(LoginResponseModel response) {
        try {


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
