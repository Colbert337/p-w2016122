package com.sysongy.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import jxl.SheetSettings;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


/**
 * 生成excel表格
 * 
 * @author
 * 
 */
public class ExportUtil {
	
	/**
	 * 导出EXCEL 工具类	生成固定格式的excel,表格都为文本,默认第0行水平居中,其余水平居左,默认垂直居中
	 * @param content		二维数组,要生成excel的数据来源
	 * @param sheetName		sheet名称,默认为sheet1
	 * @param mergeInfo		每一项的数据格式为0,1,0,2 即：把(0,1)和(0,2)合并--->第1列的第一、二个元素合并	可填 null
	 * @param os			excel输出流
	 */
	public void exportFormatExcel(String[][] content, String sheetName,String [] mergeInfo,OutputStream os) {
		exportFormatExcel(content, sheetName,mergeInfo,os,null,0);
	}
	/**
	 * 导出EXCEL 工具类	生成固定格式的excel,表格都为文本,默认第0行水平居中,其余水平居左,默认垂直居中
	 * @param content		二维数组,要生成excel的数据来源
	 * @param sheetName		sheet名称,默认为sheet1
	 * @param mergeInfo		每一项的数据格式为0,1,0,2 即：把(0,1)和(0,2)合并--->第1列的第一、二个元素合并	可填 null
	 * @param os			excel输出流
	 * @param wcell			特定列的宽度   格式为  5,10 的数据,表示 第 5 列的宽度设置为 10 	可填 null
	 */
	public void exportFormatExcel(String[][] content, String sheetName,String [] mergeInfo,OutputStream os,String [] wcell) {
		exportFormatExcel(content, sheetName,mergeInfo,os,wcell,0);
	}
	/**
	 * 导出EXCEL 工具类	生成固定格式的excel,表格都为文本,默认第0行水平居中,其余水平居左,默认垂直居中
	 * @param content		二维数组,要生成excel的数据来源
	 * @param sheetName		sheet名称,默认为sheet1
	 * @param mergeInfo		每一项的数据格式为0,1,0,2 即：把(0,1)和(0,2)合并--->第1列的第一、二个元素合并	可填 null
	 * @param os			excel输出流
	 * @param wcell			特定列的宽度   格式为  5,10 的数据,表示 第 5 列的宽度设置为 10 	可填 null
	 * @param defaultweight	最小单元格默认宽度	可填 0
	 */
	public void exportFormatExcel(String[][] content, String sheetName,String [] mergeInfo,OutputStream os,String [] wcell,int defaultweight) {
		exportFormatExcel(content, sheetName,mergeInfo,os,wcell,defaultweight,null,0);
	}
	/**
	 * 导出EXCEL 工具类	生成固定格式的excel,表格都为文本,默认第0行水平居中,其余水平居左,默认垂直居中
	 * @param content		二维数组,要生成excel的数据来源
	 * @param sheetName		sheet名称,默认为sheet1
	 * @param mergeInfo		每一项的数据格式为0,1,0,2 即：把(0,1)和(0,2)合并--->第1列的第一、二个元素合并	可填 null
	 * @param os			excel输出流
	 * @param wcell			特定列的宽度   格式为  5,10 的数据,表示 第 5 列的宽度设置为 10 	可填 null
	 * @param defaultweight	最小单元格默认宽度	可填 0
	 * @param hrow			特定行的行高，格式为  3,400, 表示第 3行的行高设置为 400 	可填 null
	 */
	public void exportFormatExcel(String[][] content, String sheetName,String [] mergeInfo,OutputStream os,String [] wcell,int defaultweight,
			String [] hrow) {
		exportFormatExcel(content, sheetName,mergeInfo,os,wcell,defaultweight,hrow,0);
	}
	
	/**
	 * 导出EXCEL 工具类	生成固定格式的excel,表格都为文本,默认第0行水平居中,其余水平居左,默认垂直居中
	 * @param content		二维数组,要生成excel的数据来源
	 * @param sheetName		sheet名称,默认为sheet1
	 * @param mergeInfo		每一项的数据格式为0,1,0,2 即：把(0,1)和(0,2)合并--->第1列的第一、二个元素合并	可填 null
	 * @param os			excel输出流
	 * @param wcell			特定列的宽度   格式为  5,10 的数据,表示 第 5 列的宽度设置为 10 	可填 null
	 * @param defaultweight	最小单元格默认宽度	可填 0
	 * @param hrow			特定行的行高，格式为  3,400, 表示第 3行的行高设置为 400 	可填 null
	 * @param defaultheight	行的默认高度 	可填 0
	 */
	public void exportFormatExcel(String[][] content, String sheetName,String [] mergeInfo,OutputStream os,String [] wcell,int defaultweight,
			String [] hrow,int defaultheight) {
		exportFormatExcel(content,sheetName,mergeInfo,os,wcell,defaultweight,hrow,defaultheight,null,null);
	}
	
