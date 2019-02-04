package com.mvptestapp.infra.application;

import android.content.Context;
import android.support.multidex.MultiDexApplication;


public class MyApplicationClass extends MultiDexApplication {

    private static int deviceHeight;
    private static int deviceWidth;
    public static Context appContext;
    public static String DeviceID;


    public static MyApplicationClass mvpApp;


    @Override
    public void onCreate() {

        try {

            super.onCreate();
            mvpApp = this;
            appContext = getApplicationContext();


        } catch (Exception e) {

        }

    }


}
