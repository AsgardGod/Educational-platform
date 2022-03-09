package com.hurry.eduservice.client;

import com.hurry.commonutils.R;
import com.hurry.eduservice.client.impl.VodFileDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Hurry
 * @CreateTime: 2022/3/4
 * To change this template use File | Settings | File Templates.
 * Description:调用nacos注册中心注册的接口
 */
@FeignClient(name = "service-vod",fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {

    //@PathVariable一定要指定参数名称，否则会出错
    @DeleteMapping("/eduvod/video/removeVideo/{id}")
    public R deleteVideo(@PathVariable("id") String id);

    //删除多个视频的方法
    @DeleteMapping("/eduvod/video/delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);
}
