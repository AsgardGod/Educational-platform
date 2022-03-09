package com.hurry.eduservice.service;

import com.hurry.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hurry.eduservice.entity.vo.CourseInfoVo;
import com.hurry.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-02-27
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo getPublishCourse(String courseId);

    void publishCourse(String id);

    void deleteCourse(String id);
}
