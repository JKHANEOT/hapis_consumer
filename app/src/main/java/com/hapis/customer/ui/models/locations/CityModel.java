package com.hapis.customer.ui.models.locations;

import com.hapis.customer.ui.models.HapisModel;

public class CityModel implements HapisModel {

    private String id;

    private String name;

    private String state_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }
}
