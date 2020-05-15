package com.bluto.utils.encrypt;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>EncryptHelper.java </p>
 * <p>
 * <p>功能描述: 加密帮助类<p>
 * <p>
 * <p>作    者: bluto</p>
 * <p>创建时间: 2020年5月14日<p>
 */
public class EncryptHelper {

    private static final String RANDOM_ALGORITHM_SHA1PRNG = "SHA1PRNG";

    /**
     * 产生对称加密的密钥
     * @param key 密码
     * @param algorithm 算法
     * @return SecretKeySpec 对称加密的密钥
     * @throws NoSuchAlgorithmException
     */
    public static SecretKey generateSecretKey(String key, String algorithm)
            throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance(algorithm);
        SecureRandom secRandom = SecureRandom.getInstance(RANDOM_ALGORITHM_SHA1PRNG);
        String encodeKey = Base64Util.enCode(key);
        secRandom.setSeed(encodeKey.getBytes());
        keyGen.init(AlgorithmEnum.getSizeByType(algorithm), secRandom);
        SecretKey secretKey = keyGen.generateKey();
        byte[] format = secretKey.getEncoded();
        SecretKeySpec spec = new SecretKeySpec(format, algorithm);
        return spec;
    }

    /**
     * 产生非对称加密的密钥对
     * @param algorithm 算法
     * @return 公钥，私钥
     * @throws NoSuchAlgorithmException
     */
    public static Map<String, String> generateKeyPair(String algorithm)
            throws NoSuchAlgorithmException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(algorithm);
        kpg.initialize(AlgorithmEnum.getSizeByType(algorithm));
        KeyPair keyPair = kpg.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        String publicKeyBase64 =
                java.util.Base64.getEncoder().encodeToString(publicKey.getEncoded());
        String privateKeyBase64 =
                java.util.Base64.getEncoder().encodeToString(privateKey.getEncoded());
        Map<String, String> result = new HashMap<>();
        result.put("publicKey", publicKeyBase64);
        result.put("privateKey", privateKeyBase64);
        return result;
    }

    /**
     *
     * @param keyType 密钥类型：1公钥 2私钥
     * @param key 私钥或公钥
     * @param keyAlgorithm key算法
     * @return 密钥对象
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    public static Key getKeyByType(int keyType, String key, String keyAlgorithm)
            throws InvalidKeySpecException, NoSuchAlgorithmException {
        Key cipherKey = null;
        if(Cipher.PUBLIC_KEY == keyType) {
            cipherKey = getPublicKey(key, keyAlgorithm);
        }else if(Cipher.PRIVATE_KEY == keyType) {
            cipherKey = getPrivateKey(key, keyAlgorithm);
        }else {
            throw new InvalidKeySpecException("type of key must be set");
        }
        return cipherKey;
    }

    /**
     * 得到公钥/私钥
     * @param publicKey 密钥字符串（经过base64编码）
     * @param algorithm 算法
     * @return 公钥
     * @throws NoSuchAlgorithmException
     */
    public static PublicKey getPublicKey(String publicKey, String algorithm)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        X509EncodedKeySpec x509KeySpec =
                new X509EncodedKeySpec(java.util.Base64.getDecoder().decode(publicKey));
        PublicKey key = keyFactory.generatePublic(x509KeySpec);
        return key;
    }

    /**
     * 得到私钥
     * @param privateKey 密钥字符串（经过base64编码）
     * @param algorithm 算法
     * @return 私钥
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PrivateKey getPrivateKey(String privateKey, String algorithm)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        PKCS8EncodedKeySpec pkcs8KeySpec =
                new PKCS8EncodedKeySpec(java.util.Base64.getDecoder().decode(privateKey));
        PrivateKey key = keyFactory.generatePrivate(pkcs8KeySpec);
        return key;
    }
}
