package com.hurry.eduservice.controller;

import com.hurry.commonutils.R;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Hurry
 * @CreateTime: 2022/2/23
 * To change this template use File | Settings | File Templates.
 * Description:
 */
@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin    //解决跨域问题
public class EduLoginController {

    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }

    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
