package com.apps.awesome.smartpipican;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

class SensorModel {
    private List<ObservationModel> observations;

    public SensorModel(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        this.observations = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, ObservationModel.class));
    }


    public List<ObservationModel> getObservations() {
        return observations;
    }

    public void setObservations(List<ObservationModel> observations) {
        this.observations = observations;
    }
}
