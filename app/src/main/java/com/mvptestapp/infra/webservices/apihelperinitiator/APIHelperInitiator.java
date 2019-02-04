package com.mvptestapp.infra.webservices.apihelperinitiator;

import android.content.Context;

import com.mvptestapp.core.callbacks.RestApiCallback;
import com.mvptestapp.core.model.LoginResponseModel;
import com.mvptestapp.core.model.UserListResponseModel;
import com.mvptestapp.infra.webservices.apihelpers.helpers.PostLoginHelper;
import com.mvptestapp.infra.webservices.apihelpers.helpers.UserListHelper;

public class APIHelperInitiator {

    public static void initiateLogin(Context context, int retryCount, RestApiCallback<LoginResponseModel> restApiCallback, String... params) {
        new PostLoginHelper(context, retryCount, restApiCallback, params).prepareApiRequest();
    }

    public static void getUserList(Context context, int retryCount, RestApiCallback<UserListResponseModel> restApiCallback, String... params) {
        new UserListHelper(context, retryCount, restApiCallback, params).prepareApiRequest();
    }


}
