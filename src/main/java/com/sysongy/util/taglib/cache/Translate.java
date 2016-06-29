package com.sysongy.util.taglib.cache;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.github.abel533.sql.SqlMapper;
/**
 * 
 * @author user
 *
 */
public class Translate {
	
	public static final String SYSPARAM = "SYSPARAM";

	public static final String SYSPARAM_EX = "SYSPARAMEX";

	private static final String SEP = ",";

	private static Translate me = new Translate();
	
	private Translate() {
	}

	public static Translate getInstance() {
		return me;
	}

	/**
	 * 根据gcode和mcode查询得mname，并返回mname
	 * @param gcode
	 * 				主参数
	 * @param mcode
	 * 				子参数
	 * @return
	 * 				名称
	 */
	public String getName(String gcode, String mcode) {
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		SqlSessionFactory sessionFactory = (SqlSessionFactory) wac.getBean("sqlSessionFactory");
		SqlSession session = sessionFactory.openSession();
		SqlMapper sqlMapper = new SqlMapper(session);
		String excuteSQL = "SELECT MNAME FROM USYSPARAM WHERE GCODE ='"+gcode+"' AND MCODE ='"+mcode+"'";
		UsysparamVO vo = sqlMapper.selectOne(excuteSQL, UsysparamVO.class);
		session.close();
		
		return vo == null ?"":vo.getMname();
	}
	
//	
//	/**
//	 * 根据gcode和mcode查询得mname，并返回mname,
//	 * 此方法与getName()的区别是找不到记录的时候不返回值.
//	 * @param gcode
//	 * 				主参数
//	 * @param mcode
//	 * 				子参数
//	 * @return
//	 * 				名称
//	 */
//	public String getNameEx(String gcode, String mcode) {
//		return StaticData.getInstance().getNameEx(gcode, mcode);
//	}
//	/**
//	 * 根据gcode,scode和mcode查询得mname，并返回mname
//	 * @deprecated
//	 * @param gcode
//	 * 				主参数
//	 * @param scode
//	 * 				父参数
//	 * @param mcode
//	 * 				子参数
//	 * @return
//	 * 				名称
//	 */
//	public String getName2(String gcode, String scode, String mcode) {
//		return StaticData.getInstance().getName(gcode, scode, mcode);
//	}
//
//	/**
//	 * 根据mcode查询mname，没有主参数
//	 * @param sql_id
//	 * 				sql语句的id
//	 * @param mcode
//	 * 				子参数
//	 * @return
//	 * 				名称
//	 */
//	public String getNameNoParam(String sql_id, String mcode) {
//		return StaticData.getInstance().getNameNoParam(sql_id, mcode);
//	}
//	
//	/**
//	 * 根据value用指定sql语句查询到对应的name，并返回name
//	 * @param sql_id
//	 * 				sql语句的id
//	 * @param value
//	 * 				查询线索
//	 * @return
//	 * 				结果字符串
//	 */
//	public String getName3(String sql_id, String value){
//		return StaticData.getInstance().getName3(sql_id, value);
//	}
//
//	/**
//	 * 根据gcode,scode和mcode用指定sql语句查询得mname，并返回mname
//	 * @param sql_id
//	 * 				sql语句的id
//	 * @param gcode
//	 * 				主参数
//	 * @param scode
//	 * 				父参数
//	 * @param mcode
//	 * 				子参数
//	 * @return
//	 * 				名称
//	 */
//	public String getName2(String sql_id, String gcode, String scode,
//			String mcode) {
//		return StaticData.getInstance().getName2(sql_id, gcode, scode, mcode);
//	}

	/**
	 * 查询主参数为gcode的所有mcode和mname，用List返回
	 * @param gcode
	 * 				主参数
	 * @return
	 * 				包含查询到的mcode和mname的List
	 */
	public List getDataList(String gcode) {
		
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		SqlSessionFactory sessionFactory = (SqlSessionFactory) wac.getBean("sqlSessionFactory");
		SqlSession session = sessionFactory.openSession();
		SqlMapper sqlMapper = new SqlMapper(session);
		String excuteSQL = "SELECT * FROM USYSPARAM WHERE GCODE ='"+gcode+"' AND SCODE is NULL";
		List<UsysparamVO> list = sqlMapper.selectList(excuteSQL, UsysparamVO.class);
		
		session.close();
		
		return list;
	}

