package com.hbkis.util;

import java.io.OutputStream;
import java.util.Map;

import jxl.write.NumberFormat;
import jxl.write.WritableSheet;

public class VerifyUtil {
	public static boolean isNullObject(String[][] content, OutputStream os) {
		if (content != null && content.length > 0 && os != null) {
			return false;
		}
		return true;
	}

	public static boolean isNull2DArray(String[][] content) {
		if (content != null && content.length > 0) {
			return false;
		}
		return true;
	}

	public static boolean isNullObject(NumberFormat nf) {
		if (nf != null) {
			return false;
		}
		return true;
	}

	public static boolean isNullObject(String sheetName) {
		if (sheetName != null && !"".equals(sheetName.trim())) {
			return false;
		}
		return true;
	}

	public static boolean isNullObject(Map<String, String[][]> content,
			OutputStream os) {
		if (content != null && content.size() > 0 && os != null) {
			return false;
		}
		return true;
	}

	public static boolean isNull1DArray(String[] mergeInfo) {
		if (mergeInfo != null && mergeInfo.length > 0) {
			return false;
		}
		return true;
	}

	public static boolean isNullObject(WritableSheet sheet) {
		if (sheet != null) {
			return false;
		}
		return true;
	}

}
