package com.hapis.customer.ui.utils;

import java.util.List;

/**
 * Created by Jawed on 11/10/2015.
 */
public class CollectionUtil {

    public static String[] getListOfLongToStringArray(List<Long> input) {

        String[] outPut = null;

        if(input != null && input.size() > 0)
        {
            outPut = new String[input.size()];

            for(int i = 0; i < input.size(); i++)
            {
                outPut[i] = String.valueOf(input.get(i));
            }
        }

        return outPut;
    }
}
