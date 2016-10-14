package com.sysongy.poms.gastation.model;

import java.math.BigDecimal;
import java.util.Date;

import com.sysongy.poms.base.model.BaseModel;
import com.sysongy.poms.permi.model.SysUserAccount;

public class Gastation extends BaseModel implements Comparable {

	private String sys_gas_station_id;
	private String gas_station_name;
	private String station_level;
	private String area_id;
	private String station_manager;
	private String email;
	private String province_id;
	private String city_id;
	private String detail;
	private String address;
	private String admin_username;
	private String admin_userpassword;
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
	private String contact_phone;
	private String gas_station_scale;
	private String longitude;
	private String latitude;
	private String expiry_date_frompage;
	private String expiry_date_after;
	private String expiry_date_before;
	private Date created_time;
	private Date expiry_date;
	private Date updated_time;
	private String xy;
	private String company;
	private String map_type;
	private String gas_server;
	private String lng_price;
	private String promotions;
    private String viewCount;
    
    private String shareCount;
    
	private String sys_user_account_id;
	private SysUserAccount account;
	private BigDecimal prepay_balance;
	private Integer prepay_version;
	private String type;
	private String price_effective_time;
	private Float fixed_discount;

	public String getPrice_effective_time() {
		return price_effective_time;
	}

	public void setPrice_effective_time(String price_effective_time) {
		this.price_effective_time = price_effective_time;
	}

	public Float getFixed_discount() {
		return fixed_discount;
	}

	public void setFixed_discount(Float fixed_discount) {
		this.fixed_discount = fixed_discount;
	}

	
	private Double distance;//虚拟距离字段，列表显示时计算距离后排序
	
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getLng_price() {
		return lng_price;
	}

	public void setLng_price(String lng_price) {
		this.lng_price = lng_price;
	}

	public String getPromotions() {
		return promotions;
	}

	public void setPromotions(String promotions) {
		this.promotions = promotions;
	}

	public String getGas_server() {
		return gas_server;
	}

	public void setGas_server(String gas_server) {
		this.gas_server = gas_server;
	}

	public String getMap_type() {
		return map_type;
	}

	public void setMap_type(String map_type) {
		this.map_type = map_type;
	}

	public String getXy() {
		return longitude+","+latitude;
	}

	public void setXy(String xy) {
		this.xy =longitude+","+latitude;;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getPrepay_version() {
		return prepay_version;
	}

	public void setPrepay_version(Integer prepay_version) {
		this.prepay_version = prepay_version;
	}

	public BigDecimal getPrepay_balance() {
		return prepay_balance;
	}

	public void setPrepay_balance(BigDecimal prepay_balance) {
		this.prepay_balance = prepay_balance;
	}

	public String getStation_level() {
		return station_level;
	}

	public void setStation_level(String station_level) {
		this.station_level = station_level;
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

	public SysUserAccount getAccount() {
		return account;
	}

	public void setAccount(SysUserAccount account) {
		this.account = account;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getSys_user_account_id() {
		return sys_user_account_id;
	}

	public void setSys_user_account_id(String sys_user_account_id) {
		this.sys_user_account_id = sys_user_account_id;
	}

	public String getStation_manager() {
		return station_manager;
	}

	public void setStation_manager(String station_manager) {
		this.station_manager = station_manager;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getExpiry_date_after() {
		return expiry_date_after;
	}

	public void setExpiry_date_after(String expiry_date_after) {
		this.expiry_date_after = expiry_date_after;
	}

	public String getExpiry_date_before() {
		return expiry_date_before;
	}

	public void setExpiry_date_before(String expiry_date_before) {
		this.expiry_date_before = expiry_date_before;
	}

	public String getExpiry_date_frompage() {
		return expiry_date_frompage;
	}

	public void setExpiry_date_frompage(String expiry_date_frompage) {
		this.expiry_date_frompage = expiry_date_frompage;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getSys_gas_station_id() {
		return sys_gas_station_id;
	}

	public void setSys_gas_station_id(String sys_gas_station_id) {
		this.sys_gas_station_id = sys_gas_station_id;
	}

	public String getGas_station_name() {
		return gas_station_name;
	}

	public void setGas_station_name(String gas_station_name) {
		this.gas_station_name = gas_station_name;
	}

	public String getContact_phone() {
		return contact_phone;
	}

	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}

	public String getGas_station_scale() {
		return gas_station_scale;
	}

	public void setGas_station_scale(String gas_station_scale) {
		this.gas_station_scale = gas_station_scale;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
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

	public Date getExpiry_date() {
		return expiry_date;
	}

	public void setExpiry_date(Date expiry_date) {
		this.expiry_date = expiry_date;
	}

	public String getViewCount() {
		return viewCount;
	}

	public void setViewCount(String viewCount) {
		this.viewCount = viewCount;
	}

	public String getShareCount() {
		return shareCount;
	}

	public void setShareCount(String shareCount) {
		this.shareCount = shareCount;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	@Override
	public int compareTo(Object obj) {
		int i = 0;
		Gastation gastation = (Gastation)obj;
		i = this.distance.compareTo(gastation.distance);
			return i;
	}
}
