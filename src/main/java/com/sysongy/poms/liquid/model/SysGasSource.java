package com.sysongy.poms.liquid.model;

import java.math.BigDecimal;
import java.util.Date;

public class SysGasSource {
	
	private String sys_gas_source_id;

	private String gas_factory_name;

	private String technology_type;

	private String delivery_method;

	private BigDecimal market_price;

	private String gas_factory_addr;

	private String status;

	private String remark;

	private Date created_date;

	private Date updated_date;

	public String getSys_gas_source_id() {
		return sys_gas_source_id;
	}

	public void setSys_gas_source_id(String sys_gas_source_id) {
		this.sys_gas_source_id = sys_gas_source_id;
	}

	public String getGas_factory_name() {
		return gas_factory_name;
	}

	public void setGas_factory_name(String gas_factory_name) {
		this.gas_factory_name = gas_factory_name;
	}

	public String getTechnology_type() {
		return technology_type;
	}

	public void setTechnology_type(String technology_type) {
		this.technology_type = technology_type;
	}

	public String getDelivery_method() {
		return delivery_method;
	}

	public void setDelivery_method(String delivery_method) {
		this.delivery_method = delivery_method;
	}

	public BigDecimal getMarket_price() {
		return market_price;
	}

	public void setMarket_price(BigDecimal market_price) {
		this.market_price = market_price;
	}

	public String getGas_factory_addr() {
		return gas_factory_addr;
	}

	public void setGas_factory_addr(String gas_factory_addr) {
		this.gas_factory_addr = gas_factory_addr;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public Date getUpdated_date() {
		return updated_date;
	}

	public void setUpdated_date(Date updated_date) {
		this.updated_date = updated_date;
	}

}