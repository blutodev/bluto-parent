package com.bluto.utils.encrypt;

import cn.hutool.core.util.HexUtil;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.Key;

/**
 * <p>RSAUtil.java </p>
 * <p>
 * <p>功能描述: RSA加密解密工具类<p>
 * <p>
 * <p>作    者: bluto</p>
 * <p>创建时间: 2020年5月15日<p>
 */
public class RSAUtil {
    public RSAUtil() {
        throw new IllegalStateException("RSAUtil class");
    }

    /**
     * 私钥加密
     * @param data 明文
     * @param privateKey 私钥
     * @return 加密后数据
     */
    public static String privateEncrypt(String data, String privateKey) throws Exception {
        return encrypt( data, Cipher.PRIVATE_KEY,
                privateKey, AlgorithmEnum.CIPHER_RSA_ECB_PKCS1PADDING_1024.getType());
    }

    /**
     * 公钥解密
     * @param ciperData 加密后数据
     * @param publicKey 公钥
     * @return 明文
     */
    public static String publicDecrypt(String ciperData, String publicKey) throws Exception {
        return decrypt(ciperData, Cipher.PUBLIC_KEY,
                publicKey, AlgorithmEnum.CIPHER_RSA_ECB_PKCS1PADDING_1024.getType());
    }

    /**
     * 公钥加密
     * @param data 明文
     * @param publicKey 公钥
     * @return 加密后数据
     */
    public static String publicEncrypt(String data, String publicKey) throws Exception {
        return encrypt(data, Cipher.PUBLIC_KEY,
                publicKey, AlgorithmEnum.CIPHER_RSA_ECB_PKCS1PADDING_1024.getType());
    }

    /**
     * 私钥解密
     * @param ciperData 加密后数据
     * @param privateKey 私钥
     * @return 明文
     */
    public static String privateDecrypt(String ciperData, String privateKey) throws Exception {
        return decrypt( ciperData, Cipher.PRIVATE_KEY,
                privateKey, AlgorithmEnum.CIPHER_RSA_ECB_PKCS1PADDING_1024.getType());
    }

    /**
     * 加密
     * @param data 明文
     * @param keyType 密钥类型：1公钥 2私钥
     * @param key 私钥或公钥
     * @param algorithm 加密/解密算法
     * @return 加密后数据
     */
    public static String encrypt(String data, int keyType, String key, String algorithm)
            throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        Key cipherKey = EncryptHelper.
                getKeyByType(keyType, key, AlgorithmEnum.KEY_ALGORITHM_RSA_512.getType());
        cipher.init(Cipher.ENCRYPT_MODE, cipherKey);
        byte[] temp = cipher.doFinal(data.getBytes());
        return HexUtil.encodeHexStr(temp);
    }

    /**
     * 解密
     * @param ciperData 加密后数据
     * @param keyType 密钥类型：1公钥 2私钥
     * @param key 私钥或公钥
     * @param algorithm 加密/解密算法
     * @return 明文
     */
    public static String decrypt(String ciperData, int keyType, String key, String algorithm)
            throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        Key cipherKey = EncryptHelper.getKeyByType(keyType,
                key, AlgorithmEnum.KEY_ALGORITHM_RSA_512.getType());
        cipher.init(Cipher.DECRYPT_MODE, cipherKey);
        byte[] temp = cipher.doFinal(HexUtil.decodeHex(ciperData));
        String data = new String(temp, StandardCharsets.UTF_8);
        return data;
    }
}
