package com.sysongy.poms.transportion.model;

import java.math.BigDecimal;
import java.util.Date;

import com.sysongy.poms.base.model.BaseModel;
import com.sysongy.poms.permi.model.SysUserAccount;

public class Transportion extends BaseModel{
	
    private String sys_transportion_id;

    private String sys_user_account_id;

    private String transportion_name;
    
    private String station_manager;

    private String contact_phone;
    
    private String admin_username;
    
	private String admin_userpassword;

    private String email;

    private String area_id;

    private String province_id;

    private String city_id;

    private String address;

    private String longitude;

    private String latitude;

    private Date expiry_date;
    
    private String expiry_date_frompage;
    
    private String expiry_date_before;
    
    private String expiry_date_after;

    private String salesmen_id;

    private String salesmen_name;

    private String operations_id;

    private String operations_name;

    private String indu_com_number;

    private String tax_number;

    private String indu_com_certif;

    private String tax_certif;

    private String lng_certif;

    private String dcp_certif;

    private String platform_type;

    private String status;

    private Date created_time;

    private Date updated_time;
        
	private SysUserAccount account;

	private String pay_code;
	
	private BigDecimal deposit;

	public BigDecimal getDeposit() {
		return deposit;
	}

	public void setDeposit(BigDecimal deposit) {
		this.deposit = deposit;
	}

	public SysUserAccount getAccount() {
		return account;
	}

	public void setAccount(SysUserAccount account) {
		this.account = account;
	}

	public String getAdmin_username() {
		return admin_username;
	}

	public void setAdmin_username(String admin_username) {
		this.admin_username = admin_username;
	}

	public String getAdmin_userpassword() {
		return admin_userpassword;
	}

	public void setAdmin_userpassword(String admin_userpassword) {
		this.admin_userpassword = admin_userpassword;
	}

	public String getStation_manager() {
		return station_manager;
	}

	public void setStation_manager(String station_manager) {
		this.station_manager = station_manager;
	}

	public String getTransportion_name() {
		return transportion_name;
	}

	public void setTransportion_name(String transportion_name) {
		this.transportion_name = transportion_name;
	}

	public String getExpiry_date_frompage() {
		return expiry_date_frompage;
	}

	public void setExpiry_date_frompage(String expiry_date_frompage) {
		this.expiry_date_frompage = expiry_date_frompage;
	}

	public String getExpiry_date_before() {
		return expiry_date_before;
	}

	public void setExpiry_date_before(String expiry_date_before) {
		this.expiry_date_before = expiry_date_before;
	}

	public String getExpiry_date_after() {
		return expiry_date_after;
	}

	public void setExpiry_date_after(String expiry_date_after) {
		this.expiry_date_after = expiry_date_after;
	}
	
	public String getSys_transportion_id() {
		return sys_transportion_id;
	}

	public void setSys_transportion_id(String sys_transportion_id) {
		this.sys_transportion_id = sys_transportion_id;
	}

	public String getSys_user_account_id() {
		return sys_user_account_id;
	}

	public void setSys_user_account_id(String sys_user_account_id) {
		this.sys_user_account_id = sys_user_account_id;
	}

	public String getContact_phone() {
		return contact_phone;
	}

	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getArea_id() {
		return area_id;
	}

	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}

	public String getProvince_id() {
		return province_id;
	}

	public void setProvince_id(String province_id) {
		this.province_id = province_id;
	}

	public String getCity_id() {
		return city_id;
	}

	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public Date getExpiry_date() {
		return expiry_date;
	}

	public void setExpiry_date(Date expiry_date) {
		this.expiry_date = expiry_date;
	}

	public String getSalesmen_id() {
		return salesmen_id;
	}

	public void setSalesmen_id(String salesmen_id) {
		this.salesmen_id = salesmen_id;
	}

	public String getSalesmen_name() {
		return salesmen_name;
	}

	public void setSalesmen_name(String salesmen_name) {
		this.salesmen_name = salesmen_name;
	}

	public String getOperations_id() {
		return operations_id;
	}

	public void setOperations_id(String operations_id) {
		this.operations_id = operations_id;
	}

	public String getOperations_name() {
		return operations_name;
	}

	public void setOperations_name(String operations_name) {
		this.operations_name = operations_name;
	}

	public String getIndu_com_number() {
		return indu_com_number;
	}

	public void setIndu_com_number(String indu_com_number) {
		this.indu_com_number = indu_com_number;
	}

	public String getTax_number() {
		return tax_number;
	}

	public void setTax_number(String tax_number) {
		this.tax_number = tax_number;
	}

	public String getIndu_com_certif() {
		return indu_com_certif;
	}

	public void setIndu_com_certif(String indu_com_certif) {
		this.indu_com_certif = indu_com_certif;
	}

	public String getTax_certif() {
		return tax_certif;
	}

	public void setTax_certif(String tax_certif) {
		this.tax_certif = tax_certif;
	}

	public String getLng_certif() {
		return lng_certif;
	}

	public void setLng_certif(String lng_certif) {
		this.lng_certif = lng_certif;
	}

	public String getDcp_certif() {
		return dcp_certif;
	}

	public void setDcp_certif(String dcp_certif) {
		this.dcp_certif = dcp_certif;
	}

	public String getPlatform_type() {
		return platform_type;
	}

	public void setPlatform_type(String platform_type) {
		this.platform_type = platform_type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreated_time() {
		return created_time;
	}

	public void setCreated_time(Date created_time) {
		this.created_time = created_time;
	}

	public Date getUpdated_time() {
		return updated_time;
	}

	public void setUpdated_time(Date updated_time) {
		this.updated_time = updated_time;
	}

	public String getPay_code() {
		return pay_code;
	}

	public void setPay_code(String pay_code) {
		this.pay_code = pay_code;
	}
}