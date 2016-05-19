package com.hbkis.util;

import java.io.File;  
import java.io.FileOutputStream;  
import java.io.OutputStream;  
import java.util.ArrayList;  
import java.util.Date;  
import java.util.List;  
  
import jxl.Cell;  
import jxl.CellType;  
import jxl.Sheet;  
import jxl.Workbook;  
import jxl.WorkbookSettings;  
import jxl.format.Alignment;  
import jxl.format.Border;  
import jxl.format.BorderLineStyle;  
import jxl.format.CellFormat;  
import jxl.format.Colour;  
import jxl.format.VerticalAlignment;  
import jxl.write.Formula;  
import jxl.write.Label;  
import jxl.write.NumberFormat;  
import jxl.write.WritableCell;  
import jxl.write.WritableCellFeatures;  
import jxl.write.WritableCellFormat;  
import jxl.write.WritableFont;  
import jxl.write.WritableImage;  
import jxl.write.WritableSheet;  
import jxl.write.WritableWorkbook;  
import jxl.write.WriteException;  
import jxl.write.biff.RowsExceededException; 

/**
 * @FileName     :  JExcelUtils.java
 * @Encoding     :  UTF-8
 * @Package      :  com.hbkis.pems.util
 * @Link         :  http://www.hbkis.com
 * @Created on   :  2015年12月2日, 下午2:52:06
 * @Author       :  Dongqiang.Wang [wdq_2012@126.com]
 * @Copyright    :  Copyright(c) 2015 西安海贝信息科技有限公司
 * @Description  :
 *
 */
public class JExcelUtils {
	  
    private static final int TITLE_LENGTH = 2;  
  
    // private static final int SHEET_WIDTH = 6;  
  
