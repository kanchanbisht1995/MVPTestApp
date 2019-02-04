package com.mvptestapp.infra.webservices.apiprovider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mvptestapp.infra.webservices.apidefination.RestAPIs;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RestAPIProvider

{

    private static class RetrofitInitiator {

        private static String baseUrl = "https://reqres.in/";

        private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        private static final Gson gson = new GsonBuilder()/*.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")*/
                .setLenient()
                .create();

        private static RestAPIs restAPI = null;

        public static void generateNewRestAPI() {
            restAPI = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                  //  .addConverterFactory(new NullOnEmptyConverterFactory())
                    .client(okHttpClient)
                    .build()
                    .create(RestAPIs.class);
        }
    }

    public static RestAPIs getRestAPIInstance() {
        if (RetrofitInitiator.restAPI == null) RetrofitInitiator.generateNewRestAPI();
        else if (!RetrofitInitiator.baseUrl.equalsIgnoreCase("https://reqres.in/")) {
            RetrofitInitiator.baseUrl = "https://reqres.in/";
            RetrofitInitiator.generateNewRestAPI();
        }

        return RetrofitInitiator.restAPI;
    }
}
