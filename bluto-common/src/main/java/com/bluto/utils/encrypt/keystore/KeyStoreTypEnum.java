package com.bluto.utils.encrypt.keystore;

/**
 *
 * KeyStoreTypEnum.java
 * @Description: 秘钥库keystore类型
 * @author bluto
 * @date 2020/5/1713:04
 */
public enum KeyStoreTypEnum {

    KEY_STORE_TYP_JCEKS("jceks"),
    KEY_STORE_TYP_JKS("jks"),
    KEY_STORE_TYP_DKS("dks"),
    KEY_STORE_TYP_PKCS11("pkcs11"),
    KEY_STORE_TYP_PKCS12("pkcs12");

    private String name;

    KeyStoreTypEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
