package com.apps.awesome.smartpipican;

import com.google.android.gms.maps.model.LatLng;

class Pipican {
    private final String title;
    private final LatLng latLng;

    Pipican(LatLng latLng, String title) {
        this.latLng = latLng;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public LatLng getLatLng() {
        return latLng;
    }
}
