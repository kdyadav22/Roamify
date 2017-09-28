package com.roamify.travel.rawdata;

import com.roamify.travel.R;
import com.roamify.travel.models.ActivityModel;
import com.roamify.travel.models.DestinationModel;
import com.roamify.travel.models.HotelsModel;
import com.roamify.travel.models.MenuItemModel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Kapil on 28/09/17.
 */

public class RawData {
    public static ArrayList<HotelsModel> setHotels() {
        ArrayList<HotelsModel> arrayList = new ArrayList<>();

        //Item1
        HotelsModel title1 = new HotelsModel();
        title1.setHotelName("Welcome Hotels, Dwarka");
        title1.setRating("4.5");
        title1.setReviews(2000);
        title1.setSmallDescription("#1 best values in hotels");
        title1.setTotalPrice(8732);
        title1.setDiscountedPrice(6039);
        title1.setSiteName("Yatra.com");
        arrayList.add(title1);

        //Item2
        HotelsModel title2 = new HotelsModel();
        title2.setHotelName("Taj Mahal Hotels");
        title2.setRating("4");
        title2.setReviews(235);
        title2.setSmallDescription("#1 best values in hotels");
        title2.setTotalPrice(2732);
        title2.setDiscountedPrice(1039);
        title2.setSiteName("Booking.com");
        arrayList.add(title2);

        //Item3
        HotelsModel title3 = new HotelsModel();
        title3.setHotelName("Red Fox Hotel");
        title3.setRating("3.5");
        title3.setReviews(100);
        title3.setSmallDescription("#1 best values in hotels");
        title3.setTotalPrice(9732);
        title3.setDiscountedPrice(4039);
        title3.setSiteName("Yatra.com");
        arrayList.add(title3);

        HotelsModel title4 = new HotelsModel();
        title4.setHotelName("Welcome Hotels, Dwarka");
        title4.setRating("4.5");
        title4.setReviews(2000);
        title4.setSmallDescription("#1 best values in hotels");
        title4.setTotalPrice(8732);
        title4.setDiscountedPrice(6039);
        title4.setSiteName("Yatra.com");
        arrayList.add(title4);

        HotelsModel title5 = new HotelsModel();
        title5.setHotelName("Welcome Hotels, Dwarka");
        title5.setRating("4.5");
        title5.setReviews(2000);
        title5.setSmallDescription("#1 best values in hotels");
        title5.setTotalPrice(8732);
        title5.setDiscountedPrice(6039);
        title5.setSiteName("Yatra.com");
        arrayList.add(title5);

        HotelsModel title6 = new HotelsModel();
        title6.setHotelName("Welcome Hotels, Dwarka");
        title6.setRating("4.5");
        title6.setReviews(2000);
        title6.setSmallDescription("#1 best values in hotels");
        title6.setTotalPrice(8732);
        title6.setDiscountedPrice(6039);
        title6.setSiteName("Yatra.com");
        arrayList.add(title6);

        HotelsModel title7 = new HotelsModel();
        title7.setHotelName("Welcome Hotels, Dwarka");
        title7.setRating("4.5");
        title7.setReviews(2000);
        title7.setSmallDescription("#1 best values in hotels");
        title7.setTotalPrice(8732);
        title7.setDiscountedPrice(6039);
        title7.setSiteName("Yatra.com");
        arrayList.add(title7);

        HotelsModel title8 = new HotelsModel();
        title8.setHotelName("Welcome Hotels, Dwarka");
        title8.setRating("4.5");
        title8.setReviews(2000);
        title8.setSmallDescription("#1 best values in hotels");
        title8.setTotalPrice(8732);
        title8.setDiscountedPrice(6039);
        title8.setSiteName("Yatra.com");
        arrayList.add(title8);

        return arrayList;
    }

    public static ArrayList<ActivityModel> setWaterActivity() {
        ArrayList<ActivityModel> arrayList = new ArrayList<>();

        //Item1
        ActivityModel title1 = new ActivityModel();
        title1.setActivityName("Angling");
        title1.setActivityId("W1");
        arrayList.add(title1);

        //Item2
        ActivityModel title2 = new ActivityModel();
        title2.setActivityName("Kayaking");
        title2.setActivityId("W2");
        arrayList.add(title2);

        //Item3
        ActivityModel title3 = new ActivityModel();
        title3.setActivityName("River Cruising");
        title3.setActivityId("W3");
        arrayList.add(title3);

        ActivityModel title4 = new ActivityModel();
        title4.setActivityName("Rafting");
        title4.setActivityId("W4");
        arrayList.add(title4);

        ActivityModel title5 = new ActivityModel();
        title5.setActivityName("Sailing");
        title5.setActivityId("5");
        arrayList.add(title5);

        ActivityModel title6 = new ActivityModel();
        title6.setActivityName("Scuba");
        title6.setActivityId("W6");
        arrayList.add(title6);

        return arrayList;
    }

