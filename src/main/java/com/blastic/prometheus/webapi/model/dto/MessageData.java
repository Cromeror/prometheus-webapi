package com.blastic.prometheus.webapi.model.dto;

/**
 * @author Cristóbal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class MessageData {

    private String message;

    public MessageData() {
    }

    public MessageData(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
