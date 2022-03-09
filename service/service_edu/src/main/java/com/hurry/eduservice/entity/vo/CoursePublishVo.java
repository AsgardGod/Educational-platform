package com.hurry.eduservice.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Hurry
 * @CreateTime: 2022/3/1
 * To change this template use File | Settings | File Templates.
 * Description:
 */
@Data
public class CoursePublishVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;//只用于显示
}
