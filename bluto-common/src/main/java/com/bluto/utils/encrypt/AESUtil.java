package com.bluto.utils.encrypt;

import cn.hutool.core.util.HexUtil;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

/**
 * <p>AESUtil.java </p>
 * <p>
 * <p>功能描述: AES对象工具<p>
 * <p>
 * <p>作    者: bluto</p>
 * <p>创建时间: 2020年5月13日<p>
 */
public class AESUtil {

    public AESUtil() {
        throw new IllegalStateException("AESUtil class");
    }

    /**
     * AES加密
     * @param data 明文
     * @param key 密钥
     * @param algorithm AES加密算法
     * @return
     */
    public static String encrypt(String data, String key, String algorithm) throws Exception{
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
        Cipher cipher = Cipher.getInstance(algorithm);
        SecretKey spec = EncryptHelper.
                generateSecretKey(key, AlgorithmEnum.KEY_ALGORITHM_RSA_512.getType());
        cipher.init(Cipher.ENCRYPT_MODE, spec);
        String result = HexUtil.encodeHexStr(cipher.doFinal(bytes));
        return result;
    }

    /**
     * AES加密(默认算法AES/ECB/PKCS5Padding)
     * @param data 明文
     * @param key 密钥
     * @return
     */
    public static String encrypt(String data, String key) throws Exception{
        return encrypt(data, key, AlgorithmEnum.CIPHER_AES_ECB_PKCS5PADDING.getType());
    }

    /**
     * AES解密
     * @param input 密文
     * @param key 密钥
     * @param algorithm 加密算法
     * @return
     */
    public static String decrypt(String input, String key, String algorithm) throws Exception{
        byte[] bytes = HexUtil.decodeHex(input);
        Cipher cipher = Cipher.getInstance(algorithm);
        SecretKey spec = EncryptHelper.
                generateSecretKey(key, AlgorithmEnum.KEY_ALGORITHM_RSA_512.getType());
        cipher.init(Cipher.DECRYPT_MODE, spec);
        String result = new String(cipher.doFinal(bytes), StandardCharsets.UTF_8);
        return result;
    }

    /**
     * AES解密(默认算法AES/ECB/PKCS5Padding)
     * @param input 密文
     * @param key 密钥
     * @return
     */
    public static String decrypt(String input, String key) throws Exception{
        return decrypt(input, key, AlgorithmEnum.CIPHER_AES_ECB_PKCS5PADDING.getType());
    }
}
