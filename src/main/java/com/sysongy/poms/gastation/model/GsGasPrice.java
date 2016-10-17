package com.sysongy.poms.gastation.model;

import com.sysongy.poms.base.model.BaseModel;
import com.sysongy.poms.liquid.model.SysGasSource;
import com.sysongy.poms.usysparam.model.Usysparam;

import java.math.BigDecimal;
import java.util.Date;

public class GsGasPrice extends BaseModel {
	
    private String gsGasPriceId;

    private String sysGasStationId;

    private String gasNum;

    private Usysparam gasNumInfo;

    private String gasName;

    private Usysparam gasNameInfo;

    private String price_id;

    private String unit;

    private Usysparam unitInfo;

    private String remark;

    private Date createdDate;

    private Date updatedDate;
    
    private String created_date_before;
	
	private String created_date_after;

    private ProductPrice productPriceInfo;

    private String gs_gas_source_id;
    
    private Gastation gas_station;

    private SysGasSource gs_gas_source_info;

    private int is_deleted;

    private String productType = "1";

    private String minus_money; //立减金额
    private Float fixed_discount;//固定折扣
    private String preferential_type;//优惠类型

    public String getPreferential_type() {
        return preferential_type;
    }

    public void setPreferential_type(String preferential_type) {
        this.preferential_type = preferential_type;
    }

    public String getMinus_money() {
        return minus_money;
    }

    public void setMinus_money(String minus_money) {
        this.minus_money = minus_money;
    }

    public Float getFixed_discount() {
        return fixed_discount;
    }

    public void setFixed_discount(Float fixed_discount) {
        this.fixed_discount = fixed_discount;
    }

    public String getCreated_date_before() {
		return created_date_before;
	}

	public void setCreated_date_before(String created_date_before) {
		this.created_date_before = created_date_before;
	}

	public String getCreated_date_after() {
		return created_date_after;
	}

	public void setCreated_date_after(String created_date_after) {
		this.created_date_after = created_date_after;
	}

	public Gastation getGas_station() {
		return gas_station;
	}

	public void setGas_station(Gastation gas_station) {
		this.gas_station = gas_station;
	}

	public String getGsGasPriceId() {
        return gsGasPriceId;
    }

    public void setGsGasPriceId(String gsGasPriceId) {
        this.gsGasPriceId = gsGasPriceId == null ? null : gsGasPriceId.trim();
    }

    public String getSysGasStationId() {
        return sysGasStationId;
    }

    public void setSysGasStationId(String sysGasStationId) {
        this.sysGasStationId = sysGasStationId == null ? null : sysGasStationId.trim();
    }

    public String getGasNum() {
        return gasNum;
    }

    public void setGasNum(String gasNum) {
        this.gasNum = gasNum == null ? null : gasNum.trim();
    }

    public String getGasName() {
        return gasName;
    }

    public void setGasName(String gasName) {
        this.gasName = gasName == null ? null : gasName.trim();
    }

    public Usysparam getGasNameInfo() {
        return gasNameInfo;
    }

    public void setGasNameInfo(Usysparam gasNameInfo) {
        this.gasNameInfo = gasNameInfo;
    }

    public String getPrice_id() {
        return price_id;
    }

    public void setPrice_id(String price_id) {
        this.price_id = price_id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Usysparam getGasNumInfo() {
        return gasNumInfo;
    }

    public void setGasNumInfo(Usysparam gasNumInfo) {
        this.gasNumInfo = gasNumInfo;
    }

    public ProductPrice getProductPriceInfo() {
        return productPriceInfo;
    }

    public void setProductPriceInfo(ProductPrice productPriceInfo) {
        this.productPriceInfo = productPriceInfo;
    }

    public String getGs_gas_source_id() {
        return gs_gas_source_id;
    }

    public void setGs_gas_source_id(String gs_gas_source_id) {
        this.gs_gas_source_id = gs_gas_source_id;
    }

    public SysGasSource getGs_gas_source_info() {
        return gs_gas_source_info;
    }

    public void setGs_gas_source_info(SysGasSource gs_gas_source_info) {
        this.gs_gas_source_info = gs_gas_source_info;
    }

    public int getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(int is_deleted) {
        this.is_deleted = is_deleted;
    }

    public Usysparam getUnitInfo() {
        return unitInfo;
    }

    public void setUnitInfo(Usysparam unitInfo) {
        this.unitInfo = unitInfo;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}