package com.hapis.customer.ui.adapters.datamodels;

import com.hapis.customer.ui.models.HapisModel;

/**
 * Created by Javeed on 2/27/2018.
 */

public class GroupDataGeneralItem extends GroupDataListItem {

    private HapisModel hapisModel;

    public HapisModel getHapisModel() {
        return hapisModel;
    }

    public void setHapisModel(HapisModel hapisModel) {
        this.hapisModel = hapisModel;
    }

    @Override
    public int getType() {
        return TYPE_GENERAL;
    }
}
