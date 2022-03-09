package com.hurry.eduservice.controller;


import com.hurry.commonutils.R;
import com.hurry.eduservice.entity.EduSubject;
import com.hurry.eduservice.entity.subject.OneSubject;
import com.hurry.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-02-26
 */
@Api(description="课程分类管理")
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        subjectService.saveSubject(file,subjectService);
        return R.ok();
    }

    @GetMapping("getAllSubject")
    public R getSubject(){
        List<OneSubject> allSubjectList = subjectService.getAllSubject();
        return R.ok().data("list",allSubjectList);
    }
}

