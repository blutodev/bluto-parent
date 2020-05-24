package com.bluto.utils.ocr;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.setting.dialect.Props;

import java.io.File;
import java.security.NoSuchProviderException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 文字识别对象生成工厂
 *
 * @author  bluto
 * @date    2020/5/24
 */
public class BltOcrFactory {

    private static Map<BltOcrProvider, BltOcr> nkOcrProviderMap = new ConcurrentHashMap<>();

    private static BltOcrFactory instance = null;

    private static final String BLT_SCAN_OCR_PACKAGE = "com.bluto.utils.ocr";

    private BltOcrFactory(){}

    /**
     * 单例模式创建ocr工厂对象
     * @return ocr工厂对象
     */
    public static synchronized BltOcrFactory getInstance(){
        if(null == instance){
            instance = new BltOcrFactory();
        }
        if(MapUtil.isEmpty(nkOcrProviderMap)) {
            loadOcrProvider();
        }
        return instance;
    }

    /**
     * 根据供应商枚举，创建ocr对象
     * @param provider 供应商枚举
     * @return ocr对象
     * @throws NoSuchProviderException
     */
    public BltOcr createOcr(BltOcrProvider provider) throws NoSuchProviderException{
        BltOcr bltOcr = nkOcrProviderMap.get(provider);
        if(null == bltOcr){
            throw new NoSuchProviderException("no provider found");
        }
        return bltOcr;
    }

    /**
     * 通过配置文件中ocr供应商标识，创建ocr对象
     * @param propertiesFile 配置文件
     * @return ocr对象
     * @throws NoSuchProviderException
     */
    public BltOcr createOcr(File propertiesFile) throws NoSuchProviderException{
        String nkOrcProvider = new Props(propertiesFile).getStr("blt.orc.provider");
        BltOcrProvider provider = BltOcrProvider.valueOf(nkOrcProvider);
        return createOcr(provider);
    }

    /**
     * 加载orc供应商对象到map中
     */
    private static void loadOcrProvider(){
        Set<Class<?>> classes = ClassUtil.scanPackageBySuper(BLT_SCAN_OCR_PACKAGE, BltOcr.class);
        classes.stream().forEach(each -> {
            BltOcr ocrApi = (BltOcr) ReflectUtil.newInstance(each, null);
            nkOcrProviderMap.put(ocrApi.getOrcProvider(), ocrApi);
        });
    }
}
