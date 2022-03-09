package com.hurry.ucenter.controller;


import com.hurry.commonutils.JwtUtils;
import com.hurry.commonutils.R;
import com.hurry.ucenter.entity.Member;
import com.hurry.ucenter.entity.vo.RegisterVo;
import com.hurry.ucenter.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author Hurry
 * @since 2022-03-06
 */
@RestController
@RequestMapping("/ucenterservice/member")
@CrossOrigin
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("login")       //登录
    public R login(@RequestBody Member member) {
        String token = memberService.login(member);
        return R.ok().data("token",token);
    }

    @PostMapping("register")    //注册
    public R register(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return R.ok();
    }
    //根据token获取用户信息
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request) {
        Member member = memberService.getMemberInfo(request);
        return R.ok().data("userInfo",member);

    }

}

