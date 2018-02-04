package com.nexuslink.wenavi.model;

import com.amap.api.maps.model.LatLng;

/**
 * Created by aplrye on 17-8-29.
 */

public class WeNaviLocation {
    private double latitude;//维度
    private double longitude;//经度

    public WeNaviLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
