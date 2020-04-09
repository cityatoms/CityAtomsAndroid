package com.github.abdularis.trackmylocation.GlobalData;

import android.app.Application;

public class ServeiceUrls  extends Application {
    public static final String BASE_URL = "http://ec2-3-12-160-215.us-east-2.compute.amazonaws.com:3000/api/v1/";

    public static final String LOGIN_URL = BASE_URL+"me/users";

   }
