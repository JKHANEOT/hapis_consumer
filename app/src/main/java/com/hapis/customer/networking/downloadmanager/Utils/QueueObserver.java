package com.hapis.customer.networking.downloadmanager.Utils;

/**
 * Created by JKHAN
 */
public interface QueueObserver {

    void wakeUp(int taskID);
}
