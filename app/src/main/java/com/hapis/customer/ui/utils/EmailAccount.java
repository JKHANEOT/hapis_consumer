package com.hapis.customer.ui.utils;

/**
 * Created by Rahul on 4/25/2017.
 */

public class EmailAccount {
    public String name;
    public String type;

    EmailAccount(String name, String type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return "[name :" + name + ", type:" + type + "]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
