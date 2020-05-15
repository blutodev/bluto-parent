package com.bluto.utils.encrypt;

public enum AlgorithmEnum {

    /**密钥对称加密 **/
    KEY_ALGORITHM_AES("AES", 128),
    KEY_ALGORITHM_DES("DES", 56),
    KEY_ALGORITHM_DESEDE("DESede", 168),

    /**密钥非对称加密 **/
    KEY_ALGORITHM_DSA("DSA", 1024),
    KEY_ALGORITHM_RSA_512("RSA", 512),
    KEY_ALGORITHM_RSA_1024("RSA", 1024),

    /** 加密/解密算法-工作模式 **/
    CIPHER_AES_CBC_NOPADDING("AES_CBC_NoPadding", 128),
    CIPHER_AES_CBC_PKCS5PADDING("AES_CBC_PKCS5Padding" ,128),
    CIPHER_AES_ECB_NOPADDING ("AES/ECB/NoPadding", 128),
    CIPHER_AES_ECB_PKCS5PADDING("AES/ECB/PKCS5Padding", 128),
    CIPHER_DES_CBC_NOPADDING("DES/CBC/NoPadding", 56),
    CIPHER_DES_CBC_PKCS5PADDING("DES/CBC/PKCS5Padding", 56),
    CIPHER_DES_ECB_NOPADDING("DES/ECB/NoPadding", 56),
    CIPHER_DES_ECB_PKCS5PADDING("DES/ECB/PKCS5Padding", 56),
    CIPHER_DESEDE_CBC_NOPADDING("DESede/CBC/NoPadding", 168),
    CIPHER_DESEDE_CBC_PKCS5PADDING("DESede/CBC/PKCS5Padding", 168),
    CIPHER_DESEDE_ECB_NOPADDING("DESede/ECB/NoPadding", 168),
    CIPHER_DESEDE_ECB_PKCS5PADDING("DESede/ECB/PKCS5Padding", 168),
    CIPHER_RSA_ECB_PKCS1PADDING_1024("RSA/ECB/PKCS1Padding", 1024),
    CIPHER_RSA_ECB_PKCS1PADDING_2048("RSA/ECB/PKCS1Padding", 2048);

    private final String type;
    private final int size;

    AlgorithmEnum(String type, int size) {
        this.type = type;
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public static int getSizeByType(String type){
        AlgorithmEnum[] algorithmEnums = AlgorithmEnum.values();
        for(AlgorithmEnum each : algorithmEnums){
            if(type.equals(each.getType())){
                return each.getSize();
            }
        }
        return 0;
    }
}
