package com.hurry.oss.controller;

import com.hurry.commonutils.R;
import com.hurry.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Hurry
 * @CreateTime: 2022/2/26
 * To change this template use File | Settings | File Templates.
 * Description:
 */
@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping
    public R upload(MultipartFile file){

        String url = ossService.uploadFileAvatar(file);
        return R.ok().data("url",url);
    }
}
