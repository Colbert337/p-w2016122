package com.sysongy.poms.usysparam.model;

import java.util.List;

public class Usysparam {
	
	private String gcode;
	private String mcode;
	private String mname;
	private String data;
	private String scode;
	private List<Usysparam> usysparams;
	
	public String getGcode() {
		return gcode;
	}
	public void setGcode(String gcode) {
		this.gcode = gcode;
	}
	public String getMcode() {
		return mcode;
	}
	public void setMcode(String mcode) {
		this.mcode = mcode;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getScode() {
		return scode;
	}
	public void setScode(String scode) {
		this.scode = scode;
	}

	public List<Usysparam> getUsysparams() {
		return usysparams;
	}

	public void setUsysparams(List<Usysparam> usysparams) {
		this.usysparams = usysparams;
	}
}
