package com.hurry.ucenter.service;

import com.hurry.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hurry.ucenter.entity.vo.RegisterVo;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author Hurry
 * @since 2022-03-06
 */
public interface MemberService extends IService<Member> {

    String login(Member member);

    void register(RegisterVo registerVo);

    Member getMemberInfo(HttpServletRequest request);
}
