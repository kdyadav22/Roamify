package com.roamify.travel.rawdata;

import com.roamify.travel.R;
import com.roamify.travel.models.ActivityModel;
import com.roamify.travel.models.HotelsModel;
import com.roamify.travel.models.MenuModel;
import com.roamify.travel.models.PackageDetailsModel;
import com.roamify.travel.models.PackageModel;
import com.roamify.travel.models.PackageTabModel;
import com.roamify.travel.models.StateModel;
import com.roamify.travel.models.StateWiseActivityModel;
import com.roamify.travel.models.ZoneModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        title5.setActivityId("w5");
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
        title1.setActivityName("Paragliding");
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

    public static ArrayList<PackageModel> setPackage() {
        ArrayList<PackageModel> arrayList = new ArrayList<>();

        PackageModel title1 = new PackageModel();
        title1.setPackageName("Chadar Frozen River");
        title1.setPackageDuration("4N/5D");
        title1.setPackagePrice("15000 per person");
        title1.setPackageId("PACK1");
        arrayList.add(title1);

        PackageModel title2 = new PackageModel();
        title2.setPackageName("Roopkund Trek");
        title2.setPackageDuration("3N/4D");
        title2.setPackagePrice("25000 per person");
        title2.setPackageId("PACK2");
        arrayList.add(title2);

        PackageModel title3 = new PackageModel();
        title3.setPackageName("Hot Air Ballooning at Jaipur");
        title3.setPackageDuration("5N/6D");
        title3.setPackagePrice("35000 per person");
        title3.setPackageId("PACK3");
        arrayList.add(title3);

        PackageModel title4 = new PackageModel();
        title4.setPackageName("Scuba Diving");
        title4.setPackageDuration("2N/3D");
        title4.setPackagePrice("10000 per person");
        title4.setPackageId("PACK4");
        arrayList.add(title4);

        PackageModel title5 = new PackageModel();
        title5.setPackageName("Brahma Tal Trek");
        title5.setPackageDuration("6N/7D");
        title5.setPackagePrice("45000 per person");
        title5.setPackageId("PACK5");
        arrayList.add(title5);

        PackageModel title6 = new PackageModel();
        title6.setPackageName("Everest Base Camp Trek");
        title6.setPackageDuration("3N/4D");
        title6.setPackagePrice("10000 per person");
        title6.setPackageId("PACK6");
        arrayList.add(title6);

        return arrayList;
    }

    public static ArrayList<PackageTabModel> setPackageTabMenu() {
        ArrayList<PackageTabModel> arrayList = new ArrayList<>();

        PackageTabModel title1 = new PackageTabModel();
        title1.setTabName("Description");
        title1.setTabId("Tab1");
        arrayList.add(title1);

        PackageTabModel title3 = new PackageTabModel();
        title3.setTabName("Package Details");
        title3.setTabId("Tab3");
        arrayList.add(title3);

        PackageTabModel title4 = new PackageTabModel();
        title4.setTabName("Map");
        title4.setTabId("Tab4");
        arrayList.add(title4);

        PackageTabModel title5 = new PackageTabModel();
        title5.setTabName("Testimonials");
        title5.setTabId("Tab5");
        arrayList.add(title5);

        return arrayList;
    }

    public static ArrayList<ZoneModel> setZone() {
        ArrayList<ZoneModel> arrayList = new ArrayList<>();

        ZoneModel title1 = new ZoneModel();
        title1.setZoneName("East");
        title1.setZoneId("Z1");
        arrayList.add(title1);

        ZoneModel title3 = new ZoneModel();
        title3.setZoneName("West");
        title3.setZoneId("Z2");
        arrayList.add(title3);

        ZoneModel title4 = new ZoneModel();
        title4.setZoneName("North");
        title4.setZoneId("Z3");
        arrayList.add(title4);

        ZoneModel title5 = new ZoneModel();
        title5.setZoneName("South");
        title5.setZoneId("Z4");
        arrayList.add(title5);

        ZoneModel title6 = new ZoneModel();
        title6.setZoneName("North-East");
        title6.setZoneId("Z5");
        arrayList.add(title6);

        ZoneModel title7 = new ZoneModel();
        title7.setZoneName("South-West");
        title7.setZoneId("Z6");
        arrayList.add(title7);

        return arrayList;
    }

    public static ArrayList<StateModel> setState() {
        ArrayList<StateModel> arrayList = new ArrayList<>();

        //Item1
        StateModel title1 = new StateModel();
        title1.setStateName("Delhi");
        title1.setStateId("STA1");
        title1.setZoneId("Z1");
        arrayList.add(title1);

        //Item2
        StateModel title2 = new StateModel();
        title2.setStateName("Garhwal");
        title2.setStateId("STA2");
        title2.setZoneId("Z1");
        arrayList.add(title2);

        //Item3
        StateModel title3 = new StateModel();
        title3.setStateName("Himachal Pradesh");
        title3.setStateId("STA3");
        title3.setZoneId("Z1");
        arrayList.add(title3);

        StateModel title4 = new StateModel();
        title4.setStateName("Kashmir");
        title4.setStateId("STA4");
        title4.setZoneId("Z2");
        arrayList.add(title4);

        StateModel title5 = new StateModel();
        title5.setStateName("Kumaon");
        title5.setStateId("STA5");
        title5.setZoneId("Z2");
        arrayList.add(title5);

        StateModel title6 = new StateModel();
        title6.setStateName("Ladakh");
        title6.setStateId("STA6");
        title6.setZoneId("Z2");
        arrayList.add(title6);

        StateModel title7 = new StateModel();
        title7.setStateName("Manali");
        title7.setStateId("STA7");
        title7.setZoneId("Z3");
        arrayList.add(title7);

        StateModel title8 = new StateModel();
        title8.setStateName("Rishikesh");
        title8.setStateId("STA8");
        title8.setZoneId("Z3");
        arrayList.add(title8);

        return arrayList;
    }

    public static ArrayList<StateWiseActivityModel> setStateWiseActivity() {
        ArrayList<StateWiseActivityModel> arrayList = new ArrayList<>();

        //Item1
        StateWiseActivityModel title1 = new StateWiseActivityModel();
        title1.setZoneId("Z1");
        title1.setStateId("STA1");
        title1.setActivityId("ACT1");
        title1.setActivityName("Camel Safari");
        arrayList.add(title1);

        //Item1
        StateWiseActivityModel title2 = new StateWiseActivityModel();
        title2.setZoneId("Z1");
        title2.setStateId("STA1");
        title2.setActivityId("ACT2");
        title2.setActivityName("Angling");
        arrayList.add(title2);

        //Item1
        StateWiseActivityModel title3 = new StateWiseActivityModel();
        title3.setZoneId("Z1");
        title3.setStateId("STA1");
        title3.setActivityId("ACT3");
        title3.setActivityName("Kayaking");
        arrayList.add(title3);

        //Item1
        StateWiseActivityModel title4 = new StateWiseActivityModel();
        title4.setZoneId("Z1");
        title4.setStateId("STA2");
        title4.setActivityId("ACT4");
        title4.setActivityName("Rafting");
        arrayList.add(title4);

        //Item1
        StateWiseActivityModel title5 = new StateWiseActivityModel();
        title5.setZoneId("Z1");
        title5.setStateId("STA2");
        title5.setActivityId("ACT5");
        title5.setActivityName("Aerosports");
        arrayList.add(title5);

        //Item1
        StateWiseActivityModel title6 = new StateWiseActivityModel();
        title6.setZoneId("Z2");
        title6.setStateId("STA3");
        title6.setActivityId("ACT6");
        title6.setActivityName("Paragliding");
        arrayList.add(title6);

        //Item1
        StateWiseActivityModel title7 = new StateWiseActivityModel();
        title7.setZoneId("Z2");
        title7.setStateId("STA3");
        title7.setActivityId("ACT7");
        title7.setActivityName("Skydiving");
        arrayList.add(title7);

        //Item1
        StateWiseActivityModel title8 = new StateWiseActivityModel();
        title8.setZoneId("Z3");
        title8.setStateId("STA4");
        title8.setActivityId("ACT8");
        title8.setActivityName("Zip Line");
        arrayList.add(title8);

        //Item1
        StateWiseActivityModel title9 = new StateWiseActivityModel();
        title9.setZoneId("Z3");
        title9.setStateId("STA5");
        title9.setActivityId("ACT9");
        title9.setActivityName("Camping");
        arrayList.add(title9);

        //Item1
        StateWiseActivityModel title10 = new StateWiseActivityModel();
        title10.setZoneId("Z3");
        title10.setStateId("STA6");
        title10.setActivityId("ACT10");
        title10.setActivityName("Cycling");
        arrayList.add(title10);

        //Item1
        StateWiseActivityModel title11 = new StateWiseActivityModel();
        title11.setZoneId("Z3");
        title11.setStateId("STA7");
        title11.setActivityId("ACT11");
        title11.setActivityName("Caving");
        arrayList.add(title11);

        //Item1
        StateWiseActivityModel title12 = new StateWiseActivityModel();
        title12.setZoneId("Z1");
        title12.setStateId("STA8");
        title12.setActivityId("ACT12");
        title12.setActivityName("Mountaineering");
        arrayList.add(title12);

        //Item1
        StateWiseActivityModel title13 = new StateWiseActivityModel();
        title13.setZoneId("Z1");
        title13.setStateId("STA10");
        title13.setActivityId("ACT13");
        title13.setActivityName("Motorsports");
        arrayList.add(title13);

        //Item1
        StateWiseActivityModel title14 = new StateWiseActivityModel();
        title14.setZoneId("Z2");
        title14.setStateId("STA11");
        title14.setActivityId("ACT14");
        title14.setActivityName("Jeep Safari");
        arrayList.add(title14);

        return arrayList;
    }

    public static ArrayList<PackageDetailsModel> setPackageDetails() {
        ArrayList<PackageDetailsModel> arrayList = new ArrayList<>();

        PackageDetailsModel title1 = new PackageDetailsModel();

        title1.setPackageId("PD1");
        title1.setPackageDetails("");
        title1.setPackageAddress("");
        title1.setPackageReviews("");
        title1.setPackageTestimonials("");

        JSONObject jsonObject = new JSONObject();
        JSONArray customJsonArray = new JSONArray();
        try {
            customJsonArray.put(R.drawable.playcardp403_1);
            customJsonArray.put(R.drawable.playcardp403_2);
            customJsonArray.put(R.drawable.playcardp403_3);
            customJsonArray.put(R.drawable.playcardp402_2);
            jsonObject.put("ImageArray", customJsonArray);
            title1.setPackageImages(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        arrayList.add(title1);


        return arrayList;
    }
}
