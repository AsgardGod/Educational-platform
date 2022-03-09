package com.hurry.eduservice.client.impl;

import com.hurry.commonutils.R;
import com.hurry.eduservice.client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Hurry
 * @CreateTime: 2022/3/4
 * To change this template use File | Settings | File Templates.
 * Description:
 */
@Component
public class VodFileDegradeFeignClient implements VodClient {
    //出错熔断之后会执行
    @Override
    public R deleteVideo(String id) {
        return R.error().message("删除单个视频出错");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("删除多个视频出错");
    }
}
