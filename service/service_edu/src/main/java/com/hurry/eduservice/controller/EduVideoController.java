package com.hurry.eduservice.controller;


import com.hurry.commonutils.R;
import com.hurry.eduservice.client.VodClient;
import com.hurry.eduservice.entity.EduVideo;
import com.hurry.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-02-27
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private VodClient vodClient;    //远程调用删除视频的接口

    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return R.ok();
    }
    //更新小节信息
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        videoService.updateById(eduVideo);
        return R.ok();
    }
    //根据id删除小节
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id){
        //根据小节id获取小节信息
        EduVideo eduVideo = videoService.getById(id);
        //从小节中获取视频id
        String videoSourceId = eduVideo.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)){
            vodClient.deleteVideo(videoSourceId);   //远程调用Vod模块的删除阿里云视频的方法
        }
        videoService.removeById(id);
        return R.ok();
    }
    //根据id获取小节信息
    @GetMapping("{id}")
    public R getVideoInfo(@PathVariable String id){
        EduVideo eduVideo = videoService.getById(id);
        return R.ok().data("video",eduVideo);
    }
}

