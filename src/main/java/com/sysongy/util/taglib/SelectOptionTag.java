package com.sysongy.util.taglib;

import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.beanutils.PropertyUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sysongy.util.taglib.cache.Translate;
import com.sysongy.util.taglib.cache.UsysparamVO;

public class SelectOptionTag extends BodyTagSupport {
	
	private static final Log log = LogFactory.getLog(SelectOptionTag.class);

	private static final long serialVersionUID = -7054926525472151702L;

	public static final String SYSPARAM = "SYSPARAM";

	public static final String SYSPARAM_EX = "SYSPARAMEX";

	public SelectOptionTag() {
		super();
	}

	// Releases any resources we may have (or inherit)
	public void release() {
		super.release();
	}

	private String form, field;

	private String type;

	private String gcode;

	private String scode;

	private boolean flag;

	private boolean link;

	private boolean all;

	private boolean rule;

	public int doStartTag() {
		return SKIP_BODY;
	}

	public int doEndTag() throws JspTagException {
		try {
			Object bean = null;
			String valuestr = "";
			try {
				bean = pageContext.getRequest().getAttribute(form);
				if (bean != null) {
					valuestr = (String) PropertyUtils.getProperty(bean, field);
				}
			} catch (Exception ex) {

			}
			
			if (type == null) {
				type = SYSPARAM;
			}

			if (type != null && type.trim().length() > 0) {
				List list = null;

				if (SYSPARAM.equals(type)) {
					if (gcode != null && gcode.trim().length() > 0) {
						list = Translate.getInstance().getDataList(gcode);
					}
				} else if (SYSPARAM_EX.equals(type)) {
					if (gcode != null && gcode.trim().length() > 0 && scode != null && scode.trim().length() > 0) {
//						list = Translate.getInstance().getDataList2(gcode, scode);
					}
				} else {
					if (gcode != null && gcode.trim().length() > 0){
//						list = Translate.getInstance().getDataList(type, gcode);
					}
					if (list == null || list.size() == 0){
//						list = Translate.getInstance().getDataListNoParam(type);
					}
				}

				if (flag) {
					pageContext.getOut().print("<option value=''>--请选择--</option>");
				}

				if (all) {
					String isselect = "";
					if (valuestr != null && valuestr.equals("*")) {
						isselect = " selected ";
					}
					pageContext.getOut().print("<option value='*' " + isselect + " >* 所有</option>");
				}

				if (list != null) {
					Iterator iter = list.iterator();
					while (iter.hasNext()) {
						UsysparamVO usysparam = (UsysparamVO) iter.next();
						String sname = "";
						if (link) {
							sname = usysparam.getMcode() + " - " + usysparam.getMname();
						} else {
							sname = usysparam.getMname();
						}

						String isselect = "";
						if (valuestr != null && valuestr.equals(usysparam.getMcode())) {
							isselect = " selected ";
						}
						pageContext.getOut().print("<option value='" + usysparam.getMcode()+ "' " + isselect + " >"+ sname + "</option>");
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

	public boolean isFlag() {

		return flag;
	}

	public String getType() {
		return type;
	}

	public boolean isLink() {
		return link;
	}

	public String getForm() {
		return form;
	}

	public String getField() {
		return field;
	}

	public String getScode() {
		return scode;
	}

	public boolean isAll() {
		return all;
	}

	public void setGcode(String gcode) {
		this.gcode = gcode;
	}

	public void setFlag(boolean flag) {

		this.flag = flag;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setLink(boolean link) {
		this.link = link;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public void setField(String field) {
		this.field = field;
	}

	public void setScode(String scode) {
		this.scode = scode;
	}

	public void setAll(boolean all) {
		this.all = all;
	}

	public boolean isRule() {
		return rule;
	}

	public void setRule(boolean rule) {
		this.rule = rule;
	}

}
