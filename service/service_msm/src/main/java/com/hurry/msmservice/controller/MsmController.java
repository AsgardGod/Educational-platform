package com.hurry.msmservice.controller;

import com.hurry.commonutils.R;
import com.hurry.msmservice.service.MsmService;
import com.hurry.msmservice.utils.RandomUtil;
import com.tencentcloudapi.sms.v20210111.models.SendStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
@RestController
@RequestMapping("edumsm/msm")
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;

    @GetMapping("sendMsm/{phone}")
    public R sendMsm(@PathVariable String phone) {
        boolean isSend = msmService.sendMsm(phone);
        if (isSend){
            return R.ok();
        }else {
            return R.error().message("验证码发送失败");
        }
    }

}
