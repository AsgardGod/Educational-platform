package com.hurry.eduservice.entity.vo;

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
public class ChapterVo {

    private String id;
    private String title;

    List<VideoVo> children = new ArrayList<>();
}
