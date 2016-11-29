package com.sysongy.util.taglib;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sysongy.util.taglib.cache.Translate;

public class Code2NameTag extends BodyTagSupport {
	
	private static final Log log = LogFactory.getLog(Code2NameTag.class);

	private static final long serialVersionUID = 3984693398166947061L;

	public static final String SYSPARAM = "SYSPARAM";

	public static final String SYSPARAM_EX = "SYSPARAMEX";

	public Code2NameTag() {
		super();
	}

	// Releases any resources we may have (or inherit)
	public void release() {
		super.release();
	}

	private String type;

	private String gcode;

	private String mcode;

	private boolean link;

	public int doStartTag() {
		return SKIP_BODY;
	}

	public int doEndTag() throws JspTagException {
		try {
			if (type == null) {
				type = SYSPARAM;
			}
			if (type != null && type.trim().length() > 0 && mcode != null && mcode.trim().length() > 0) {
				if (SYSPARAM.equals(type)) {
					if (gcode != null && gcode.trim().length() > 0) {
						
						String r = Translate.getInstance().getName(gcode, mcode.trim());
						
						if (link) {
							pageContext.getOut().print(mcode + " - " + r);
						} else {
							pageContext.getOut().print(r);
						}
					}
				} else {
					//String r = Translate.getInstance().getNameNoParam(type,
					//		mcode.trim());
					//从表翻译（非参数）查询，则调用getName3接口
//					String r = Translate.getInstance().getName3(type, mcode.trim());
					if (link) {
//						pageContext.getOut().print(mcode + " - " + r);
					} else {
//						pageContext.getOut().print(r);
					}
				}
			}
		} catch (Exception ex) {
			log.error("", ex);
		}

		return EVAL_PAGE;
	}

	public String getGcode() {
		return gcode;
	}

	public String getMcode() {
		return mcode;
	}

	public String getType() {
		return type;
	}

	public boolean isLink() {
		return link;
	}

	public void setGcode(String gcode) {
		this.gcode = gcode;
	}

	public void setMcode(String mcode) {
		this.mcode = mcode;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setLink(boolean link) {
		this.link = link;
	}

}
