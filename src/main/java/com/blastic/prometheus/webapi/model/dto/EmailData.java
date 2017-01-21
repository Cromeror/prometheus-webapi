package com.blastic.prometheus.webapi.model.dto;

/**
 * @author Cristóbal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class EmailData {

    protected String address;

    public EmailData() {
    }

    public EmailData(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
