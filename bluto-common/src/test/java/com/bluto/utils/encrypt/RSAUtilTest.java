package com.bluto.utils.encrypt;

import org.junit.BeforeClass;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class RSAUtilTest {

    private static String publicKey = "";
    private static String privateKey = "";
    private static String data = "vEedcYzazBaFwqMKHsHh4A==";
    private static String encryptData = "";

    @BeforeClass
    public static void beforeClass(){
        System.out.println("================ generate public/private key start ============");
        try {
            Map<String, String> keyPairs =
                    EncryptHelper.generateKeyPair(AlgorithmEnum.KEY_ALGORITHM_RSA_512.getType());
            publicKey = keyPairs.get("publicKey");
            privateKey = keyPairs.get("privateKey");
            System.out.println("publicKey=" + publicKey);
            System.out.println("privateKey=" + privateKey);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        System.out.println("================ generate public/private key end ============");
    }

    @Test
    public void privateEncrypt() {
        try {
            System.out.println("================ privateEncrypt() data=" + data);
            encryptData = RSAUtil.privateEncrypt(data, privateKey);
            System.out.println("================ privateEncrypt() result=" + encryptData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void publicDecrypt() {
        try {
            String result = RSAUtil.publicDecrypt(encryptData, publicKey);
            System.out.println("================ publicDecrypt() result=" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void publicEncrypt() {
        try {
            encryptData = RSAUtil.publicEncrypt(data, publicKey);
            System.out.println("================ publicEncrypt() result=" + encryptData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void privateDecrypt() {
        try {
            System.out.println("================ privateDecrypt() encryptData=" + encryptData);
            String result = RSAUtil.privateDecrypt(encryptData, privateKey);
            System.out.println("================ privateDecrypt() result=" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}