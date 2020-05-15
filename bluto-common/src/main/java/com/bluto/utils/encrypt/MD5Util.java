package com.bluto.utils.encrypt;


import cn.hutool.core.util.HexUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <p>MD5Util.java </p>
 *
 * <p>功能描述: MD5对象工具<p>
 *
 * <p>作    者: bluto</p>
 * <p>创建时间: 2020年5月13日<p>
 *
 */
public class MD5Util {
    private MD5Util() {
        throw new IllegalStateException("MD5Util class");
    }

    /**
     * 获得字符串的md5值
     *
     * @param str 待加密的字符串
     * @return md5加密后的字符串
     */
    public static String getMD5String(String str) {
        String result = "";
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
            byte[] data = md5.digest(bytes);
            result = HexUtil.encodeHexStr(data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获得字符串的md5大写值
     *
     * @param str 待加密的字符串
     * @return md5加密后的大写字符串
     */
    public static String getMD5UpperString(String str) {
        return getMD5String(str).toUpperCase();
    }

    /**
     * 校验字符串的md5值
     *
     * @param str 目标字符串
     * @param md5 基准md5
     * @return 校验结果
     */
    public static boolean checkMD5IgnoreCase(String str, String md5) {
        return getMD5String(str).equalsIgnoreCase(md5);
    }

    /**
     * 获取文件的md5.
     *
     * @param file
     * @return
     */
    public static String getFileMD5Str(File file) {
        FileInputStream fin = null;
        FileChannel fch = null;
        String result = "";
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            fin = new FileInputStream(file);
            fch = fin.getChannel();
            ByteBuffer byteBuffer = fch.map(FileChannel.MapMode.READ_ONLY,
                    0, file.length());
            md5.update(byteBuffer);
            result = HexUtil.encodeHexStr(md5.digest());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }finally {
            try {
                if (fch != null) {
                    fch.close();
                }
                if (fin != null) {
                    fin.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
