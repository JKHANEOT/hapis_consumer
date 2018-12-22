package com.hapis.customer.ui.adapters.datamodels;

/**
 * Created by Javeed on 2/27/2018.
 */

public class DateItem extends GroupDataListItem {

    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int getType() {
        return TYPE_DATE;
    }
}
