package com.hurry.eduservice.service;

import com.hurry.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hurry.eduservice.entity.vo.ChapterVo;
import com.hurry.eduservice.entity.vo.CourseInfoVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-02-27
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideo(String courseId);

    boolean deleteChapter(String chapterId);

    void deleteCourseById(String id);
}
