package com.hapis.customer.ui.adapters.datamodels;

/**
 * Created by Javeed on 2/27/2018.
 */

public abstract class GroupDataListItem {

    public static final int TYPE_DATE = 0;
    public static final int TYPE_GENERAL = 1;

    abstract public int getType();
}
