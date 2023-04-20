package com.atguigu.common.oss;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;

import java.util.HashMap;
import java.util.Map;

public class StsService {
    // （endpoint）STS接入地址，例如sts.cn-hangzhou.aliyuncs.com。
    private static String endpoint = "sts.cn-chengdu.aliyuncs.com";
    // 填写步骤1生成的RAM用户访问密钥AccessKey ID和AccessKey Secret。
    private static String accessKeyId = "LTAI5t8CZ6NTrfxVqoAAraKF";
    private static String accessKeySecret = "ARwjWfgOTTihpaqTkcL4uXmc8ooPu3";
    // 填写步骤3获取的角色ARN。
    private static String roleArn = "acs:ram::1542896096464390:role/ramosstest";
    //  自定义角色会话名称，用来区分不同的令牌，例如可填写为SessionTest。
    private static String roleSessionName = "RamOssTest";
    // 设置临时访问凭证的有效时间为3600秒。
    private static Long durationSeconds = 3600L;

    public static Map<String,String> getkey(){
        try {
            // regionId表示RAM的地域ID。以华东1（杭州）地域为例，regionID填写为cn-hangzhou。也可以保留默认值，默认值为空字符串（""）。
            String regionId = "cn-chengdu";
            // 添加endpoint。适用于Java SDK 3.12.0及以上版本。
            DefaultProfile.addEndpoint(regionId, "Sts", endpoint);
            // 添加endpoint。适用于Java SDK 3.12.0以下版本。
            // DefaultProfile.addEndpoint("",regionId, "Sts", endpoint);
            // 构造default profile。
            IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
            // 构造client。
            DefaultAcsClient client = new DefaultAcsClient(profile);
            final AssumeRoleRequest request = new AssumeRoleRequest();
            // 适用于Java SDK 3.12.0及以上版本。
            request.setSysMethod(MethodType.POST);
            // 适用于Java SDK 3.12.0以下版本。
            //request.setMethod(MethodType.POST);
            request.setRoleArn(roleArn);
            request.setRoleSessionName(roleSessionName);
            //request.setPolicy(policy);
            request.setDurationSeconds(durationSeconds);
            final AssumeRoleResponse response = client.getAcsResponse(request);
            Map<String,String> map = new HashMap<>();
            //放入map中返回
            map.put("accessKeyId", response.getCredentials().getAccessKeyId());
            map.put("accessKeySecret", response.getCredentials().getAccessKeySecret());
            map.put("securityToken",response.getCredentials().getSecurityToken());
            map.put("RequestId",response.getRequestId());
            System.out.println("Expiration: " + response.getCredentials().getExpiration());
            System.out.println("Access Key Id: " + response.getCredentials().getAccessKeyId());
            System.out.println("Access Key Secret: " + response.getCredentials().getAccessKeySecret());
            System.out.println("Security Token: " + response.getCredentials().getSecurityToken());
            System.out.println("RequestId: " + response.getRequestId());
            return map;
        } catch (ClientException e) {
            System.out.println("Failed：");
            System.out.println("Error code: " + e.getErrCode());
            System.out.println("Error message: " + e.getErrMsg());
            System.out.println("RequestId: " + e.getRequestId());
        }
        return null;
    }
    public static void main(String[] args) {
       /* // STS接入地址，例如sts.cn-hangzhou.aliyuncs.com。
        String endpoint = "sts.cn-chengdu.aliyuncs.com";
        // 填写步骤1生成的RAM用户访问密钥AccessKey ID和AccessKey Secret。
        String accessKeyId = "LTAI5t8CZ6NTrfxVqoAAraKF";
        String accessKeySecret = "ARwjWfgOTTihpaqTkcL4uXmc8ooPu3";
        // 填写步骤3获取的角色ARN。
        String roleArn = "acs:ram::1542896096464390:role/ramosstest";
        // 自定义角色会话名称，用来区分不同的令牌，例如可填写为SessionTest。
        String roleSessionName = "RamOssTest";
        // 以下Policy用于限制仅允许使用临时访问凭证向目标存储空间examplebucket上传文件。
        // 临时访问凭证最后获得的权限是步骤4设置的角色权限和该Policy设置权限的交集，即仅允许将文件上传至目标存储空间examplebucket下的exampledir目录。
        // 如果policy为空，则用户将获得该角色下所有权限。
        String policy = "{\n" +
                "    \"Version\": \"1\", \n" +
                "    \"Statement\": [\n" +
                "        {\n" +
                "            \"Action\": [\n" +
                "                \"oss:PutObject\"\n" +
                "            ], \n" +
                "            \"Resource\": [\n" +
                "                \"acs:oss:*:*:examplebucket/*\" \n" +
                "            ], \n" +
                "            \"Effect\": \"Allow\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";*/

        /*try {
            // regionId表示RAM的地域ID。以华东1（杭州）地域为例，regionID填写为cn-hangzhou。也可以保留默认值，默认值为空字符串（""）。
            String regionId = "cn-chengdu";
            // 添加endpoint。适用于Java SDK 3.12.0及以上版本。
            DefaultProfile.addEndpoint(regionId, "Sts", endpoint);
            // 添加endpoint。适用于Java SDK 3.12.0以下版本。
            // DefaultProfile.addEndpoint("",regionId, "Sts", endpoint);
            // 构造default profile。
            IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
            // 构造client。
            DefaultAcsClient client = new DefaultAcsClient(profile);
            final AssumeRoleRequest request = new AssumeRoleRequest();
            // 适用于Java SDK 3.12.0及以上版本。
            request.setSysMethod(MethodType.POST);
            // 适用于Java SDK 3.12.0以下版本。
            //request.setMethod(MethodType.POST);
            request.setRoleArn(roleArn);
            request.setRoleSessionName(roleSessionName);
            //request.setPolicy(policy);
            request.setDurationSeconds(durationSeconds);
            final AssumeRoleResponse response = client.getAcsResponse(request);
            Map<String,String> map = new HashMap<>();
            //放入map中返回
            map.put("Access Key Id", response.getCredentials().getAccessKeyId());
            map.put("Access Key Secret", response.getCredentials().getAccessKeySecret());
            map.put("Security Token",response.getCredentials().getSecurityToken());
            map.put("RequestId",response.getRequestId());
            System.out.println("Expiration: " + response.getCredentials().getExpiration());
            System.out.println("Access Key Id: " + response.getCredentials().getAccessKeyId());
            System.out.println("Access Key Secret: " + response.getCredentials().getAccessKeySecret());
            System.out.println("Security Token: " + response.getCredentials().getSecurityToken());
            System.out.println("RequestId: " + response.getRequestId());
        } catch (ClientException e) {
            System.out.println("Failed：");
            System.out.println("Error code: " + e.getErrCode());
            System.out.println("Error message: " + e.getErrMsg());
            System.out.println("RequestId: " + e.getRequestId());
        }*/

        System.out.println(getkey());
    }
}
