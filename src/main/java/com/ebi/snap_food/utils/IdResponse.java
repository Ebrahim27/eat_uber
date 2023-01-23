package com.ebi.snap_food.utils;

public class IdResponse {
    int status ;
    Long id;


    public IdResponse(int status, Long id) {
        this.status = status;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }




}


