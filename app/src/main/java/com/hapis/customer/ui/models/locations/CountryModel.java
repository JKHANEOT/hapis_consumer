package com.hapis.customer.ui.models.locations;

import com.hapis.customer.ui.models.HapisModel;

import java.util.List;

public class CountryModel implements HapisModel {

    private int id;

    private String sortname;

    private String name;

    private int phoneCode;

    private List<StateModel> stateModels;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSortname() {
        return sortname;
    }

    public void setSortname(String sortname) {
        this.sortname = sortname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(int phoneCode) {
        this.phoneCode = phoneCode;
    }

    public List<StateModel> getStateModels() {
        return stateModels;
    }

    public void setStateModels(List<StateModel> stateModels) {
        this.stateModels = stateModels;
    }
}