	/**
	 * 导出EXCEL 工具类	生成固定格式的excel,表格都为文本,默认第0行水平居中,其余水平居左,默认垂直居中
	 * @param content		二维数组,要生成excel的数据来源
	 * @param sheetName		sheet名称,默认为sheet1
	 * @param mergeInfo		每一项的数据格式为0,1,0,2 即：把(0,1)和(0,2)合并--->第1列的第一、二个元素合并	可填 null
	 * @param os			excel输出流
	 * @param wcell			特定列的宽度   格式为  5,10 的数据,表示 第 5 列的宽度设置为 10 	可填 null
	 * @param defaultweight	最小单元格默认宽度	可填 0
	 * @param hrow			特定行的行高，格式为  3,400, 表示第 3行的行高设置为 400 	可填 null
	 * @param defaultheight	行的默认高度 	可填 0
	 * @param font			字号设置，数组，String [] font = new String []{"0,16","2",11};	可填 null
	 */
	public void exportFormatExcel(String[][] content, String sheetName,String [] mergeInfo,OutputStream os,String [] wcell,int defaultweight,
			String [] hrow,int defaultheight,String [] font) {
		exportFormatExcel(content,sheetName,mergeInfo,os,wcell,defaultweight,hrow,defaultheight,font,null);
	}
	
	/**
	 * 导出EXCEL 工具类	生成固定格式的excel,表格都为文本,默认第0行水平居中,其余水平居左,默认垂直居中
	 * @param content		二维数组,要生成excel的数据来源
	 * @param sheetName		sheet名称,默认为sheet1
	 * @param mergeInfo		每一项的数据格式为0,1,0,2 即：把(0,1)和(0,2)合并--->第1列的第一、二个元素合并	可填 null
	 * @param os			excel输出流
	 * @param wcell			特定列的宽度   格式为  5,10 的数据,表示 第 5 列的宽度设置为 10 	可填 null
	 * @param defaultweight	最小单元格默认宽度	可填 0
	 * @param hrow			特定行的行高，格式为  3,400, 表示第 3行的行高设置为 400 	可填 null
	 * @param defaultheight	行的默认高度 	可填 0
	 * @param font			字号设置，数组，String [] font = new String []{"0,16","2",11};	可填 null
	 * @param align			对齐设置，数组，String [] align = new String []{"0,center","1,left","2,right"};	可填 null
	 */
	public void exportFormatExcel(String[][] content, String sheetName,String [] mergeInfo,OutputStream os,String [] wcell,int defaultweight,
			String [] hrow,int defaultheight,String [] font,String [] align) {
		exportFormatExcel(content,sheetName,mergeInfo,os,wcell,defaultweight,hrow,defaultheight,font,align,true);
	}
	
	
	
