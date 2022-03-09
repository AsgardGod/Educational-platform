package com.hurry.educms.service;

import com.hurry.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author Hurry
 * @since 2022-03-05
 */
public interface CrmBannerService extends IService<CrmBanner> {

    CrmBanner getBannerById(String id);

    void saveBanner(CrmBanner banner);

    void updateBannerById(CrmBanner banner);

    void removeBannerById(String id);

    List<CrmBanner> getAllBanner();
}
