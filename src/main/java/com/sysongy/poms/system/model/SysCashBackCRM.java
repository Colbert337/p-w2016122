package com.sysongy.poms.system.model;

import com.sysongy.poms.base.model.BaseModel;

import java.util.Date;
import java.util.List;

public class SysCashBackCRM extends BaseModel{

	private List<SysCashBack> sysCashBackForCard;

	private List<SysCashBack> sysCashBackForCash;

	private List<SysCashBack>  sysCashBackForPOS;

	public List<SysCashBack> getSysCashBackForCard() {
		return sysCashBackForCard;
	}

	public void setSysCashBackForCard(List<SysCashBack> sysCashBackForCard) {
		this.sysCashBackForCard = sysCashBackForCard;
	}

	public List<SysCashBack> getSysCashBackForCash() {
		return sysCashBackForCash;
	}

	public void setSysCashBackForCash(List<SysCashBack> sysCashBackForCash) {
		this.sysCashBackForCash = sysCashBackForCash;
	}

	public List<SysCashBack> getSysCashBackForPOS() {
		return sysCashBackForPOS;
	}

	public void setSysCashBackForPOS(List<SysCashBack> sysCashBackForPOS) {
		this.sysCashBackForPOS = sysCashBackForPOS;
	}
}