package com.usi.service.entity;

import java.util.Date;

/**
 * created  by KDF on 2017/11/9.
 */


public  class Device {
    private String type;
    private String addr;
    private String status;
    private Date date = new Date();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
