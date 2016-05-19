package com.hbkis.boss.common.dhtml;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.hbkis.util.UUIDGenerator;
/**
 * 
 * @FileName     :  XTree.java
 * @Encoding     :  UTF-8
 * @Package      :  com.hbkis.pems.common.dhtml
 * @Link         :  http://www.hbkis.com
 * @Created on   :  2016年1月6日, 上午11:31:37
 * @Author       :  Maloo
 * @Copyright    :  Copyright(c) 2015 西安海贝信息科技有限公司
 * @Description  :
 *
 */
public class XTree {

	Collection treenodes = new Vector();

	String id;
	public XTree() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void addTreeNode(XTreeNode treenode) {
		treenodes.add(treenode);
	}
	public String toXML() {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
		sb.append("<tree id=\"" + UUIDGenerator.quote(id) + "\">");
		Iterator iterator = treenodes.iterator();
		while(iterator.hasNext()){
			XTreeNode treenode = (XTreeNode)iterator.next();
			buildNodeXML(sb,treenode);
		}
		sb.append("</tree>");
		
		return sb.toString();
	}

	public void buildNodeXML(StringBuffer sb,XTreeNode treenode) {
		sb.append("<item text=\"" );
		sb.append(UUIDGenerator.quote(treenode.getText()));
		sb.append("\" id=\"");
		sb.append(UUIDGenerator.quote(treenode.getId()));
		sb.append("\"");
		if(treenode.getTooltip() != null){
			sb.append(" tooltip=\"");
			sb.append(UUIDGenerator.quote(treenode.getTooltip()));
			sb.append("\"");
		}
		if(treenode.getIm0() != null){
			sb.append(" im0=\"");
			sb.append(UUIDGenerator.quote(treenode.getIm0()));
			sb.append("\"");
		}
		if(treenode.getIm1() != null){
			sb.append(" im1=\"");
			sb.append(UUIDGenerator.quote(treenode.getIm1()));
			sb.append("\"");
		}
		if(treenode.getIm2() != null){
			sb.append(" im2=\"");
			sb.append(UUIDGenerator.quote(treenode.getIm2()));
			sb.append("\"");
		}
		if(treenode.getAcolor() != null){
			sb.append(" acolor=\"");
			sb.append(UUIDGenerator.quote(treenode.getAcolor()));
			sb.append("\"");
		}
		if(treenode.getScolor() != null){
			sb.append(" scolor=\"");
			sb.append(UUIDGenerator.quote(treenode.getScolor()));
			sb.append("\"");
		}
		if(treenode.isSelect()){
			sb.append(" select=\"1\"");
		}
		if(treenode.isOpen()){
			sb.append(" open=\"1\"");
		}
		if(treenode.isChecked()){
			sb.append(" checked=\"1\"");
		}
		if(treenode.isCall()){
			sb.append(" call=\"1\"");
		}
		if(treenode.isHaschild()){
			sb.append(" child=\"1\"");
		}
		if(treenode.getImheight() != null){
			sb.append(" imheight=\"");
			sb.append(UUIDGenerator.quote(treenode.getImheight()));
			sb.append("\"");
		}
		if(treenode.getImwidth() != null){
			sb.append(" imwidth=\"");
			sb.append(UUIDGenerator.quote(treenode.getImwidth()));
			sb.append("\"");
		}
		sb.append(">");
		if(treenode.getUrl() != null){
			sb.append("<userdata name=\"url\">");
			sb.append(UUIDGenerator.quote(treenode.getUrl()));
			sb.append("</userdata>");
		}
		if(treenode.isHaschild() && treenode.getChildren() != null){
			Iterator iterator = treenode.getChildren().iterator();
			while(iterator.hasNext()){
				XTreeNode chdnode = (XTreeNode)iterator.next();
				buildNodeXML(sb,chdnode);
			}

		}
		sb.append("</item>");
		
	}

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	
}
