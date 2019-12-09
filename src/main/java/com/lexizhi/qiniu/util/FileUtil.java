package com.lexizhi.qiniu.util;

/**
 * @Author: laoyu
 * @Date: 2019/12/9 15:30
 * @Description: 这个类只有一个简单的方法，
 * 那就是判断图片的后缀是否符合要求。
 */
public class FileUtil {
    //图片允许的后缀扩展名
    public static String[] IMAGE_FILE_EXID = new String[]{"png", "bmp", "jpg", "jpeg","pdf"};
    public static boolean isFileAllowed(String fileName){
        for (String ext : IMAGE_FILE_EXID) {
            if (ext.equals(fileName)) {
                return true;
            }
        }
        return false;
    }
}
