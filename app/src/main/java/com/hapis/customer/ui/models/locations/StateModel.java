package com.hapis.customer.ui.models.locations;

import com.hapis.customer.ui.models.HapisModel;

import java.util.List;

public class StateModel implements HapisModel {

    private String id;

    private String name;

    private String country_id;

    private List<CityModel> cityModels;

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

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public List<CityModel> getCityModels() {
        return cityModels;
    }

    public void setCityModels(List<CityModel> cityModels) {
        this.cityModels = cityModels;
    }
}
