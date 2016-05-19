package com.hbkis.boss.common.dhtml;

import java.util.Collection;
import java.util.Vector;
/**
 * 
 * @FileName     :  XTreeNode.java
 * @Encoding     :  UTF-8
 * @Package      :  com.hbkis.pems.common.dhtml
 * @Link         :  http://www.hbkis.com
 * @Created on   :  2016年1月6日, 上午11:31:45
 * @Author       :  Maloo
 * @Copyright    :  Copyright(c) 2015 西安海贝信息科技有限公司
 * @Description  :
 *
 */
public class XTreeNode {

	Collection children = null;

	
	String text;// label of the node
	String id;// id of the node Optional parameters for this tag are:

	String tooltip;// tooltip for the node
	String im0;// image for node without children (tree will get images from
				// the path specified in setImagePath(url) method)
	String im1;// image for opened node with children
	String im2;// image for closed node with children
	String acolor;// colour of not selected item
	String scolor;// colour of selected item
	boolean select = false;// select node on load (any value)
	boolean open = false;// show node opened (any value)
	boolean call = false;// call function on select(any value)
	boolean checked = false;// check checkbox if exists(any value)
	boolean haschild = true;
	String imheight;// height of the icon
	String imwidth;// width of the icon
	String url ;//节点的路径
	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public XTreeNode() {
		super();
		// TODO Auto-generated constructor stub
	}


	/**
	 * @return Returns the acolor.
	 */
	public String getAcolor() {
		return acolor;
	}
	/**
	 * @param acolor The acolor to set.
	 */
	public void setAcolor(String acolor) {
		this.acolor = acolor;
	}
	/**
	 * @return Returns the call.
	 */
	public boolean isCall() {
		return call;
	}
	/**
	 * @param call The call to set.
	 */
	public void setCall(boolean call) {
		this.call = call;
	}
	/**
	 * @return Returns the checked.
	 */
	public boolean isChecked() {
		return checked;
	}
	/**
	 * @param checked The checked to set.
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	/**
	 * @return Returns the children.
	 */
	public Collection getChildren() {
		return children;
	}
	/**
	 * @param children The children to set.
	 */
	public void setChildren(Collection children) {
		this.children = children;
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
	/**
	 * @return Returns the im0.
	 */
	public String getIm0() {
		return im0;
	}
	/**
	 * @param im0 The im0 to set.
	 */
	public void setIm0(String im0) {
		this.im0 = im0;
	}
	/**
	 * @return Returns the im1.
	 */
	public String getIm1() {
		return im1;
	}
	/**
	 * @param im1 The im1 to set.
	 */
	public void setIm1(String im1) {
		this.im1 = im1;
	}
	/**
	 * @return Returns the im2.
	 */
	public String getIm2() {
		return im2;
	}
	/**
	 * @param im2 The im2 to set.
	 */
	public void setIm2(String im2) {
		this.im2 = im2;
	}
	/**
	 * @return Returns the imheight.
	 */
	public String getImheight() {
		return imheight;
	}
	/**
	 * @param imheight The imheight to set.
	 */
	public void setImheight(String imheight) {
		this.imheight = imheight;
	}
	/**
	 * @return Returns the imwidth.
	 */
	public String getImwidth() {
		return imwidth;
	}
	/**
	 * @param imwidth The imwidth to set.
	 */
	public void setImwidth(String imwidth) {
		this.imwidth = imwidth;
	}
	/**
	 * @return Returns the open.
	 */
	public boolean isOpen() {
		return open;
	}
	/**
	 * @param open The open to set.
	 */
	public void setOpen(boolean open) {
		this.open = open;
	}
	/**
	 * @return Returns the scolor.
	 */
	public String getScolor() {
		return scolor;
	}
	/**
	 * @param scolor The scolor to set.
	 */
	public void setScolor(String scolor) {
		this.scolor = scolor;
	}
	/**
	 * @return Returns the select.
	 */
	public boolean isSelect() {
		return select;
	}
	/**
	 * @param select The select to set.
	 */
	public void setSelect(boolean select) {
		this.select = select;
	}
	/**
	 * @return Returns the text.
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text The text to set.
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * @return Returns the tooltip.
	 */
	public String getTooltip() {
		return tooltip;
	}
	/**
	 * @param tooltip The tooltip to set.
	 */
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}


	/**
	 * @return Returns the haschild.
	 */
	public boolean isHaschild() {
		return haschild;
	}


	/**
	 * @param haschild The haschild to set.
	 */
	public void setHaschild(boolean haschild) {
		this.haschild = haschild;
	}
	
	
	public void addChild(XTreeNode treenode){
		if(children==null){
			children = new Vector();
		}
		children.add(treenode);
	}
}
