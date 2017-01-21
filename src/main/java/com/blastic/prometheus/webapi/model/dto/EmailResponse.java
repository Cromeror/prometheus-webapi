package com.blastic.prometheus.webapi.model.dto;

/**
 * @author Cristóbal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class EmailResponse extends EmailData{

    private Boolean confirmed;

    public EmailResponse() {
    }

    public EmailResponse(String address, Boolean confirmed) {
        super(address);
        this.confirmed = confirmed;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }
}
