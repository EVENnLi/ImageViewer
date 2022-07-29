package com.example.imageviewer.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @decription: 获取hash之后的key，防止图片url中的特殊字符影响使用
 */
public class GetHashKey {
    public static String hashKeyFromUrl(String url) {
        String cacheKey=null;
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(url.getBytes());
            cacheKey = bytesToHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey=String.valueOf(url.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            //toHexString(int i):将整数参数的字符串表示形式返回为基数为 16 的无符号整数,hex是指十六进制
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                sb.append("0");
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
