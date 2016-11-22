package com.sysongy.poms.base.model;

import java.util.List;

public class BaseModel {

	private List<?> list;
	private Object model;
	private Integer pageNum = 1;
	private Integer pageSize = 20;
	private Integer pageNumMax;
	private Integer convertPageNum;
	private Long total;
	private String orderby;

	public Integer getConvertPageNum() {
		return convertPageNum;
	}

	public void setConvertPageNum(Integer convertPageNum) {
		this.convertPageNum = convertPageNum;
	}

	public Integer getPageNumMax() {
		return pageNumMax;
	}

	public void setPageNumMax(Integer pageNumMax) {
		this.pageNumMax = pageNumMax;
	}

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public Object getModel() {
		return model;
	}

	public void setModel(Object model) {
		this.model = model;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

}
