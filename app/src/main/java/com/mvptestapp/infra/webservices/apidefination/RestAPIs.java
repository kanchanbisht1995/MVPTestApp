package com.mvptestapp.infra.webservices.apidefination;

import com.mvptestapp.core.model.LoginResponseModel;
import com.mvptestapp.core.model.UserListResponseModel;
import com.mvptestapp.infra.utils.URLEndPoints;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestAPIs {

    @FormUrlEncoded
    @POST(URLEndPoints.LOGIN)
    Call<LoginResponseModel> login(@FieldMap HashMap<String, Object> body);

    @GET(URLEndPoints.USER_LIST)
    Call<UserListResponseModel> getUserList();


}
