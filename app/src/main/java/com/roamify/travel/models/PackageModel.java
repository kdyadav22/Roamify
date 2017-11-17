package com.roamify.travel.models;

/**
 * Created by Kapil on 11/11/17.
 */

public class PackageModel {
    String packageId, packageName, packageDuration, packagePrice, packageImageName;

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageDuration() {
        return packageDuration;
    }

    public void setPackageDuration(String packageDuration) {
        this.packageDuration = packageDuration;
    }

    public String getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(String packagePrice) {
        this.packagePrice = packagePrice;
    }

    public String getPackageImageName() {
        return packageImageName;
    }

    public void setPackageImageName(String packageImageName) {
        this.packageImageName = packageImageName;
    }
}
