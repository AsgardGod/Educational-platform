package com.hurry.eduservice.service.impl;

import com.hurry.eduservice.entity.EduChapter;
import com.hurry.eduservice.entity.EduCourse;
import com.hurry.eduservice.entity.EduCourseDescription;
import com.hurry.eduservice.entity.EduVideo;
import com.hurry.eduservice.entity.vo.CourseInfoVo;
import com.hurry.eduservice.entity.vo.CoursePublishVo;
import com.hurry.eduservice.mapper.EduCourseMapper;
import com.hurry.eduservice.service.EduChapterService;
import com.hurry.eduservice.service.EduCourseDescriptionService;
import com.hurry.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hurry.eduservice.service.EduVideoService;
import com.hurry.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-02-27
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduVideoService eduVideoService;


    @Autowired
    private EduChapterService eduChapterService;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {

        //向课程表中添加数据
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert == 0){
            throw new GuliException(20001,"添加课程失败");
        }
        //获取添加之后的课程表的id值，作为描述标的id值，使其互相关联
        String cid = eduCourse.getId();

        //向课程描述表中添加数据
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo,eduCourseDescription);
        //手动设置id值，为课程表的id值
        eduCourseDescription.setId(cid);
        eduCourseDescriptionService.save(eduCourseDescription);

        return cid;
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        EduCourse eduCourse  = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);
        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if (update == 0){
            throw new GuliException(20001,"修改课程表失败！");
        }
        EduCourseDescription courseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo,courseDescription);
        eduCourseDescriptionService.updateById(courseDescription);

    }

    @Override
    public CoursePublishVo getPublishCourse(String courseId) {
        return baseMapper.getPublishCourseInfo(courseId);
    }

    @Override
    public void publishCourse(String id) {
        EduCourse course = new EduCourse();
        course.setId(id);
        course.setStatus("Normal");
        baseMapper.updateById(course);
    }

    @Override
    public void deleteCourse(String id) {

        //删除小节
        eduVideoService.deleteCourseById(id);
        //删除章节
        eduChapterService.deleteCourseById(id);
        //删除描述
        eduCourseDescriptionService.removeById(id);
        //删除课程本身
        int i = baseMapper.deleteById(id);
        if (i == 0){
            throw new GuliException(20001,"删除课程失败");
        }


    }
}
