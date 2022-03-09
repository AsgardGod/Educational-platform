package com.hurry.educms.controller;


import com.hurry.commonutils.R;
import com.hurry.educms.entity.CrmBanner;
import com.hurry.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author Hurry
 * @since 2022-03-05
 */
@RestController
@RequestMapping("/cmsservice/bannerFront")
@CrossOrigin
public class BannerFrontController {

    @Autowired
    private CrmBannerService bannerService;

    @GetMapping("/getAllBanner")
    public R getAllBanner(){
        List<CrmBanner> bannerList = bannerService.getAllBanner();
        return R.ok().data("list",bannerList);
    }


}

