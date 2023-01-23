package com.ebi.snap_food.utils;

public class MessageWithId {

    int status ;
    String message;
Long id;

    public MessageWithId() {
    }

    public MessageWithId(int status, String message, Long id) {
        this.status = status;
        this.message = message;
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
