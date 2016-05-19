package com.hbkis.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class FileUtil {

    /**
     * 写文件
     *
     * @param fromStream  源文件输入流
     * @param toPath 目标文件地址
     * @param bufSize  缓冲区大小 如：1024
     * @return 操作状态
     */
    public static boolean writeFile(InputStream fromStream, String toPath,int bufSize) {
        boolean status = false;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        File parentFile = new File(toPath).getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }

        try {
            bis = new BufferedInputStream(fromStream);
            bos = new BufferedOutputStream(new FileOutputStream(toPath));

            byte[] buf = new byte[bufSize];
            int bufLength = 0;
            while ((bufLength = bis.read(buf)) != -1) {
                bos.write(buf, 0, bufLength);
                bos.flush();
            }

            status = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return status;
    }

    /**
     * 删除单个文件或单个空文件夹
     * @param filePath
     */
    public static void deleteFile(String filePath) {
        try {
            File file = new File(filePath);
            file.delete();
        } catch (Exception e) {
            System.out.println("删除文件失败！");
        }
    }
    
    /**
     * 删除文件夹和文件夹内的所有文件（包含文件夹）
     * @param folderPath 文件夹路径  
     * @return 成功或失败
     * 如：E:/log/error  则删除error和包含的所有文件
     */
    public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + File.separator + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + File.separator + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}
    //上传图片并且返回图片相关参数
    public static String getUploadParam(HttpServletRequest request2,String dispatchUrl){
        //上传图片，封装图片属性
        
        MultipartHttpServletRequest request = (MultipartHttpServletRequest) request2;
        List<MultipartFile> files=request.getFiles("fileUpload");  
        JSONArray array = new JSONArray();
        try { 
            String localUrl = null;

            if(files != null && files.size() > 0){
                for(MultipartFile file:files){
                    if(file.isEmpty()){
                        continue;
                    }
                    String fileName = file.getOriginalFilename();
                    fileName = System.currentTimeMillis()+ ""+ new Random().nextInt(10000)+ fileName.substring(fileName.lastIndexOf("."),fileName.length());
                    JSONObject obj = new JSONObject();
                    obj.put("imgpath", dispatchUrl+fileName);
                    obj.put("imagsize", file.getSize());
                    obj.put("imagname", fileName);

                    FileUtil.writeFile(file.getInputStream(), localUrl+dispatchUrl+fileName, 1024);
                    array.add(obj);
                }
            }
//            mainObj.put("imgarray", array);
        }catch (Exception e1) {
            e1.printStackTrace();
        }
        String str = array.toString();
        return str;
    }
}
