package com.csm.myproject.Utils;

/**
 * @Author 快乐小柴
 * @Date 2022/10/23 16:10
 * @Version 1.0
 */
import org.apache.commons.codec.binary.Base64;
import java.io.*;
public class Base64Util {
    // 对字节数组字符串进行Base64解码并生成图片
    //imgFilePath 待保存的本地路径
    public static void GenerateImage(String base64Str, String imgFilePath) {
        // 图像数据为空
        if (base64Str == null) {
            return;
        }
        try {
            // Base64解码
            byte[] bytes = Base64.decodeBase64(base64Str);
            for (int i = 0; i < bytes.length; ++i) {
                // 调整异常数据
                if (bytes[i] < 0) {
                    bytes[i] += 256;
                }
            }
            // 生成jpeg图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(bytes);
            out.flush();
            out.close();
            //====
        } catch (Exception e) {
        }
    }
}


