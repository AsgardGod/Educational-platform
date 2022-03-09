package com.hurry.educms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hurry.educms.entity.CrmBanner;
import com.hurry.educms.mapper.CrmBannerMapper;
import com.hurry.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author Hurry
 * @since 2022-03-05
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Override
    public CrmBanner getBannerById(String id) {
        return baseMapper.selectById(id);
    }

    @Override
    public void saveBanner(CrmBanner banner) {
        baseMapper.insert(banner);
    }

    @Override
    public void updateBannerById(CrmBanner banner) {
        baseMapper.updateById(banner);
    }

    @Cacheable(value = "banner",key = "'selectIndexList'")
    @Override
    public List<CrmBanner> getAllBanner() {
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        //根据id进行降序排序后再显示前两条记录
        wrapper.orderByDesc("id");
        wrapper.last("limit 2");
        return baseMapper.selectList(wrapper);
    }

    @Override
    public void removeBannerById(String id) {
        baseMapper.deleteById(id);
    }
}
