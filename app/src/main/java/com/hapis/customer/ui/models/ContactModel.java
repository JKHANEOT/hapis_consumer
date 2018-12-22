package com.hapis.customer.ui.models;

public class ContactModel {

    private String email;

    private String mobile;

    private Integer isdCode;

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email the new email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the mobile.
     *
     * @return the mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * Sets the mobile.
     *
     * @param mobile the new mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * Gets the isd code.
     *
     * @return the isd code
     */
    public Integer getIsdCode() {
        return isdCode;
    }

    /**
     * Sets the isd code.
     *
     * @param isdCode the new isd code
     */
    public void setIsdCode(Integer isdCode) {
        this.isdCode = isdCode;
    }

}