	/**
	 * 导出EXCEL 工具类	生成固定格式的excel,表格都为文本,默认第0行水平居中,其余水平居左,默认垂直居中
	 * @param content		二维数组,要生成excel的数据来源
	 * @param sheetName		sheet名称,默认为sheet1
	 * @param mergeInfo		每一项的数据格式为0,1,0,2 即：把(0,1)和(0,2)合并--->第1列的第一、二个元素合并	可填 null
	 * @param os			excel输出流
	 * @param wcell			特定列的宽度   格式为  5,10 的数据,表示 第 5 列的宽度设置为 10 	可填 null
	 * @param defaultweight	最小单元格默认宽度	可填 0
	 * @param hrow			特定行的行高，格式为  3,400, 表示第 3行的行高设置为 400 	可填 null
	 * @param defaultheight	行的默认高度 	可填 0
	 * @param font			字号设置，数组，String [] font = new String []{"0,16","2",11};	可填 null
	 * @param align			对齐设置，数组，String [] align = new String []{"0,center","1,left","2,right"};	可填 null
	 * @param readonly		文档是否只读，默认为只读 true；
	 */
	public void exportFormatExcel(String[][] content, String sheetName,String [] mergeInfo,OutputStream os,String [] wcell,int defaultweight,
			String [] hrow,int defaultheight,String [] font,String [] align,boolean readonly) {
		if (VerifyUtil.isNullObject(content, os)|| VerifyUtil.isNull2DArray(content)) {
			return;
		}
		// 默认名称
		if (VerifyUtil.isNullObject(sheetName)) {
			sheetName = "sheet1";
		}
		if(defaultweight == 0){
			defaultweight = 10 ;
		}
		
		WritableWorkbook workbook = null;
		try {
			workbook = Workbook.createWorkbook(os);
			//workbook.setProtected(true); //只能保护当前文件,不能起到本质保护作用，用户可以修改另存为生成新的文件
			WritableSheet sheet = workbook.createSheet(sheetName, 0);
			if(readonly){
				sheet.getSettings().setProtected(true); //不可编辑，重新复制文件同样不可编辑
				sheet.getSettings().setPassword("hbkis");;
			}
			
			if(mergeInfo != null){
				for (int i = 0; i < mergeInfo.length; i++) {
					int m = 0,n = 0,p = 0,k = 0 ;
					m = Integer.parseInt(mergeInfo[i].split(",")[0]);
					n = Integer.parseInt(mergeInfo[i].split(",")[1]);
					p = Integer.parseInt(mergeInfo[i].split(",")[2]);
					k = Integer.parseInt(mergeInfo[i].split(",")[3]);
					sheet.mergeCells(m,n,p,k); 
					/*	sheet.mergeCells(m,n,p,k); 
					 * 	合并 第M列第N行  ---到  第P列第K行
					 * 	例如：合并第一列第一行到第六列第一行的所有单元格 
					 * 	sheet.mergeCells( 0 , 0 , 5 , 0 );
					 * */
				}
			}
			
			for (int i = 0; i < content.length; i++) {
				for (int j = 0; j < content[i].length; j++) {
					/*字号设置 ，设置字号的默认为加粗*/
					WritableFont tempfont = null ;
					if(font != null){
						for (int k = 0; k < font.length; k++) {
							if(i == Integer.parseInt(font[k].split(",")[0])){
								tempfont = new WritableFont(WritableFont.TIMES, Integer.parseInt(font[k].split(",")[1]) ,WritableFont.BOLD);
							}
						}
					}else{
						/*未设置字号的，是否设置第 0 行的默认字号*/
						//是否设置默认第0行字号 ？？？
					}
					
					WritableCellFormat format = new WritableCellFormat();
					if(font != null){
						if(tempfont != null){
							format = new WritableCellFormat(tempfont);
						}else{
							format = new WritableCellFormat();
						}
						
					}else{
						format = new WritableCellFormat();
					}
					
					/*为0 的字符串不显示*/
					if (content[i][j] == null ) {
						content[i][j] = "";
					}
					if("0".equals(content[i][j]) || "0.0".equals(content[i][j]) || "0.00".equals(content[i][j]) || "0.000".equals(content[i][j])){
						content[i][j] = "";
					}
					
					format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);//设置单元格边框
					format.setWrap(true);//单元格内自动换行
					//format.setAlignment(jxl.format.Alignment.LEFT); 					//设置文字做对齐
					//format.setAlignment(Alignment.CENTRE);							//把水平对齐方式指定为居中 
					/*对齐设置*/
					if(align != null){
						for (int k = 0; k < align.length; k++) {
							if(i == Integer.parseInt(align[k].split(",")[0])){
								if("left".equals(align[k].split(",")[1])){
									format.setAlignment(jxl.format.Alignment.LEFT); 	//靠左
								}else if("center".equals(align[k].split(",")[1])){
									format.setAlignment(jxl.format.Alignment.CENTRE); 	//居中
								}else if("right".equals(align[k].split(",")[1])){
									format.setAlignment(jxl.format.Alignment.RIGHT); 	//靠右
								}
							}
						}
					}else{
						if(i == 0){
							format.setAlignment(jxl.format.Alignment.CENTRE); 	//居中,未设置对齐方式的，第0行默认居中
						}else{
							format.setAlignment(jxl.format.Alignment.LEFT); 	//靠左,未设置对齐方式的，其他行默认居左
						}
					}
					
					format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); 	//垂直居中
					Label label = new Label(j, i, content[i][j], format);
					
					//有宽度参数的处理
					if(wcell != null){
						boolean ishave = false ;
						for (int k = 0; k < wcell.length; k++) {
							if(j == Integer.parseInt(wcell[k].split(",")[0])){
								sheet.setColumnView(j, Integer.parseInt(wcell[k].split(",")[1])); //设置第J列的列宽度
								ishave = true ;
								break;
							}
						}
						if(!ishave){
							sheet.setColumnView(j, defaultweight); //设置默认宽度为 8
						}
					}else{//设置默认宽度
						sheet.setColumnView(j, defaultweight); //设置默认宽度为 8
					}
					
					sheet.addCell(label);
					
					if(hrow != null){
						boolean rhave = false ;
						for (int mm = 0; mm < hrow.length; mm++) {
							if(i == Integer.parseInt(hrow[mm].split(",")[0])){
								sheet.setRowView(i, Integer.parseInt(hrow[mm].split(",")[1])); //设置属性行高
								rhave = true; 
								break ;
							}
						}
						if(!rhave){
							//不设置则为自适应行高
							if(defaultheight != 0 ){
								sheet.setRowView(i, defaultheight); //设置行高
							}
						}
					}else{
						//不设置则为自适应行高
						if(defaultheight != 0 ){
							sheet.setRowView(i, defaultheight); //设置行高
						}
					}
					
				}
			}
			workbook.write();
		} catch (Exception e) {
		} finally {
			try {
				workbook.close();
				os.close();
			} catch (WriteException e) {
			} catch (IOException e) {
			}
		}
	}
	

	/**
	 * 合并表格
	 * 
	 * @param sheet
	 *            工作表
	 * @param mergeInfo
	 *            要合并的表格的信息
	 * @throws RowsExceededException
	 * @throws NumberFormatException
	 * @throws WriteException
	 */
	private void merge(WritableSheet sheet, String[] mergeInfo)
			throws RowsExceededException, NumberFormatException, WriteException {
		if (VerifyUtil.isNullObject(sheet)
				|| VerifyUtil.isNull1DArray(mergeInfo)) {
			return;
		} else if (!this.isMergeInfo(mergeInfo)) {
			return;
		} else {
			for (String str : mergeInfo) {
				String[] temp = str.split(",");
				sheet.mergeCells(Integer.parseInt(temp[1]),
						Integer.parseInt(temp[0]), Integer.parseInt(temp[3]),
						Integer.parseInt(temp[2]));
			}
		}
	}

	/**
	 * 处理要居中的行或列的数据
	 * 
	 * @param indexes
	 *            行标或列标
	 * @return 行坐标或列坐标组成的集合
	 */
	private Set<Integer> getInfo(String indexes) {
		Set<Integer> set = new HashSet<Integer>();
		if (VerifyUtil.isNullObject(indexes)) {
			return set;
		}
		String[] temp = indexes.split(",", 0);
		for (String str : temp) {
			if (isNumeric(str)) {
				set.add(Integer.parseInt(str));
			}
		}
		return set;
	}

	/**
	 * 判断字符串是否由纯数字组成
	 * 
	 * @param str
	 *            源字符串
	 * @return true是，false否
	 */
	private boolean isNumeric(String str) {
		if (VerifyUtil.isNullObject(str)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[-+]?[0-9]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * 判断字符串是否是数字
	 * 
	 * @param str
	 *            源字符串
	 * @return true是，false否
	 */
	private boolean isNumber(String number) {
		// 判断参数
		if (VerifyUtil.isNullObject(number)) {
			return false;
		}
		// 查看是否有小数点
		int index = number.indexOf(".");
		if (index < 0) {
			return isNumeric(number);
		} else {
			// 如果有多个".",则不是数字
			if (number.indexOf(".") != number.lastIndexOf(".")) {
				return false;
			}
			String num1 = number.substring(0, index);
			String num2 = number.substring(index + 1);
			return isNumeric(num1) && isNumeric(num2);
		}
	}

	/**
	 * 判断合并项内容是否合法
	 * 
	 * @param mergeInfo
	 *            合并项 每一项的数据格式为0,1,0,2即把(0,1)和(0,2)合并
	 * @return true合法,false非法
	 */
	private boolean isMergeInfo(String[] mergeInfo) {
		if (VerifyUtil.isNull1DArray(mergeInfo)) {
			return false;
		} else {
			for (String str : mergeInfo) {
				String[] temp = str.split(",");
				if (VerifyUtil.isNull1DArray(temp) || temp.length != 4) {
					return false;
				} else {
					for (String s : temp) {
						if (!isNumeric(s)) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	/**
	 * 生成固定格式的excel,表格都为文本,水平居左,垂直居中
	 * @param sheetName		sheet名称,默认为sheet1
	 * @param content		二维数组,要生成excel的数据来源
	 * @param os			excel输出流
	 * @param mergeInfo		每一项的数据格式为0,1,0,2 即：把(0,1)和(0,2)合并--->第1列的第一、二个元素合并
	 * @param wcell 		特定列的宽度   格式为  5,10 的数据,表示 第 5 列的宽度设置为 10 
	 * @param defaultweight 最小单元格默认宽度
	 * @param hrow			特定行的行高，格式为  3,400, 表示第 3行的行高设置为 400 
	 * @param defaultheight	行的默认高度 
	 */
	public void OLDexportFormatExcel(String[][] content, String sheetName,String [] mergeInfo,OutputStream os,
			String [] wcell,int defaultweight,String [] hrow,int defaultheight) {
		if (VerifyUtil.isNullObject(content, os)|| VerifyUtil.isNull2DArray(content)) {
			return;
		}
		// 默认名称
		if (VerifyUtil.isNullObject(sheetName)) {
			sheetName = "sheet1";
		}
		if(defaultweight == 0){
			defaultweight = 10 ;
		}
		
		WritableWorkbook workbook = null;
		try {
			workbook = Workbook.createWorkbook(os);
			WritableSheet sheet = workbook.createSheet(sheetName, 0);
			for (int i = 0; i < mergeInfo.length; i++) {
				int m = 0,n = 0,p = 0,k = 0 ;
				m = Integer.parseInt(mergeInfo[i].split(",")[0]);
				n = Integer.parseInt(mergeInfo[i].split(",")[1]);
				p = Integer.parseInt(mergeInfo[i].split(",")[2]);
				k = Integer.parseInt(mergeInfo[i].split(",")[3]);
				sheet.mergeCells(m,n,p,k); 
				/*	sheet.mergeCells(m,n,p,k); 
				 * 	合并 第M列第N行  ---到  第P列第K行
				 * 	例如：合并第一列第一行到第六列第一行的所有单元格 
				 * 	sheet.mergeCells( 0 , 0 , 5 , 0 );
				 * */
			}
			
			
			for (int i = 0; i < content.length; i++) {
				for (int j = 0; j < content[i].length; j++) {
					if(i == 0){//判断是否为标题
						WritableFont font1 = new WritableFont(WritableFont.TIMES, 16 ,WritableFont.BOLD);
						WritableCellFormat format1 = new  WritableCellFormat(font1);
						format1.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);//设置单元格边框
						format1.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); //垂直居中
						format1.setAlignment(jxl.format.Alignment.CENTRE);//水平居中
						Label label = new  Label(j, i,content[i][j],format1);
						sheet.addCell(label);
					}else if(i == 1){//判断是否为表头  update by wdq 2015-09-08 
						WritableFont font1 = new WritableFont(WritableFont.TIMES, 11 ,WritableFont.BOLD);
						WritableCellFormat format1 = new  WritableCellFormat(font1);
						format1.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);//设置单元格边框
						format1.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//垂直居中
						format1.setAlignment(jxl.format.Alignment.CENTRE);//水平居中
						Label label = new  Label(j, i,content[i][j],format1);
						sheet.addCell(label);
					}else{
						WritableCellFormat format = new WritableCellFormat();
						if (content[i][j] == null ) {
							content[i][j] = "";
						}
						if("0".equals(content[i][j]) || "0.0".equals(content[i][j]) || "0.00".equals(content[i][j]) || "0.000".equals(content[i][j])){
							content[i][j] = "";
						}
						
						format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);//设置单元格边框
						format.setWrap(true);//单元格内自动换行
						format.setAlignment(jxl.format.Alignment.LEFT); 					//设置文字做对齐
						//format.setAlignment(Alignment.CENTRE);							//把水平对齐方式指定为居中 
						format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); 	//垂直居中
						Label label = new Label(j, i, content[i][j], format);
						
						//有宽度参数的处理
						if(wcell != null){
							boolean ishave = false ;
							for (int k = 0; k < wcell.length; k++) {
								if(j == Integer.parseInt(wcell[k].split(",")[0])){
									sheet.setColumnView(j, Integer.parseInt(wcell[k].split(",")[1])); //设置第J列的列宽度
									ishave = true ;
									break;
								}
							}
							if(!ishave){
								sheet.setColumnView(j, defaultweight); //设置默认宽度为 8
							}
						}else{//设置默认宽度
							sheet.setColumnView(j, defaultweight); //设置默认宽度为 8
						}
						
						sheet.addCell(label);
					}
					
					if(hrow != null){
						boolean rhave = false ;
						for (int mm = 0; mm < hrow.length; mm++) {
							if(i == Integer.parseInt(hrow[mm].split(",")[0])){
								sheet.setRowView(i, Integer.parseInt(hrow[mm].split(",")[1])); //设置属性行高
								rhave = true; 
								break ;
							}
						}
						if(!rhave){
							//不设置则为自适应行高
							if(defaultheight != 0 ){
								sheet.setRowView(i, defaultheight); //设置行高
							}
						}
					}else{
						//不设置则为自适应行高
						if(defaultheight != 0 ){
							sheet.setRowView(i, defaultheight); //设置行高
						}
					}
					
				}
			}
			workbook.write();
		} catch (Exception e) {
		} finally {
			try {
				workbook.close();
				os.close();
			} catch (WriteException e) {
			} catch (IOException e) {
			}
		}
	}
	
	
	/**
	 * 生成具有一定格式excel
	 * @param sheetName	sheet名称,默认为sheet1
	 * @param nf		数字类型的格式 如:jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#.##");默认无格式
	 * @param content	二维数组,要生成excel的数据来源
	 * @param 合并项		每一项的数据格式为0,1,0,2 即：把(0,1)和(0,2)合并--->第1列的第一、二个元素合并
	 * @param os		excel输出流
	 * @param row		需要水平居中的行,默认居左。以逗号分隔的字符串
	 * @param col		需要水平居中的列,默认居左。以逗号分隔的字符串
	 */
	public void export(String sheetName, jxl.write.NumberFormat nf, String[][] content,
			String[] mergeInfo, OutputStream os, String row, String col) {

		if (VerifyUtil.isNullObject(content, os)
				|| VerifyUtil.isNull2DArray(content)) {
			return;
		}
		// 默认名称
		if (VerifyUtil.isNullObject(sheetName)) {
			sheetName = "sheet1";
		}
		Set<Integer> rows = this.getInfo(row);
		Set<Integer> cols = this.getInfo(col);
		WritableWorkbook workbook = null;
		try {
			workbook = Workbook.createWorkbook(os);
			WritableSheet sheet = workbook.createSheet(sheetName, 0);
			for (int i = 0; i < content.length; i++) {
				for (int j = 0; j < content[i].length; j++) {
					if (content[i][j] == null) {
						content[i][j] = "";
					}
					if (isNumber(content[i][j]) && !rows.contains(i)
							&& !cols.contains(j)) {// 处理数字
						Number number = null;
						if (VerifyUtil.isNullObject(nf)) {// 数字无格式
							number = new Number(j, i,Double.valueOf(content[i][j]));
						} else {// 如果有格式,按格式生成
							jxl.write.WritableCellFormat wcfn = new jxl.write.WritableCellFormat(nf);
							number = new Number(j, i,Double.valueOf(content[i][j]), wcfn);
						}
						sheet.addCell(number);
					} else {// 处理非数字
						WritableCellFormat format = new WritableCellFormat();
						if (rows.contains(i) || cols.contains(j)) {
							format.setAlignment(jxl.format.Alignment.CENTRE);
						} else {
							format.setAlignment(jxl.format.Alignment.LEFT);
						}
						format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
						Label label = new Label(j, i, content[i][j], format);
						sheet.addCell(label);
					}
				}
			}
			this.merge(sheet, mergeInfo);
			workbook.write();
		} catch (Exception e) {
		} finally {
			try {
				workbook.close();
				os.close();
			} catch (WriteException e) {
			} catch (IOException e) {
			}
		}
	}
	
	
	
//	public static void main(String[] args) {
//		ExportUtil ee = new ExportUtil();
//		String[][] content = new String[][] { { "", "第一列", null, "第三列" },
//				{ "第一行", "aa", "2.00", "22" }, { "第二行", "bb", "3.01", "32" },
//				{ "第三行", "cc", "4.00", "41" } };
//		try {
//			OutputStream os = new FileOutputStream("D:/test2.xls");
//			ee.export(null, null, content,
//					new String[] { "0,1,0,2", "1,0,3,0" }, os, "0,1", "0");
//		} catch (Exception e) {
//		}
//	}
}
