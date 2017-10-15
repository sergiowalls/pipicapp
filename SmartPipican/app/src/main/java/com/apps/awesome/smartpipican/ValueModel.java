package com.apps.awesome.smartpipican;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

class ValueModel {
    private List<String> dogs;

    public ValueModel(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        this.dogs = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, String.class));
    }

    public List<String> getDogs() {
        return dogs;
    }

    public void setDogs(List<String> dogs) {
        this.dogs = dogs;
    }
}
