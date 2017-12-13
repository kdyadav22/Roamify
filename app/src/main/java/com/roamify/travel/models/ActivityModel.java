package com.roamify.travel.models;

import java.util.ArrayList;

/**
 * Created by Kapil on 28/09/17.
 */

public class ActivityModel{
    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    String activityName;
    String activityId;

    public int getActivityType() {
        return activityType;
    }

    public void setActivityType(int activityType) {
        this.activityType = activityType;
    }

    int activityType;

    public ArrayList<ActivityModel> getActivityModels() {
        return activityModels;
    }

    public void setActivityModels(ArrayList<ActivityModel> activityModels) {
        this.activityModels = activityModels;
    }

    ArrayList<ActivityModel> activityModels;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    int position;

    public String getActivityIcon() {
        return activityIcon;
    }

    public void setActivityIcon(String activityIcon) {
        this.activityIcon = activityIcon;
    }

    String activityIcon;
}
