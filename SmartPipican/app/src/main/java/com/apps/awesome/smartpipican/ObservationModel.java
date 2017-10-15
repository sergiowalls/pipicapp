package com.apps.awesome.smartpipican;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

class ObservationModel {
    private ValueModel value;
    private String timestamp;
    private String location;

    public ValueModel getValue() {
        return value;
    }

    public void setValue(ValueModel value) {
        this.value = value;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
