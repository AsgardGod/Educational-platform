package com.hurry.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hurry.commonutils.R;
import com.hurry.eduservice.entity.EduCourse;
import com.hurry.eduservice.entity.vo.CourseInfoVo;
import com.hurry.eduservice.entity.vo.CoursePublishVo;
import com.hurry.eduservice.entity.vo.CourseQuery;
import com.hurry.eduservice.service.EduCourseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-02-27
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    //获取全部课程列表
    @GetMapping("getAll")
    public R getAll(){
        List<EduCourse> list = courseService.list(null);
        return R.ok().data("list",list);
    }
    //课程列表的分页查询
    @GetMapping("pageCourse/{current}/{limit}")
    public R pageCourse(@PathVariable long current,@PathVariable long limit){
        Page<EduCourse> pageCourse = new Page<>(current,limit);
        courseService.page(pageCourse, null);
        List<EduCourse> records = pageCourse.getRecords();
        long total = pageCourse.getTotal();
        return R.ok().data("total",total).data("rows",records);
    }
    //课程的条件搜索列表
    @PostMapping("getCoursePageCondition/{current}/{limit}")
    public R getCoursePageCondition(@PathVariable long current, @PathVariable long limit,
                                    @RequestBody CourseQuery courseQuery){
        Page<EduCourse> pageCourse = new Page<>(current,limit);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();
        if (!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }
        if (!StringUtils.isEmpty(status)){
            wrapper.like("status",status);
        }
        wrapper.orderByDesc("gmt_create");
        courseService.page(pageCourse,wrapper);
        long total = pageCourse.getTotal();
        List<EduCourse> records = pageCourse.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    @PostMapping("addCourse")
    public R addCourse(@RequestBody CourseInfoVo courseInfoVo){
        //本次返回课程id，为后边的添加大纲使用
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",id);
    }
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }

    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }
    @ApiOperation(value = "根据ID获取课程发布信息")
    @GetMapping("getPublishCourse/{courseId}")
    public R getPublishCourse(@PathVariable String courseId){
        CoursePublishVo coursePublishVo = courseService.getPublishCourse(courseId);
        return R.ok().data("coursePublishVo",coursePublishVo);
    }

    @GetMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        courseService.publishCourse(id);
        return R.ok();
    }

    @DeleteMapping("{id}")
    public R deleteCourse(@PathVariable String id){
        courseService.deleteCourse(id);
        return R.ok();
    }
}

