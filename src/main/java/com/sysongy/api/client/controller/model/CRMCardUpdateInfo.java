package com.sysongy.api.client.controller.model;

import com.sysongy.poms.base.model.BaseModel;
import com.sysongy.poms.card.model.GasCard;

import java.util.Date;

/**
 * Created by Administrator on 2016/6/16.
 */
public class CRMCardUpdateInfo extends GasCard {

    private String sys_gas_station_id;

    private Integer startID;

    private Integer endID;

    private String statusType;

    public String getSys_gas_station_id() {
        return sys_gas_station_id;
    }

    public void setSys_gas_station_id(String sys_gas_station_id) {
        this.sys_gas_station_id = sys_gas_station_id;
    }

    public Integer getStartID() {
        return startID;
    }

    public void setStartID(Integer startID) {
        this.startID = startID;
    }

    public Integer getEndID() {
        return endID;
    }

    public void setEndID(Integer endID) {
        this.endID = endID;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

}
