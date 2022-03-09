package com.hurry.msmservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hurry.commonutils.R;
import com.hurry.msmservice.service.MsmService;
import com.hurry.msmservice.utils.ConstantPropertiesUtils;
import com.hurry.msmservice.utils.RandomUtil;
import com.hurry.servicebase.exceptionhandler.GuliException;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;

//导入可选配置类
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;

// 导入对应SMS模块的client
import com.tencentcloudapi.sms.v20210111.SmsClient;

// 导入要请求接口对应的request response类
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20210111.models.SendStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Hurry
 * @CreateTime: 2022/3/5
 * To change this template use File | Settings | File Templates.
 * Description:
 */
@Service
public class MsmServiceImpl implements MsmService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public boolean sendMsm(String phone) {
        try {
            if (StringUtils.isEmpty(phone)) {
                return false;
            }
            String code = redisTemplate.opsForValue().get(phone);
            if (!StringUtils.isEmpty(code)){
                return true;
            }
            Credential cred = new Credential(ConstantPropertiesUtils.SECRET_ID,ConstantPropertiesUtils.SECRET_KEY);
            ClientProfile clientProfile = new ClientProfile();
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("sms.tencentcloudapi.com");
            clientProfile.setHttpProfile(httpProfile);
            SmsClient client = new SmsClient(cred, "ap-guangzhou", clientProfile);
            SendSmsRequest req = new SendSmsRequest();
            String[] phoneNumber = {"+86"+phone};
            String param = RandomUtil.getSixBitRandom();
            String[] templateParam = {param,ConstantPropertiesUtils.EXPIRE_TIME};
            req.setPhoneNumberSet(phoneNumber);
            req.setTemplateParamSet(templateParam);
            req.setSmsSdkAppId(ConstantPropertiesUtils.APP_ID);
            req.setTemplateId(ConstantPropertiesUtils.TEMPLATE_IDS);
            req.setSignName(ConstantPropertiesUtils.SMS_SIGN);
            SendSmsResponse sendSmsResponse = client.SendSms(req);
            SendStatus[] sendStatusSet = sendSmsResponse.getSendStatusSet();
            if (sendStatusSet[0].getCode().equals("Ok")){
                //将code放入缓存，并设置过期时间
                redisTemplate.opsForValue().set(phone,param,10, TimeUnit.MINUTES);
                return true;
            }else {
                return false;
            }
        }catch(TencentCloudSDKException  e) {
            e.printStackTrace();
            throw new GuliException(20001,"短信验证码发送失败");
        }
    }
}
