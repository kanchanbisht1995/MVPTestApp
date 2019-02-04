package com.mvptestapp.infra.webservices.apihelpers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.mvptestapp.core.callbacks.RestApiCallback;
import com.mvptestapp.core.localexception.RequestFailureException;
import com.mvptestapp.core.model.BaseResponseModel;
import com.mvptestapp.infra.utils.AppConstants;
import com.mvptestapp.infra.webservices.apidefination.RestAPIs;
import com.mvptestapp.infra.webservices.apiprovider.RestAPIProvider;

import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BaseAPIHelper<T extends BaseResponseModel> {

    private final String TAG = this.getClass().getSimpleName();

    private final int RETRY_LIMIT;
    private int retryCount;
    private RestApiCallback<T> callback;

    protected Context context;

    protected HashMap<String, Object> requestBody;

    public BaseAPIHelper(@NonNull Context context, int retryCount, @NonNull RestApiCallback<T> callback) {

        this.context = context;
        this.callback = callback;

        if (retryCount >= 3) RETRY_LIMIT = 3;
        else if (retryCount <= 0) RETRY_LIMIT = 0;
        else RETRY_LIMIT = retryCount;

    }

    private HashMap<String, String> getHeader() {
        HashMap<String, String> headerMap = new HashMap<>();
        // headerMap.put(APIConstants.authToken, PrefsConfig.getString(PreferenceConstants.requestToken));
        return headerMap;
    }

    protected abstract void createRequestBody();

    public abstract void prepareApiRequest();

    public abstract void parseResponse(T cipherText);

    protected RestAPIs getRetrofit() {
        return RestAPIProvider.getRestAPIInstance();
    }

    protected void callAPI(Call<T> call) {

        Log.d(TAG, "Request Url -> " + call.request().url());
        Log.d(TAG, "Request Headers -> " + call.request().headers());
        if (requestBody != null)
            Log.d(TAG, "Request Body -> " + new JSONObject(requestBody).toString());

        call.enqueue(responseCallback);
    }

    private Callback<T> responseCallback = new Callback<T>() {

        @Override
        public void onResponse(Call<T> call, Response<T> response) {

            if (!isCallSuccess(response.code()))
                if (++retryCount < RETRY_LIMIT) retry(call);
                else {
                    Log.d(TAG, "Response -> " + response.code() + response.message());
                    callback.onFailure(new RequestFailureException(AppConstants.universalErrorMessage));
                }
            else {
                T responseBody = response.body();
                parseResponse(responseBody);
                callback.onResponse(responseBody);
            }

        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {
            Log.e(TAG, "Response ->" + t.getMessage());
            if (++retryCount < RETRY_LIMIT && call != null) retry(call);
            else
                callback.onFailure(new RequestFailureException(AppConstants.universalErrorMessage));
        }
    };

    private void retry(Call<T> call) {
        Log.v(TAG, "Retrying API Call -  (" + retryCount + " / " + RETRY_LIMIT + ")");
        call.clone().enqueue(responseCallback);
    }

    private static boolean isCallSuccess(int code) {
        return (code >= 200 && code < 400);
    }
}