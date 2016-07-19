package com.sysongy.api.client.controller.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/19.
 */
public class PayCodeValidModel implements Serializable {

    private static final long serialVersionUID = 7802344903407680621L;

    private String id;

    private int errTimes;

    private boolean isLock;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getErrTimes() {
        return errTimes;
    }

    public void setErrTimes(int errTimes) {
        this.errTimes = errTimes;
    }

    public boolean isLock() {
        return isLock;
    }

    public void setLock(boolean lock) {
        isLock = lock;
    }
}
