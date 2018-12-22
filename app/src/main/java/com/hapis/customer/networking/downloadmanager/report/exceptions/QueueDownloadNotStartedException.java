package com.hapis.customer.networking.downloadmanager.report.exceptions;

/**
 * Created by JKHAN
 */
public class QueueDownloadNotStartedException extends IllegalStateException {

    public QueueDownloadNotStartedException(){
        super("Queue Download not started yet");
    }
}
