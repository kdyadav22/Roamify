package com.roamify.travel.utils;

import com.roamify.travel.listeners.ActivityItemClickListener;
import com.roamify.travel.listeners.AllActivityItemClickListener;
import com.roamify.travel.listeners.RatingBarCallback;

/**
 * Created by Panalink-03 on 11/24/2017.
 */

public class Constants {
    public static ActivityItemClickListener activityItemClickListener;
    public static AllActivityItemClickListener allActivityItemClickListener;
    public static RatingBarCallback ratingBarCallback;

    public static final int SOCKET_TIME_OUT = 30 * 1000; //30 seconds; Changeable
    public static final int DEFAULT_MAX_RETRIES = 3;
    public static final int IMAGE_SOCKET_TIME_OUT = 60 * 1000 * 2; //120 seconds; Changeable

    public static final String BaseImageUrl = "http://mohanpackaging.com/app/appImage/";
    public static final String BaseUrl = "http://mohanpackaging.com/app/";
    public static final String GetAllResultApi = BaseUrl + "getAllResult.php";

}
