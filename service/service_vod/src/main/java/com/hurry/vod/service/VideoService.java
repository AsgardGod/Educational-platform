package com.hurry.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Hurry
 * @CreateTime: 2022/3/3
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public interface VideoService {
    String uploadVideo(MultipartFile file);

    void removeVideoById(String id);

    void removeMoreVideo(List videoIdList);

    String getPlayAuth(String id);
}
