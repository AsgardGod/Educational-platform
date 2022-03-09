package com.hurry.vod.controller;

import com.hurry.commonutils.R;
import com.hurry.vod.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Hurry
 * @CreateTime: 2022/3/3
 * To change this template use File | Settings | File Templates.
 * Description:
 */
@RestController
@RequestMapping("eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VideoService videoService;

    @PostMapping("uploadVideo")
    public R uploadVideo(MultipartFile file){
        String videoId = videoService.uploadVideo(file);
        return R.ok().data("videoId",videoId);
    }

    @DeleteMapping("removeVideo/{id}")
    public R deleteVideo(@PathVariable String id){
        videoService.removeVideoById(id);
        return R.ok();
    }

    //删除多个视频的方法
    @DeleteMapping("delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList){
        videoService.removeMoreVideo(videoIdList);
        return R.ok();
    }

    //根据视频id获取视频播放凭证
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id) {
        String playAuth = videoService.getPlayAuth(id);
        return R.ok().data("playAuth",playAuth);
    }
}