    /** 
     * 生成Excel文件 
     *  
     * @param path 
     *            文件路径 
     * @param sheetName 
     *            工作表名称 
     * @param dataTitles 
     *            数据标题 
     */  
    public void createExcelFile(String path, String sheetName, String[] dataTitles, String titleName, String logoPath) {  
        WritableWorkbook workbook;  
        Label label;  
        try {  
  
            OutputStream os = new FileOutputStream(path);  
            workbook = Workbook.createWorkbook(os);  
  
            WritableSheet sheet = workbook.createSheet(sheetName, 0); // 添加第一个工作表  
            initialSheetSetting(sheet);  
  
              
            /* 
             * 报表主标题    合并单元格 通过writablesheet.mergeCells(int x,int y,int m,int n);来实现的 
             * 表示将从第x+1列，y+1行到m+1列，n+1行合并 
             */  
            sheet.mergeCells(0, 0, dataTitles.length - 1, 0);  
            label = new Label(0, 0, titleName, getDataCellFormat(CellType.STRING_FORMULA, "宋体", 20, Colour.BLUE, Colour.YELLOW));  
            sheet.addCell(label);  
              
            if (logoPath != null) {  
                insertImgCell(sheet, 0, 0, 1, 1, logoPath);  
            }  
            for (int i = 0; i < dataTitles.length; i++) {  
                label = new Label(i, 1, dataTitles[i], getTitleCellFormat());  
                // Label(列号,行号,内容,风格)  
                sheet.addCell(label);  
            }  
            List<Person> data = getData();  
  
            for (int i = 0; i < data.size(); i++) {  
                Person person = data.get(i);  
                insertOneCellData(sheet, 0, i + TITLE_LENGTH, person.getId(), getDataCellFormat(CellType.STRING_FORMULA));  
                // 注释 start  
                label = new Label(1, i + TITLE_LENGTH, person.getName(), getDataCellFormat(CellType.STRING_FORMULA));  
                sheet.addCell(label);  
                if ("wangyu".equals(person.getName())) {  
                    setCellComments(label, "这是个说明！");  
                }  
                // 注释 end  
                if (person.getSex() >= 20) {  
                    insertOneCellData(sheet, 2, i + TITLE_LENGTH, person.getSex(), getDataCellFormat(CellType.NUMBER, "宋体", 10,  
                            Colour.YELLOW, Colour.YELLOW));  
                } else {  
                    insertOneCellData(sheet, 2, i + TITLE_LENGTH, person.getSex(), getDataCellFormat(CellType.NUMBER));  
                }  
                insertOneCellData(sheet, 3, i + TITLE_LENGTH, person.getBirthday(), getDataCellFormat(CellType.DATE));  
                // insertFormula(sheet, 5, 1, "C2+D2+E2",  
                // getDataCellFormat(CellType.NUMBER_FORMULA));//SUM(C3:E3)  
            }  
  
            for (int i = 2; i < dataTitles.length; i++) {  
                insertFormulaCell(sheet, i, data.size() + TITLE_LENGTH, 2, data.size() + TITLE_LENGTH,  
                        getDataCellFormat(CellType.NUMBER_FORMULA));  
            }  
            insertFormula(sheet, 4, data.size() + TITLE_LENGTH, "C6+D6", getDataCellFormat(CellType.NUMBER_FORMULA));// SUM(C3:E3)  
  
            // 插入一行(1)  
            // insertRowData(sheet, 1, new String[] { "200201001", "张三", "100",  
            // "60", "100", "260" },  
            // getDataCellFormat(CellType.STRING_FORMULA));  
  
            // 插入日期  
            // mergeCellsAndInsertData(sheet, 1, 3, 5, 3, new Date(),  
            // getDataCellFormat(CellType.DATE));  
  
            workbook.write();  
            workbook.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    /** 
     * 初始化表格属性 
     *  
     * @param sheet 
     */  
    public void initialSheetSetting(WritableSheet sheet) {  
        try {  
            // sheet.getSettings().setProtected(true); //设置xls的保护，单元格为只读的  
            sheet.getSettings().setDefaultColumnWidth(10); // 设置列的默认宽度  
            // sheet.setRowView(2,false);//行高自动扩展  
            // setRowView(int row, int height);--行高  
            sheet.setRowView(0, 500);// 列宽  
            sheet.setColumnView(0, 20);// 设置第一列宽度  
            sheet.setColumnView(1, 20);  
            sheet.setColumnView(2, 20);  
            sheet.setColumnView(3, 20);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    /** 
     * 插入公式 
     *  
     * @param sheet 
     * @param col 
     * @param row 
     * @param formula 
     * @param format 
     */  
    public void insertFormula(WritableSheet sheet, Integer col, Integer row, String formula, WritableCellFormat format) {  
        try {  
            Formula f = new Formula(col, row, formula, format);  
            sheet.addCell(f);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    /** 
     * 修改公式单元格的值 
     *  
     * @param dataSheet 
     *            WritableSheet : 工作表 
     * @param col 
     *            int : 列 
     * @param row 
     *            int : 行 
     * @param startPos 
     *            int : 开始位置 
     * @param endPos 
     *            int : 结束位置 
     * @param format 
     * @throws RowsExceededException 
     * @throws WriteException 
     */  
    private void insertFormulaCell(WritableSheet dataSheet, int col, int row, int startPos, int endPos, CellFormat format)  
            throws RowsExceededException, WriteException {  
        String f = getFormula(col, row, startPos, endPos);  
        System.out.println(f);  
        // 插入公式（只支持插入，不支持修改）  
        WritableCell cell = dataSheet.getWritableCell(col, row);  
        if (cell.getType() == CellType.EMPTY) {  
            // 公式单元格  
            Formula lbl = new Formula(col, row, f);  
            if (null != format) {  
                lbl.setCellFormat(format);  
            }  
            dataSheet.addCell(lbl);  
        } else if (cell.getType() == CellType.STRING_FORMULA) {  
            System.out.println("Formula modify not supported!");  
        }  
    }  
  
    /** 
     * 得到公式 
     *  
     * @param col 
     *            int : 列 
     * @param row 
     *            int : 行 
     * @param startPos 
     *            int : 开始位置 
     * @param endPos 
     *            int : 结束位置 
     * @return String 
     * @throws RowsExceededException 
     * @throws WriteException 
     */  
    private String getFormula(int col, int row, int startPos, int endPos) throws RowsExceededException, WriteException {  
        char base = 'A';  
        char c1 = base;  
        StringBuffer formula = new StringBuffer(128);  
        // 组装公式  
        formula.append("SUM(");  
        if (col <= 25) {  
            c1 = (char) (col % 26 + base);  
            formula.append(c1).append(startPos).append(":").append(c1).append(endPos).append(")");  
        } else if (col > 25) {  
            char c2 = (char) ((col - 26) / 26 + base);  
            c1 = (char) ((col - 26) % 26 + base);  
            formula.append(c2).append(c1).append(startPos).append(":").append(c2).append(c1).append(endPos).append(")");  
        }  
  
        return formula.toString();  
    }  
  
    /** 
     * 插入一行数据 
     *  
     * @param sheet 
     *            工作表 
     * @param row 
     *            行号 
     * @param content 
     *            内容 
     * @param format 
     *            风格 
     */  
    public void insertRowData(WritableSheet sheet, Integer row, String[] dataArr, WritableCellFormat format) {  
        try {  
            Label label;  
            for (int i = 0; i < dataArr.length; i++) {  
                label = new Label(i, row, dataArr[i], format);  
                sheet.addCell(label);  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    /** 
     * 插入单元格数据 
     *  
     * @param sheet 
     * @param col 
     * @param row 
     * @param data 
     */  
    public void insertOneCellData(WritableSheet sheet, Integer col, Integer row, Object data, WritableCellFormat format) {  
        try {  
            if (data instanceof Double) {  
                jxl.write.Number labelNF = new jxl.write.Number(col, row, (Double) data, format);  
                sheet.addCell(labelNF);  
            } else if (data instanceof Integer) {  
                jxl.write.Number labelNF = new jxl.write.Number(col, row, (Integer) data, format);  
                sheet.addCell(labelNF);  
            } else if (data instanceof Boolean) {  
                jxl.write.Boolean labelB = new jxl.write.Boolean(col, row, (Boolean) data, format);  
                sheet.addCell(labelB);  
            } else if (data instanceof Date) {  
                jxl.write.DateTime labelDT = new jxl.write.DateTime(col, row, (Date) data, format);  
                sheet.addCell(labelDT);  
                setCellComments(labelDT, "这是个创建表的日期说明！");  
            } else {  
                Label label = new Label(col, row, data.toString(), format);  
                sheet.addCell(label);  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
    }  
  
    /** 
     * 合并单元格，并插入数据 
     *  
     * @param sheet 
     * @param col_start 
     * @param row_start 
     * @param col_end 
     * @param row_end 
     * @param data 
     * @param format 
     */  
    public void mergeCellsAndInsertData(WritableSheet sheet, Integer col_start, Integer row_start, Integer col_end, Integer row_end,  
            Object data, WritableCellFormat format) {  
        try {  
            sheet.mergeCells(col_start, row_start, col_end, row_end);// 左上角到右下角  
            insertOneCellData(sheet, col_start, row_start, data, format);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
    }  
  
    /** 
     * 给单元格加注释 
     *  
     * @param label 
     * @param comments 
     */  
    public void setCellComments(Object label, String comments) {  
        WritableCellFeatures cellFeatures = new WritableCellFeatures();  
        cellFeatures.setComment(comments);  
        if (label instanceof jxl.write.Number) {  
            jxl.write.Number num = (jxl.write.Number) label;  
            num.setCellFeatures(cellFeatures);  
        } else if (label instanceof jxl.write.Boolean) {  
            jxl.write.Boolean bool = (jxl.write.Boolean) label;  
            bool.setCellFeatures(cellFeatures);  
        } else if (label instanceof jxl.write.DateTime) {  
            jxl.write.DateTime dt = (jxl.write.DateTime) label;  
            dt.setCellFeatures(cellFeatures);  
        } else {  
            Label _label = (Label) label;  
            _label.setCellFeatures(cellFeatures);  
        }  
    }  
  
    /** 
     * 插入图像到单元格（图像格式只支持png） 
     *  
     * @param dataSheet 
     *            WritableSheet : 工作表 
     * @param col 
     *            int : 列 
     * @param row 
     *            int : 行 
     * @param width 
     *            int : 宽 
     * @param height 
     *            int : 高 
     * @param imgName 
     *            String : 图像的全路径 
     * @throws RowsExceededException 
     * @throws WriteException 
     */  
    private void insertImgCell(WritableSheet dataSheet, int col, int row, int width, int height, String imgName)  
            throws RowsExceededException, WriteException {  
        File imgFile = new File(imgName);  
        WritableImage img = new WritableImage(col, row, width, height, imgFile);  
        dataSheet.addImage(img);  
    }  
  
    /** 
     * 读取excel 
     *  
     * @param inputFile 
     * @param inputFileSheetIndex 
     * @throws Exception 
     */  
    public ArrayList<String> readDataFromExcel(File inputFile, int inputFileSheetIndex) {  
        ArrayList<String> list = new ArrayList<String>();  
        Workbook book = null;  
        Cell cell = null;  
        WorkbookSettings setting = new WorkbookSettings();  
        java.util.Locale locale = new java.util.Locale("zh", "CN");  
        setting.setLocale(locale);  
        setting.setEncoding("ISO-8859-1");  
        try {  
            book = Workbook.getWorkbook(inputFile, setting);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
        Sheet sheet = book.getSheet(inputFileSheetIndex);  
        for (int rowIndex = 0; rowIndex < sheet.getRows(); rowIndex++) {// 行  
            for (int colIndex = 0; colIndex < sheet.getColumns(); colIndex++) {// 列  
                cell = sheet.getCell(colIndex, rowIndex);  
                // System.out.println(cell.getContents());  
                list.add(cell.getContents());  
            }  
        }  
        book.close();  
  
        return list;  
    }  
  
    /** 
     * 得到数据表头格式 
     *  
     * @return 
     */  
    public WritableCellFormat getTitleCellFormat() {  
        WritableCellFormat wcf = null;  
        try {  
            // 字体样式  
            WritableFont wf = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD, false);// 最后一个为是否italic  
            wf.setColour(Colour.RED);  
            wcf = new WritableCellFormat(wf);  
            // 对齐方式  
            wcf.setAlignment(Alignment.CENTRE);  
            wcf.setVerticalAlignment(VerticalAlignment.CENTRE);  
            // 边框  
            wcf.setBorder(Border.ALL, BorderLineStyle.THIN);  
  
            // 背景色  
            wcf.setBackground(Colour.LIME);  
        } catch (WriteException e) {  
            e.printStackTrace();  
        }  
        return wcf;  
    }  
  
    /** 
     * 得到数据格式 
     *  
     * @return 
     */  
    public WritableCellFormat getDataCellFormat(CellType type) {  
        WritableCellFormat wcf = null;  
        try {  
            // 字体样式  
            if (type == CellType.NUMBER || type == CellType.NUMBER_FORMULA) {// 数字  
                NumberFormat nf = new NumberFormat("#.00");  
                wcf = new WritableCellFormat(nf);  
            } else if (type == CellType.DATE || type == CellType.DATE_FORMULA) {// 日期  
                jxl.write.DateFormat df = new jxl.write.DateFormat("yyyy-MM-dd hh:mm:ss");  
                wcf = new jxl.write.WritableCellFormat(df);  
            } else {  
                WritableFont wf = new WritableFont(WritableFont.TIMES, 10, WritableFont.NO_BOLD, false);// 最后一个为是否italic  
                wcf = new WritableCellFormat(wf);  
            }  
            // 对齐方式  
            wcf.setAlignment(Alignment.CENTRE);  
            wcf.setVerticalAlignment(VerticalAlignment.CENTRE);  
            // 边框  
            wcf.setBorder(Border.TOP, BorderLineStyle.THIN);  
            wcf.setBorder(Border.LEFT, BorderLineStyle.THIN);  
            wcf.setBorder(Border.BOTTOM, BorderLineStyle.THIN);  
            wcf.setBorder(Border.RIGHT, BorderLineStyle.THIN);  
            // 背景色  
            wcf.setBackground(Colour.WHITE);  
  
            wcf.setWrap(true);// 自动换行  
  
        } catch (WriteException e) {  
            e.printStackTrace();  
        }  
        return wcf;  
    }  
  
    /** 
     * 得到数据格式 
     *  
     * @return 
     */  
    public WritableCellFormat getDataCellFormat(CellType type, String fontType, int fontSize, jxl.format.Colour fontColour,  
            jxl.format.Colour background) {  
        WritableCellFormat wcf = null;  
        try {  
            // 字体样式  
            if (type == CellType.NUMBER || type == CellType.NUMBER_FORMULA) {// 数字  
                NumberFormat nf = new NumberFormat("#.00");  
                wcf = new WritableCellFormat(nf);  
            } else if (type == CellType.DATE || type == CellType.DATE_FORMULA) {// 日期  
                jxl.write.DateFormat df = new jxl.write.DateFormat("yyyy-MM-dd hh:mm:ss");  
                wcf = new jxl.write.WritableCellFormat(df);  
            } else {  
                WritableFont wf = new WritableFont(WritableFont.createFont(fontType == null ? "宋体" : fontType), fontSize,  
                        WritableFont.NO_BOLD, false, jxl.format.UnderlineStyle.NO_UNDERLINE, fontColour == null ? jxl.format.Colour.BLACK  
                                : fontColour);// 其中的false是 是否italic  
                wcf = new WritableCellFormat(wf);  
            }  
            // 对齐方式  
            wcf.setAlignment(Alignment.CENTRE);  
            wcf.setVerticalAlignment(VerticalAlignment.CENTRE);  
            // 边框  
            wcf.setBorder(Border.TOP, BorderLineStyle.THIN);  
            wcf.setBorder(Border.LEFT, BorderLineStyle.THIN);  
            wcf.setBorder(Border.BOTTOM, BorderLineStyle.THIN);  
            wcf.setBorder(Border.RIGHT, BorderLineStyle.THIN);  
            // 背景色  
            wcf.setBackground(background == null ? Colour.BLACK : background);  
  
            wcf.setWrap(true);// 自动换行  
        } catch (WriteException e) {  
            e.printStackTrace();  
        }  
        return wcf;  
    }  
  
    /** 
     * 打开文件看看 
     *  
     * @param exePath 
     * @param filePath 
     */  
    public void openExcel(String exePath, String filePath) {  
        Runtime r = Runtime.getRuntime();  
        String cmd[] = { exePath, filePath };  
        try {  
            r.exec(cmd);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    public static void main(String[] args) {  
        String[] titleData = { "编号", "姓名", "年龄", "出生日期" };  
        JExcelUtils jxl = new JExcelUtils();  
        String filePath = "C:/test.xls";  
        jxl.createExcelFile(filePath, "sheet1", titleData, "人员信息表", "D:/LogoPNG.png");  
  
        jxl.readDataFromExcel(new File(filePath), 0);  
        jxl.openExcel("C:/Program Files/Microsoft Office/OFFICE12/EXCEL.EXE", filePath);  
    }  
  
    public static List<Person> getData() {  
        List<Person> list = new ArrayList<Person>();  
        Person person1 = new Person();  
        person1.setId(1);  
        person1.setName("wangyu");  
        person1.setSex(20);  
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        // String newdate = sdf.format(new Date());  
        person1.setBirthday(new Date(System.currentTimeMillis()));  
  
        Person person2 = new Person();  
        person2.setId(2);  
        person2.setName("zhangsan");  
        person2.setSex(29);  
        person2.setBirthday(new Date(System.currentTimeMillis()));  
  
        Person person3 = new Person();  
        person3.setId(3);  
        person3.setName("lisi");  
        person3.setSex(18);  
        person3.setBirthday(new Date(System.currentTimeMillis()));  
        list.add(person1);  
        list.add(person2);  
        list.add(person3);  
        return list;  
    }  
}  
  
class Person {  
    private int id;  
    private String name;  
    private int sex;  
    private Date birthday;  
  
    public int getId() {  
        return id;  
    }  
  
    public void setId(int id) {  
        this.id = id;  
    }  
  
    public String getName() {  
        return name;  
    }  
  
    public void setName(String name) {  
        this.name = name;  
    }  
  
    public int getSex() {  
        return sex;  
    }  
  
    public void setSex(int sex) {  
        this.sex = sex;  
    }  
  
    public Date getBirthday() {  
        return birthday;  
    }  
  
    public void setBirthday(Date birthday) {  
        this.birthday = birthday;  
    }  
}