	/**
	 * 查询主参数为gcode，父参数为scode的所有mcode和mname，用List返回
	 * @param gcode
	 * 				主参数
	 * @param scode
	 * 				父参数
	 * @return
	 * 				包含查询到的mcode和mname的List
	 */
//	public List getDataList2(String gcode, String scode) {
//		return StaticData.getInstance().getStaticData2(gcode, scode);
//	}
//
//	/**
//	 * 用指定的sql语句查询所有mcode和mname，用List返回
//	 * @param sql_id
//	 * 				sql语句的id
//	 * @return
//	 * 				包含查询到的mcode和mname的List
//	 */
//	public List getDataListNoParam(String sql_id) {
//		return StaticData.getInstance().getStaticDataNoParam(sql_id);
//	}
//
//	/**
//	 * 用指定的sql语句查询主参数为gcode的所有mcode和mname，用List返回
//	 * @param sql_id
//	 * 				sql语句的id
//	 * @param gcode
//	 * 				主参数
//	 * @return
//	 * 				包含查询到的mcode和mname的List
//	 */
//	public List getDataList(String sql_id, String gcode) {
//		return StaticData.getInstance().getStaticData(sql_id, gcode);
//	}
//	
//	/**
//	 * 用指定的sql语句查询主参数为gcodet和mcode的所有mcode和mname，data,用List返回
//	 * @param sql_id
//	 * 				sql语句的id
//	 * @param gcode
//	 * 				主参数
//	 * @return
//	 * 				包含查询到的mcode和mname,data的List
//	 */
//	public List getDataList(String sql_id, String gcode,String mcode) {
//		return StaticData.getInstance().getStaticData2(sql_id, gcode,mcode);
//	}
//	
//	/**
//	 * 用指定的sql语句查询主参数为gcode，父参数为scode的所有mcode和mname，用List返回
//	 * @param sql_id
//	 * 				sql语句的id
//	 * @param gcode
//	 * 				主参数
//	 * @param scode
//	 * 				父参数
//	 * @return
//	 * 				包含查询到的mcode和mname的List
//	 */
//	public List getDataList2(String sql_id, String gcode, String scode) {
//		return StaticData.getInstance().getStaticData2(sql_id, gcode, scode);
//	}
//
//	/**
//	 * 清空dataCache，checkCache，dataCache，checkCache
//	 *
//	 */
//	public void clearCache() {
//		StaticData.getInstance().clearCache();
//	}

	/**
	 * 将多个mcode进行翻译，返回多个mname
	 * @param type
	 * 				指定的sql语句id，若为空，则默认为SYSPARAM
	 * @param gcode
	 * 				主参数
	 * @param mcode
	 * 				多个子参数组成的串,用指定分隔符seperator隔开
	 * @param seperator
	 * 				分隔符，若为空，则默认为~
	 * @return
	 * 				返回多个名称组成的串，各个名称用","连接
	 */
//	public String getMutilCode2Name(String type, String gcode, String mcode,
//			String seperator) {
//		try {
//			if (type == null) {
//				type = SYSPARAM;
//			}
//
//			if (seperator == null || seperator.trim().length() == 0) {
//				seperator = "~";
//			}
//			String result = "";
//			String[] value = StringUtils.split(mcode, seperator);
//			for (int i = 0; i < value.length; i++) {
//				value[i] = value[i].trim();
//				if (type != null && type.trim().length() > 0
//						&& value[i] != null && value[i].trim().length() > 0) {
//					if (SYSPARAM.equals(type)) {
//						if (gcode != null && gcode.trim().length() > 0) {
//							String r = getName(gcode, value[i]);
//							result += r + SEP;
//						}
//					} else {
//						String r = getName3(type, value[i]); //从表里翻译名称
//						result += r + SEP;
//					}
//				}
//			}
//			if (result.endsWith(SEP)) {
//				result = result.substring(0, result.length() - 1);
//			}
//			return result;
//		} catch (Exception ex) {
//			return mcode;
//		}
//	}

}