    public static ArrayList<ActivityModel> setAirActivity() {
        ArrayList<ActivityModel> arrayList = new ArrayList<>();

        //Item1
        ActivityModel title1 = new ActivityModel();
        title1.setActivityName("Aerosports");
        title1.setActivityId("A1");
        arrayList.add(title1);

        //Item2
        ActivityModel title2 = new ActivityModel();
        title2.setActivityName("Bungee Jumping");
        title2.setActivityId("A2");
        arrayList.add(title2);

        //Item3
        ActivityModel title3 = new ActivityModel();
        title3.setActivityName("Hot Air Ballooning");
        title3.setActivityId("A3");
        arrayList.add(title3);

        ActivityModel title4 = new ActivityModel();
        title4.setActivityName("Paragliding");
        title4.setActivityId("A4");
        arrayList.add(title4);

        ActivityModel title5 = new ActivityModel();
        title5.setActivityName("Skydiving");
        title5.setActivityId("A5");
        arrayList.add(title5);

        ActivityModel title6 = new ActivityModel();
        title6.setActivityName("Zip Line");
        title6.setActivityId("A6");
        arrayList.add(title6);

        return arrayList;
    }

    public static ArrayList<ActivityModel> setLandActivity() {
        ArrayList<ActivityModel> arrayList = new ArrayList<>();

        //Item1
        ActivityModel title1 = new ActivityModel();
        title1.setActivityName("Camel Safari");
        title1.setActivityId("L1");
        arrayList.add(title1);

        //Item2
        ActivityModel title2 = new ActivityModel();
        title2.setActivityName("Camping");
        title2.setActivityId("L2");
        arrayList.add(title2);

        //Item3
        ActivityModel title3 = new ActivityModel();
        title3.setActivityName("Caving");
        title3.setActivityId("L3");
        arrayList.add(title3);

        ActivityModel title4 = new ActivityModel();
        title4.setActivityName("Cycling");
        title4.setActivityId("L4");
        arrayList.add(title4);

        ActivityModel title5 = new ActivityModel();
        title5.setActivityName("Horse Safari");
        title5.setActivityId("L5");
        arrayList.add(title5);

        ActivityModel title6 = new ActivityModel();
        title6.setActivityName("Jeep Safari");
        title6.setActivityId("L6");
        arrayList.add(title6);

        ActivityModel title7 = new ActivityModel();
        title7.setActivityName("Mountaineering");
        title7.setActivityId("L7");
        arrayList.add(title7);

        ActivityModel title8 = new ActivityModel();
        title8.setActivityName("Motorsports");
        title8.setActivityId("L8");
        arrayList.add(title8);

        return arrayList;
    }

    public static ArrayList<ActivityModel> setDestination() {
        ArrayList<ActivityModel> arrayList = new ArrayList<>();

        //Item1
        ActivityModel title1 = new ActivityModel();
        title1.setActivityName("Delhi");
        title1.setActivityId("D1");
        arrayList.add(title1);

        //Item2
        ActivityModel title2 = new ActivityModel();
        title2.setActivityName("Garhwal");
        title2.setActivityId("D2");
        arrayList.add(title2);

        //Item3
        ActivityModel title3 = new ActivityModel();
        title3.setActivityName("Himachal Pradesh");
        title3.setActivityId("D3");
        arrayList.add(title3);

        ActivityModel title4 = new ActivityModel();
        title4.setActivityName("Kashmir");
        title4.setActivityId("D4");
        arrayList.add(title4);

        ActivityModel title5 = new ActivityModel();
        title5.setActivityName("Kumaon");
        title5.setActivityId("D5");
        arrayList.add(title5);

        ActivityModel title6 = new ActivityModel();
        title6.setActivityName("Ladakh");
        title6.setActivityId("D6");
        arrayList.add(title6);

        ActivityModel title7 = new ActivityModel();
        title7.setActivityName("Manali");
        title7.setActivityId("D7");
        arrayList.add(title7);

        ActivityModel title8 = new ActivityModel();
        title8.setActivityName("Rishikesh");
        title8.setActivityId("D8");
        arrayList.add(title8);

        return arrayList;
    }
}
