package com.roamify.travel.models;

import org.json.JSONObject;

/**
 * Created by Kapil on 11/11/17.
 */

public class PackageDetailsModel {
    String packageId, packageDetails, packageTestimonials, packageAddress, packageReviews;
    JSONObject packageImages;

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getPackageDetails() {
        return packageDetails;
    }

    public void setPackageDetails(String packageDetails) {
        this.packageDetails = packageDetails;
    }

    public String getPackageTestimonials() {
        return packageTestimonials;
    }

    public void setPackageTestimonials(String packageTestimonials) {
        this.packageTestimonials = packageTestimonials;
    }

    public String getPackageAddress() {
        return packageAddress;
    }

    public void setPackageAddress(String packageAddress) {
        this.packageAddress = packageAddress;
    }

    public String getPackageReviews() {
        return packageReviews;
    }

    public void setPackageReviews(String packageReviews) {
        this.packageReviews = packageReviews;
    }

    public JSONObject getPackageImages() {
        return packageImages;
    }

    public void setPackageImages(JSONObject packageImages) {
        this.packageImages = packageImages;
    }
}
