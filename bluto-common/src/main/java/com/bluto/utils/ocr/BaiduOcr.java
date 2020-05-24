package com.bluto.utils.ocr;

import cn.hutool.core.lang.Assert;
import cn.hutool.setting.dialect.Props;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.aip.ocr.AipOcr;
import com.bluto.utils.exception.BltUtilException;
import com.bluto.utils.exception.ErrorInfo;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * baidu文字识别实现对象
 *
 * @author  bluto
 * @date    2020/5/24
 */
public class BaiduOcr implements BltOcr {

    private static AipOcr client = null;

    //访问baidu接口时必要的验证信息
    private static final String BAIDU_APPID = "baidu.appId";
    private static final String BAIDU_APIKEY = "baidu.apiKey";
    private static final String BAIDU_SECRET_KEY = "baidu.secretKey";

    //身份证正反面标识
    private static final int ID_CARD_SIDE_FRONT = 0;
    private static final int ID_CARD_SIDE_BACK = 1;

    public BaiduOcr(){
        //初始化百度orc对象
        Props props = Props.getProp(OCR_PROPS_FILE, Charset.defaultCharset());
        String appId = props.getStr(BAIDU_APPID);
        String apiKey = props.getStr(BAIDU_APIKEY);
        String secretKey = props.getStr(BAIDU_SECRET_KEY);
        if(null == client){
            synchronized (BaiduOcr.class) {
                client = new AipOcr(appId, apiKey, secretKey);
            }
        }
    }

    @Override
    public BltOcrProvider getOrcProvider() {
        return BltOcrProvider.OCR_PROVIDER_BAIDU;
    }

    @Override
    public JSONObject general(byte[] image) throws BltUtilException{
        //入参为空校验
        Assert.notNull(image, "image is null !", new Object[0]);

        //调用接口并校验错误码
        org.json.JSONObject object = client.basicGeneral(image, generateOptionArg());
        JSONObject resp = this.checkOcrApiResp(object);

        //转换处理
        //处理前:{"words_result":[{"words":"AAA","words":"BBB"}]}
        //处理后:{"words":"AAABBB"}
        JSONArray wordArray = resp.getJSONArray("words_result");
        String words = wordArray.stream()
                .map(each -> ((JSONObject)each).getString("words"))
                .reduce((a, b) -> a.concat(System.getProperty("line.separator")).concat(b)).get();

        //封装返回结果
        JSONObject rtn = new JSONObject();
        rtn.put("words", words);
        return rtn;
    }

    @Override
    public JSONObject idcard(byte[] image, int sideTyp) throws BltUtilException{
        //入参为空校验
        Assert.notNull(image, "image is null !", new Object[0]);
        Assert.checkBetween(sideTyp, 0 ,1);

        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<>();
        options.put("detect_direction", "true");
        options.put("detect_language", "true");

        //调用接口并校验错误码
        org.json.JSONObject object = client.idcard(image,
                sideTyp == ID_CARD_SIDE_FRONT ? "front" : "back", generateOptionArg());
        JSONObject resp = this.checkOcrApiResp(object);

        //转换处理
        //处理前:{"姓名":{"words":"韩丹","location":{"top":801,"left":1098,"width":385,"height":157}}
        //处理后:{"姓名":"韩丹"}
        Map<String, Object> data = this.stripCardOcrApiResp(resp);
        return new JSONObject(data);

//        Map<String, String> idCardKeyMap = getIdCardKeyMap(sideTyp);
//        idCardKeyMap.keySet().stream().forEach( e ->
//                rtnMap.put(idCardKeyMap.get(e),
//                        idInfoObj.getJSONObject(e).getString("words")
//        ));

    }

    @Override
    public JSONObject passport(byte[] image) {
        //入参为空校验
        Assert.notNull(image, "image is null !", new Object[0]);
        //调用接口并校验错误码
        org.json.JSONObject object = client.passport(image, generateOptionArg());
        JSONObject resp = this.checkOcrApiResp(object);
        //转换处理
        Map<String, Object> data = this.stripCardOcrApiResp(resp);
        return new JSONObject(data);
    }

    /**
     * 校验ocr接口返回时是否出现错误码
     * @param object ocr接口返回原生json对象
     * @return 转换后fastjson对象
     * @throws BltUtilException
     */
    private JSONObject checkOcrApiResp(org.json.JSONObject object) throws BltUtilException {
        JSONObject resp = (JSONObject)JSON.parse(object.toString());
        if(resp.containsKey("error_code")) {
            String errorCode = resp.getString("error_code");
            String errorMsg = resp.getString("error_msg");
            throw new BltUtilException(new ErrorInfo(errorCode, errorMsg));
        }
        return resp;
    }

    /**
     * 生成访问ocr接口时选项参数
     * @return 选项参数
     */
    private HashMap<String, String> generateOptionArg(){
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<>();
        options.put("language_type", "CHN_ENG");
        options.put("detect_direction", "true");
        options.put("detect_language", "true");
        options.put("probability", "true");
        return options;
    }

    /**
     * 转换证件orc接口返回对象
     * @param resp
     * @return map格式数据
     */
    private Map<String, Object> stripCardOcrApiResp(JSONObject resp){
        JSONObject idInfoObj = resp.getJSONObject("words_result");
        Map<String, Object> rtnMap = new LinkedHashMap<>();
        idInfoObj.keySet().stream().forEach(e ->
                rtnMap.put(e, idInfoObj.getJSONObject(e).getString("words")));
        return rtnMap;
    }
//
//    private Map<String, String> getIdCardKeyMap(int sideTyp){
//        Map<String, String> map = new HashMap<>();
//        if(ID_CARD_SIDE_FRONT == sideTyp) {
//            map.put("姓名", "name");
//            map.put("民族", "nationality");
//            map.put("住址", "address");
//            map.put("公民身份号码", "idNo");
//            map.put("出生", "birthday");
//            map.put("性别", "sex");
//        }else if(ID_CARD_SIDE_BACK == sideTyp) {
//            map.put("失效日期", "invalidDate");
//            map.put("签发机关", "issueOrg");
//            map.put("签发日期", "issueDate");
//        }
//        return map;
//    }

}
