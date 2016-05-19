package com.hbkis.boss.menu.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName     :  TreeNode.java
 * @Encoding     :  UTF-8
 * @Package      :  com.hbkis.boss.menu.model
 * @Link         :  http://www.hbkis.com
 * @Created on   :  2016年1月14日, 下午4:38:48
 * @Author       :  DongdongHe
 * @Copyright    :  Copyright(c) 2015 西安海贝信息科技有限公司
 * @Description  :
 *
 */
public class TreeNode {
	
	private String id;
	
	private String name;
	
	private String pId;
	
	private List<TreeNode> children = new ArrayList<TreeNode>();

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}
	
	
	
	
}
