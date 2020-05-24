package com.bluto.utils.ocr;


import com.alibaba.fastjson.JSONObject;
import com.bluto.utils.exception.BltUtilException;

/**
 * 文字识别自定义接口
 *
 * @author  bluto
 * @date    2020/5/24
 */
public interface BltOcr {

    String OCR_PROPS_FILE = "blt_orc.properties";

    /**
     * 获得ocr供应商
     * @return 供应商枚举
     */
    BltOcrProvider getOrcProvider();

    /**
     * 普通文字识别
     * @param image 图片base64后编码
     * @return 识别后文字
     * @throws Exception
     */
    JSONObject general(byte[] image) throws BltUtilException;

    /**
     * 身份证识别
     * @param image 图片base64后编码
     * @param sideTyp 正反面标识，0：正面 1：反面
     * @return 返回姓名、性别、民族、出生日期、住址、身份证号、签发机关、有效期限
     * @throws Exception
     */
    JSONObject idcard(byte[] image, int sideTyp) throws BltUtilException;

    /**
     * 护照识别
     * @param image 图片base64后编码
     * @return 返回国家码、护照号、姓名、姓名拼音、性别、出生地点、出生日期、签发地点、签发日期、有效期、签发机关
     * @throws Exception
     */
    JSONObject passport(byte[] image) throws BltUtilException;
}
