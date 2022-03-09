package com.hurry.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Hurry
 * @CreateTime: 2022/2/27
 * To change this template use File | Settings | File Templates.
 * Description:
 */
@Data
public class OneSubject {

    private String id;
    private String title;
    //名称必需与前端对应，前端为children，后端也必须为children，否则不会显示
    List<TwoSubject> children = new ArrayList<>();
}
