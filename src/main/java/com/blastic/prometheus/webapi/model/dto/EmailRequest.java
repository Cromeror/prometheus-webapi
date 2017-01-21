package com.blastic.prometheus.webapi.model.dto;

/**
 * @author Cristóbal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class EmailRequest extends EmailData{

    public EmailRequest() {
    }

    public EmailRequest(String address) {
        super(address);
    }
}
