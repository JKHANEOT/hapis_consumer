package com.hapis.customer.networking.downloadmanager.Utils.helper;

/**
 * Created by JKHAN
 */
public class SqlString {

    public static String Int(int number){
        return "'"+number+"'";
    }

    public static String String(String name){
        return "'"+name+"'";
    }
}
