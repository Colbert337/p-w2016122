package com.sysongy.util.taglib;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.text.DecimalFormat;

/**
 * 用于金钱显示的标签
 * 用法：如：
 * <s:MoneyFormat value="${vo.payfee}" precision="2"/>
 * precision表示小数位取几位
 * @author addi.li
 *
 */
public class MoneyFormatTag extends BodyTagSupport {
	private static final Log log = LogFactory.getLog(MoneyFormatTag.class);

	private static final long serialVersionUID = 1L;

	public static final String SYSPARAM = "SYSPARAM";

	public static final String SYSPARAM_EX = "SYSPARAMEX";

	public MoneyFormatTag() {
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
	
	private String value;
	
	private String precision;//精度，小数位取几位

	public int doStartTag() {
		return SKIP_BODY;
	}

	public int doEndTag() throws JspTagException {
		try {
			if (type == null) {
				type = SYSPARAM;
			}
			if (type != null && type.trim().length() > 0) {
				if (SYSPARAM.equals(type)) {
					if (value != null && value.trim().length() > 0 && precision != null && precision.trim().length() >0 && precision.indexOf(".") == -1) {
						StringBuffer format = new StringBuffer("");
						int intPrecision = Integer.parseInt(precision);
						//因为数据库为分，而页面显示则需要是元，所以要除以100
						double doubleValue = Double.parseDouble(value) / 100;
						if(1 > doubleValue) {
							if(intPrecision == 0) {
								format.append("0"); 
							}else {
								format.append("0.");
								for(int i = 1; i <= intPrecision; i++ ) {
									format.append("0");
								}
							}
						}else {
							if(intPrecision == 0) {
								format.append("###"); 
							}else {
								format.append("###.");
								for(int i = 1; i <= intPrecision; i++ ) {
									format.append("0");
								}
							}
						}
						String stringFormat = format.toString();
						String r = new DecimalFormat(stringFormat).format(doubleValue);
						if (link) {
							pageContext.getOut().print(mcode + " - " + r);
						} else {
							pageContext.getOut().print(r);
						}
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

	public String getPrecision() {
		return precision;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
