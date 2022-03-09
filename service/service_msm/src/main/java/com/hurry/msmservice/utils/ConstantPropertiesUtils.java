package com.hurry.msmservice.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Hurry
 * @CreateTime: 2022/2/26
 * To change this template use File | Settings | File Templates.
 * Description:常量类，读取配置文件application.properties中的配置
 * 在这个类中实现一个接口，当spring加载之后，会执行接口中的方法
 */
@Component
public class ConstantPropertiesUtils implements InitializingBean {

    /** 腾讯云账户密钥对secretId（在访问管理中配置） */
    @Value("${sms-config.secretId}")
    private String secretId;

    /** 腾讯云账户密钥对secretKey（在访问管理中配置） */
    @Value("${sms-config.secretKey}")
    private String secretKey;

    /** 短信应用appId */
    @Value("${sms-config.appId}")
    private String appId;

    /** 短信应用appKey */
    @Value("${sms-config.appKey}")
    private String appKey;

    /** 签名 */
    @Value("${sms-config.smsSign}")
    private String smsSign;

    /** 过期时间 */
    @Value("${sms-config.expireTime}")
    private String expireTime;

    /** 模板id */
    @Value("${sms-config.templateIds.code}")
    private String templateIds;


    public static String SECRET_ID;
    public static String SECRET_KEY;
    public static String APP_ID;
    public static String APP_KEY;
    public static String SMS_SIGN;
    public static String EXPIRE_TIME;
    public static String TEMPLATE_IDS;

    @Override
    public void afterPropertiesSet() throws Exception {
        SECRET_ID = secretId;
        SECRET_KEY = secretKey;
        APP_ID = appId;
        APP_KEY = appKey;
        SMS_SIGN = smsSign;
        EXPIRE_TIME = expireTime;
        TEMPLATE_IDS = templateIds;
    }
}
