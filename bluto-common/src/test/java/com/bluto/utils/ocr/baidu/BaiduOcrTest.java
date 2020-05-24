package com.bluto.utils.ocr.baidu;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONObject;
import com.bluto.utils.ocr.BltOcr;
import com.bluto.utils.ocr.BltOcrFactory;
import com.bluto.utils.ocr.BltOcrProvider;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * ${file_name}
 *
 * @author bluto
 * @Description: ${todo}
 * @date 2020/5/23 13:23
 */
public class BaiduOcrTest {

    private BltOcr bltOcr = null;

    @Before
    public void initBltOcr() throws Exception{
        BltOcrFactory factory = BltOcrFactory.getInstance();
        BltOcrProvider provider = BltOcrProvider.OCR_PROVIDER_BAIDU;
        bltOcr = factory.createOcr(provider);
    }

    @Test
    public void general() throws Exception{
        String filePath = "orc_general_test.jpg";
        byte[] imgData = FileUtil.readBytes(filePath);
        JSONObject rt = bltOcr.general(imgData);
        System.out.println("rt="+ rt.toJSONString());
    }

    @Test
    public void idcard() throws Exception{
        String front = "F:\\ocr_test_idcard_front.jpg";
        String back = "F:\\ocr_test_idcard_back.jpg";
        byte[] frontImg = FileUtil.readBytes(front);
        byte[] backImg = FileUtil.readBytes(back);
        System.out.println("front="+ bltOcr.idcard(frontImg, 0));
        System.out.println("back="+ bltOcr.idcard(backImg, 1));
    }

    @Test
    public void passport() {
        String filePath = "F:\\orc_test_passport.jpg";
        byte[] imgData = FileUtil.readBytes(filePath);
        System.out.println("passport="+ bltOcr.passport(imgData));
    }
}