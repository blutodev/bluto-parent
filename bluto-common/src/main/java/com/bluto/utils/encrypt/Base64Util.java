package com.bluto.utils.encrypt;

import java.nio.charset.StandardCharsets;

/**
 * <p>Base64Util.java </p>
 * <p>
 * <p>功能描述: Base64对象工具<p>
 * <p>
 * <p>作    者: bluto</p>
 * <p>创建时间: 2020年5月13日<p>
 */
public class Base64Util {
    private Base64Util() {
        throw new IllegalStateException("Base64Util class");
    }

    /**
     * base64算法加密
     * @param str
     * @return
     */
    public static String enCode(String str){
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        String result = java.util.Base64.getEncoder().encodeToString(bytes);
        return result;
    }

    /**
     * base64算法解密
     * @param str
     * @return
     * @throws Exception
     */
    public static String decode(String str){
        byte[] desc = java.util.Base64.getDecoder().decode(str);
        String result = new String(desc, StandardCharsets.UTF_8);
        return result;
    }
}
