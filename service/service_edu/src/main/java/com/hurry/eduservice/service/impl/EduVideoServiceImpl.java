package com.hurry.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hurry.eduservice.client.VodClient;
import com.hurry.eduservice.entity.EduVideo;
import com.hurry.eduservice.mapper.EduVideoMapper;
import com.hurry.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.management.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-02-27
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    @Override
    public void deleteCourseById(String id) {
        //先删除阿里云的视频
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",id);
        wrapperVideo.select("video_source_id");
        List<EduVideo> eduVideos = baseMapper.selectList(wrapperVideo);
        List<String> videoIds = new ArrayList<>();
        for (int i = 0; i< eduVideos.size(); i++) {
            EduVideo eduVideo = eduVideos.get(i);
            String videoId = eduVideo.getVideoSourceId();
            if (!StringUtils.isEmpty(videoId)){
                videoIds.add(videoId);
            }
        }
        if (videoIds.size() > 0){
            vodClient.deleteBatch(videoIds);
        }
        //再删除小节内容
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        baseMapper.delete(wrapper);
    }
}
