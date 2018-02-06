package com.roamify.travel.models;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Kapil on 11/11/17.
 */

public class PackageDetailsModel {
    String id ;
    String locationId ;
    String activityId ;
    String packageName ;
    String address  ;
    String packagePrice ;
    String description ;
    String features ;
    String specification ;
    String ratings ;
    JSONArray source ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(String packagePrice) {
        this.packagePrice = packagePrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public JSONArray getSource() {
        return source;
    }

    public void setSource(JSONArray source) {
        this.source = source;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String[] getGalleryImages() {
        return galleryImages;
    }

    public void setGalleryImages(String[] galleryImages) {
        this.galleryImages = galleryImages;
    }

    String duration ;
    String [] galleryImages;
}
