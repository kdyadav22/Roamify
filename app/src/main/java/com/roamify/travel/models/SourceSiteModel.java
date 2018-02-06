package com.roamify.travel.models;

/**
 * Created by Kapil on 10/01/18.
 */

public class SourceSiteModel {
    private String sourceId;
    private String sourceUrl;
    private String rating ;
    public String getSourceId() {
        return sourceId;
    }
    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }
    public String getSourceUrl() {
        return sourceUrl;
    }
    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }
    public String getRating() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }
}
