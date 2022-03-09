package com.hurry.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hurry.commonutils.R;
import com.hurry.eduservice.entity.EduTeacher;
import com.hurry.eduservice.entity.vo.TeacherQuery;
import com.hurry.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-02-20
 */
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {
    //查询讲师表中的所有数据
    @Autowired
    private EduTeacherService service;

    @GetMapping("findAll")
    public R findAll(){
        List<EduTeacher> list = service.list(null);
        return R.ok().data("data",list);
    }

    //逻辑删除讲师
    @DeleteMapping("{id}")
    public R removeTeacher(@PathVariable String id){
        boolean flag = service.removeById(id);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //讲师列表分页
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageTeacher(@PathVariable long current,    //当前页
                         @PathVariable long limit){     //每页个数
        //调用方法时，他会将所有数据都封装到pageTeacher对象里
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        service.page(pageTeacher,null);
        long total = pageTeacher.getTotal();    //数据条数
        List<EduTeacher> records = pageTeacher.getRecords();    //数据list集合
//        Map map = new HashMap();
//        map.put("total",total);
//        map.put("rows",records);
//        return R.ok().data(map);
        return R.ok().data("total",total).data("rows",records);
    }

    //讲师列表条件查询
    // @RequestBody(required = false)注解是使用JSON格式传递数据，并将其封装到指定对象中，
    // 需要和@PostMapping搭配之用，并且传递的至不能为空，需要required设置为false，才可以传递空值
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if(!StringUtils.isEmpty(name)) {
            //构建条件
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)) {
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create",end);
        }
        //排序功能，每次新添加的数据在最前边显示
        wrapper.orderByDesc("gmt_create");
        service.page(pageTeacher,wrapper);
        long total = pageTeacher.getTotal();    //数据条数
        List<EduTeacher> records = pageTeacher.getRecords();    //数据list集合
        return R.ok().data("total",total).data("rows",records);
    }
    //添加讲师
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean flag = service.save(eduTeacher);
        return flag ? R.ok() : R.error();
    }
    //根据id获得讲师信息
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id){
        EduTeacher eduTeacher = service.getById(id);
        if (eduTeacher != null){
            return R.ok().data("teacher",eduTeacher);
        }else {
            return R.error();
        }
    }
    //根据id修改讲师信息
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean flag = service.updateById(eduTeacher);
        return flag ? R.ok() : R.error();
    }
}

