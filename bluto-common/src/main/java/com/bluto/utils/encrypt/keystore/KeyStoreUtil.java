package com.bluto.utils.encrypt.keystore;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.digest.DigestUtil;

import javax.crypto.SecretKey;
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

    /**
     * 获得私钥（非对称加密）
     * @param keyStore 密钥库
     * @param alias 别名
     * @param aliasKey 别名密码
     * @return
     * @throws UnrecoverableEntryException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     */
    public static PrivateKey readPrivateKey(KeyStore keyStore, String alias, String aliasKey)
            throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException {
        KeyStore.PrivateKeyEntry pkEntry =
                (KeyStore.PrivateKeyEntry) readEntry(keyStore, KeyRep.Type.PRIVATE, alias, aliasKey);
        PrivateKey pk = pkEntry.getPrivateKey();
        return pk;
    }

    /**
     * 获得密钥（对称加密时）
     * @param keyStore 密钥库
     * @param alias 别名
     * @param aliasKey 别名密码
     * @return
     * @throws UnrecoverableEntryException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     */
    public static SecretKey readSecretKey(KeyStore keyStore, String alias, String aliasKey)
            throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException {
        KeyStore.SecretKeyEntry skEntry =
                (KeyStore.SecretKeyEntry) readEntry(keyStore, KeyRep.Type.SECRET, alias, aliasKey);
        SecretKey secretKey = skEntry.getSecretKey();
        return secretKey;
    }

    /**
     * 获得颁发的可信赖证书
     * @param keyStore 密钥库
     * @param alias 别名
     * @param aliasKey 别名密码
     * @return
     * @throws UnrecoverableEntryException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     */
    public static X509Certificate readTrustedCertificate(KeyStore keyStore, String alias, String aliasKey)
            throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException {
        // trusted certificate entries are not password-protected
        KeyStore.TrustedCertificateEntry caEntry =
                (KeyStore.TrustedCertificateEntry) readEntry(keyStore, KeyRep.Type.PUBLIC, alias, null);
        X509Certificate cert = (X509Certificate) caEntry.getTrustedCertificate();
        return cert;
    }

    protected static KeyStore.Entry readEntry(KeyStore keyStore, KeyRep.Type keyType, String alias, String aliasKey)
            throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException {
        KeyStore.Entry entry = null;
        if(keyType.equals(KeyRep.Type.PRIVATE) || keyType.equals(KeyRep.Type.SECRET)){
            KeyStore.ProtectionParameter potParam =
                    new KeyStore.PasswordProtection(aliasKey.toCharArray());
            entry = keyStore.getEntry(alias, potParam);
        }else if(keyType.equals(KeyRep.Type.PUBLIC)){
            entry = keyStore.getEntry(alias, null);
        }
        return entry;
    }

    public static void main(String[] args) {
        try {
            File file = FileUtil.file("com/bluto/utils/encrypt/keystore/myCa1.jks");
            KeyStore keyStore = getDefaultKeyStore(file, KEYSTORE_PASSWORD);

            //File file = FileUtil.file("com/bluto/utils/encrypt/keystore/myks-des.store");
            //KeyStore keyStore = getKeyStore(file, "123456", KeyStoreTypEnum.KEY_STORE_TYP_JCEKS);

            //java.security.UnrecoverableKeyException: Cannot recover key 检查alias密码是否正确

            PrivateKey pk = readPrivateKey(keyStore, KEYSTORE_ALIAS, KEYSTORE_ALIAS_PASSWORD);
            String algorithm = pk.getAlgorithm();
            String encoded = HexUtil.encodeHexStr(pk.getEncoded());
            System.out.println("algorithm=" + algorithm);
            System.out.println("encoded=" + encoded);

            X509Certificate x509 = (X509Certificate)readTrustedCertificate
                    (keyStore, "ca_trust_cert1", null);
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
