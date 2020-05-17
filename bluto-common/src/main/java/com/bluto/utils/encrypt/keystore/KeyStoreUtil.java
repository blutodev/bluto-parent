package com.bluto.utils.encrypt.keystore;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.digest.DigestUtil;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

/**
* KeyStoreUtil.java
* @Description: 密码库对象工具类
* @author bluto
* @date 2020/5/17
*/
public class KeyStoreUtil {

    private static String KEYSTORE_PASSWORD="admin@123456";
    private static String KEYSTORE_ALIAS="myCa1";
    private static String KEYSTORE_ALIAS_PASSWORD="bluto@2020";

    /**
     * 获得秘钥库对象，默认秘钥库类型为jks
     * @param file 秘钥库文件
     * @param password 秘钥库密码
     * @return
     */
    public static KeyStore getDefaultKeyStore(File file, String password)
            throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        return getKeyStore(file, password, KeyStoreTypEnum.KEY_STORE_TYP_JKS);
    }

    /**
     * 获得秘钥库对象
     * @param file 秘钥库文件
     * @param password 秘钥库密码
     * @param keyStoreTpy 秘钥库类型
     * @return 秘钥库对象
     */
    public static KeyStore getKeyStore(File file, String password, KeyStoreTypEnum keyStoreTpy)
            throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        Assert.notNull(file, "file is null !", new Object[0]);
        Assert.notEmpty(password, "password is empty !", new Object[0]);

        InputStream stream = new FileInputStream(file);
        KeyStore keyStore = KeyStore.getInstance(keyStoreTpy.getName());
        keyStore.load(stream, password.toCharArray());
        return keyStore;
    }


    public static PrivateKey getPrivateKey(KeyStore keyStore, String alias, String aliasKey)
            throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException {
        KeyStore.ProtectionParameter protParam =
                new KeyStore.PasswordProtection(aliasKey.toCharArray());
        KeyStore.PrivateKeyEntry pkEntry =
                (KeyStore.PrivateKeyEntry) keyStore.getEntry(alias, protParam);
        PrivateKey pk = pkEntry.getPrivateKey();
        return pk;
    }

    public static Certificate getTrustedCertificate(KeyStore keyStore, String alias, String aliasKey)
            throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException {
        // "main" java.lang.UnsupportedOperationException: trusted certificate entries are not password-protected
        KeyStore.ProtectionParameter protParam =
                new KeyStore.PasswordProtection(aliasKey.toCharArray());
        KeyStore.TrustedCertificateEntry CaEntry =
                (KeyStore.TrustedCertificateEntry)keyStore.getEntry(alias, null);
        Certificate cert = CaEntry.getTrustedCertificate();
        return cert;
    }

    public static void main(String[] args) {
        try {
            File file = FileUtil.file("com/bluto/utils/encrypt/keystore/myCa1.jks");
            KeyStore keyStore = getDefaultKeyStore(file, KEYSTORE_PASSWORD);

            //File file = FileUtil.file("com/bluto/utils/encrypt/keystore/myks-des.store");
            //KeyStore keyStore = getKeyStore(file, "123456", KeyStoreTypEnum.KEY_STORE_TYP_JCEKS);

            //java.security.UnrecoverableKeyException: Cannot recover key 检查alias密码是否正确

            PrivateKey pk = getPrivateKey(keyStore, KEYSTORE_ALIAS, KEYSTORE_ALIAS_PASSWORD);
            String algorithm = pk.getAlgorithm();
            String encoded = HexUtil.encodeHexStr(pk.getEncoded());
            System.out.println("algorithm=" + algorithm);
            System.out.println("encoded=" + encoded);

            X509Certificate x509 = (X509Certificate)getTrustedCertificate
                    (keyStore, "ca_trust_cert1", "123456");
            int version = x509.getVersion();
            Principal issuerDN = x509.getIssuerDN();
            Principal subjectDN = x509.getSubjectDN();
            String x509SigAlgName = x509.getSigAlgName();
            System.out.println(KEYSTORE_ALIAS + " 版本 =" + version);
            System.out.println(KEYSTORE_ALIAS + " 序列号 =" + HexUtil.toHex(x509.getSerialNumber().intValue()));
            System.out.println(KEYSTORE_ALIAS + " 颁发者 =" + issuerDN.getName());
            System.out.println(KEYSTORE_ALIAS + " 使用者 =" + subjectDN.getName());
            System.out.println(KEYSTORE_ALIAS + " 有效期从 =" + x509.getNotBefore());
            System.out.println(KEYSTORE_ALIAS + " 有效期至 =" + x509.getNotAfter());
            System.out.println(KEYSTORE_ALIAS + " 签名算法 =" + x509SigAlgName);
            PublicKey publicKey = x509.getPublicKey();
            System.out.println(KEYSTORE_ALIAS + " 证书公钥信息 算法=" + publicKey.getAlgorithm());
            System.out.println(KEYSTORE_ALIAS + " 证书公钥信息 算法=" + HexUtil.encodeHexStr(publicKey.getEncoded()));
            System.out.println(KEYSTORE_ALIAS + " 证书指纹信息 =" + DigestUtil.sha1Hex(x509.getEncoded()));




            Enumeration<String> aliases = keyStore.aliases();
            while(aliases.hasMoreElements()){
                String alias = aliases.nextElement();
                if(keyStore.getCertificate(alias).getType().equals("X.509")){
                    //X509Certificate x509 = ((X509Certificate) keyStore.getCertificate(alias));



                }
            }







        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnrecoverableEntryException e) {
            e.printStackTrace();
        }

    }
}
