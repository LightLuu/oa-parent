// This file is auto-generated, don't edit it. Thanks.
package com.atguigu.common.oss;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.ocr_api20210707.models.RecognizeBasicRequest;
import com.aliyun.ocr_api20210707.models.RecognizeBasicResponse;
import com.aliyun.oss.OSS;
import com.aliyun.tea.*;
import com.aliyun.teautil.models.RuntimeOptions;
import com.atguigu.common.oss.entity.OcrResult;
import com.google.gson.Gson;
import com.sun.deploy.util.IconEncoder;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

@Component
public class Sample {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Resource
    private  com.aliyun.ocr_api20210707.Client ocrClient;
    /**
     * 使用AK&SK初始化账号Client
     * @param accessKeyId
     * @param accessKeySecret
     * @return Client
     * @throws Exception
     */
    public static com.aliyun.ocr_api20210707.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                // 必填，您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 必填，您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "ocr-api.cn-hangzhou.aliyuncs.com";
        return new com.aliyun.ocr_api20210707.Client(config);
    }

    public void getocr(){
       // com.aliyun.ocr_api20210707.models.RecognizeBasicRequest recognizeBasicRequest = ocrClient.models.
    }

    public static void main(String[] args_) throws Exception {
        java.util.List<String> args = java.util.Arrays.asList(args_);
        // 工程代码泄露可能会导致AccessKey泄露，并威胁账号下所有资源的安全性。以下代码示例仅供参考，建议使用更安全的 STS 方式，更多鉴权访问方式请参见：https://help.aliyun.com/document_detail/378657.html
        com.aliyun.ocr_api20210707.Client client = Sample.createClient("LTAI5t8CZ6NTrfxVqoAAraKF", "ARwjWfgOTTihpaqTkcL4uXmc8ooPu3");
        com.aliyun.ocr_api20210707.models.RecognizeBasicRequest recognizeBasicRequest = new RecognizeBasicRequest()
                .setUrl("https://filemanage-afraid.oss-cn-chengdu.aliyuncs.com/images/2023/04/27/16825742953617602.png?Expires=1682627954&OSSAccessKeyId=TMP.3KfurnL5t8GfjYR5SfdSdbfGSGHQs3tPfVzR1y8Y8U7Xwu4bwXAdaxLEV7y3nEXTsxkBQZ3U9r8GetsiRT7pNqqLJAQL3R&Signature=LK%2BaFukx%2BZQfJ25ZdCv2JyczXLQ%3D");
        try {
            // 复制代码运行请自行打印 API 的返回值
            RecognizeBasicResponse recognizeBasicResponse = client.recognizeBasicWithOptions(recognizeBasicRequest, new RuntimeOptions());
            System.out.println(recognizeBasicResponse.getBody().getData());
            //code
            if(200 == recognizeBasicResponse.getStatusCode()){
                JSONObject jsonObject = JSON.parseObject(recognizeBasicResponse.getBody().getData());
            }
        } catch (Exception error) {
            // 如有需要，请打印 error
            error.printStackTrace();
       }
    }
}
