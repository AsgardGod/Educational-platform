package com.hurry.eduservice.service;

import com.hurry.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hurry.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-02-26
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file,EduSubjectService subjectService);

    List<OneSubject> getAllSubject();
}
