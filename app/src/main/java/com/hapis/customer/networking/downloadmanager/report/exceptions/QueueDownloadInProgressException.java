package com.hapis.customer.networking.downloadmanager.report.exceptions;

/**
 * Created by JKHAN
 */
public class QueueDownloadInProgressException extends IllegalAccessException {

    public QueueDownloadInProgressException(){
        super("queue download is already in progress");
    }
}
