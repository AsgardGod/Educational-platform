package com.hurry.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Hurry
 * @CreateTime: 2022/2/26
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public interface OssService {
    String uploadFileAvatar(MultipartFile file);
}
