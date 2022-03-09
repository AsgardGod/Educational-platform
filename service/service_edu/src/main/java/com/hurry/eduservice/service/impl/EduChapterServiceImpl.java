package com.hurry.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hurry.eduservice.entity.EduChapter;
import com.hurry.eduservice.entity.EduCourseDescription;
import com.hurry.eduservice.entity.EduVideo;
import com.hurry.eduservice.entity.vo.ChapterVo;
import com.hurry.eduservice.entity.vo.CourseInfoVo;
import com.hurry.eduservice.entity.vo.VideoVo;
import com.hurry.eduservice.mapper.EduChapterMapper;
import com.hurry.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hurry.eduservice.service.EduCourseDescriptionService;
import com.hurry.eduservice.service.EduVideoService;
import com.hurry.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-02-27
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;

    @Override
    public List<ChapterVo> getChapterVideo(String courseId) {

        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<EduVideo> eduVideoList = videoService.list(wrapperVideo);

        List<ChapterVo> finaList = new ArrayList<>();
        for (EduChapter eduChapter : eduChapterList) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            List<VideoVo> videoVoList = new ArrayList<>();
            for (EduVideo eduVideo : eduVideoList) {
                VideoVo videoVo = new VideoVo();
                BeanUtils.copyProperties(eduVideo,videoVo);
                if (eduVideo.getChapterId().equals(eduChapter.getId())){
                    videoVoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVoList);
            finaList.add(chapterVo);
        }
        return finaList;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = videoService.count(wrapper);    //根据章节id查询小节
        if (count > 0){     //若查询到内容，则不删除
            throw new GuliException(20001,"章节中有内容，无法删除");
        }else {
            int result = baseMapper.deleteById(chapterId);
            //result>0，则说明已经被删除
            return result>0;
        }
    }

    @Override
    public void deleteCourseById(String id) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        baseMapper.delete(wrapper);
    }

}
