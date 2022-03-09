package com.hurry.eduservice.service;

import com.hurry.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-02-27
 */
public interface EduVideoService extends IService<EduVideo> {

    void deleteCourseById(String id);
}
