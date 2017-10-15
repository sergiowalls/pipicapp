package com.apps.awesome.smartpipican;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashSet;
import java.util.Set;

class Pipican {
    private String title;
    private LatLng latLng;
    private String address;
    private Set<Facility> facilities;

    Pipican() {}

    Pipican(LatLng latLng, String title) {
        this.latLng = latLng;
        this.title = title;
        this.facilities = new HashSet<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void addWaterFacility() {
        this.facilities.add(Facility.WATER);
    }

    public void addBagsFacility() {
        this.facilities.add(Facility.BAGS);
    }

    public void addBigSizeFacility() {
        this.facilities.add(Facility.BIG_SIZE);
    }

    public void addSpecialZonesFacility() {
        this.facilities.add(Facility.SPECIAL_ZONES);
    }

    public void addAgilityFacility() {
        this.facilities.add(Facility.AGILITY);
    }

    private enum Facility {
        WATER, BAGS, BIG_SIZE, SPECIAL_ZONES, AGILITY
    }
}
