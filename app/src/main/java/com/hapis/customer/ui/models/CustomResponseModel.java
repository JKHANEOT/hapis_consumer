package com.hapis.customer.ui.models;

public class CustomResponseModel implements HapisModel {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1896152615197682367L;

    /**
     * The status.
     */
    private ResponseStatus status;

    /**
     * Gets the status.
     *
     * @return the status
     */
    public ResponseStatus getStatus() {
        return status;
    }

    /**
     * Sets the status.
     *
     * @param status the new status
     */
    public void setStatus(ResponseStatus status) {
        this.status = status;
    }
}
