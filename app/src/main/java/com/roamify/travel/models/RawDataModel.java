package com.roamify.travel.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Kapil on 28/09/17.
 */

public class RawDataModel implements Serializable{
    public ArrayList<ActivityModel> getActivityModelarrayList() {
        return activityModelarrayList;
    }

    public void setActivityModelarrayList(ArrayList<ActivityModel> activityModelarrayList) {
        this.activityModelarrayList = activityModelarrayList;
    }

    transient ArrayList<ActivityModel> activityModelarrayList;
}
