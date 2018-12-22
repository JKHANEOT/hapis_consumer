package com.hapis.customer.ui.adapters.datamodels;

/**
 * Created by Rahul on 3/5/2018.
 */

public class DateItemLong extends GroupDataListItem {

    private Long date;

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    @Override
    public int getType() {
        return TYPE_DATE;
    }
}
