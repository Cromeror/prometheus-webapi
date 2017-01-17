package com.blastic.prometheus.webapi.model.dto;

/**
 * @author Cristóbal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class EmailData {

    protected String address;

    private Boolean confirmed;

    public EmailData() {
    }

    public EmailData(String address) {
        this.address = address;
    }

    public EmailData(String address, Boolean confirmed) {
        this.address = address;
        this.confirmed = confirmed;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }
}
