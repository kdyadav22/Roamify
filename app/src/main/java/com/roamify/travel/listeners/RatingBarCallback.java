package com.roamify.travel.listeners;

import com.roamify.travel.models.SourceSiteModel;

/**
 * Created by Kapil on 06/02/18.
 */

public interface RatingBarCallback {
    void onClickRatingBar(SourceSiteModel sourceSiteModel, int pos);
}
