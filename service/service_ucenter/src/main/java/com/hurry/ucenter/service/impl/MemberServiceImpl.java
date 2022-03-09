package com.hurry.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hurry.commonutils.JwtUtils;
import com.hurry.commonutils.MD5;
import com.hurry.servicebase.exceptionhandler.GuliException;
import com.hurry.ucenter.entity.Member;
import com.hurry.ucenter.entity.vo.RegisterVo;
import com.hurry.ucenter.mapper.MemberMapper;
import com.hurry.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author Hurry
 * @since 2022-03-06
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public String login(Member member) {
        String mobile = member.getMobile();
        String password = member.getPassword();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001,"手机号或密码为空");
        }
        //根据手机号查询数据
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Member memberMobile = baseMapper.selectOne(wrapper);
        if (memberMobile == null) {     //手机号为空
            throw new GuliException(20001,"手机号输入错误");
        }
        if (!MD5.encrypt(password).equals(memberMobile.getPassword())) {     //密码不正确
            throw new GuliException(20001,"密码输入错误");
        }
        if (memberMobile.getIsDisabled()) {     //是否被禁用
            throw new GuliException(20001,"账号被禁用");
        }
        //使用工具类生成token
        return JwtUtils.getJwtToken(memberMobile.getId(), memberMobile.getNickname());
    }

    @Override
    public void register(RegisterVo registerVo) {
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();

        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)
            || StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname)) {
            throw new GuliException(20001,"数据不能为空");
        }
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer integer = baseMapper.selectCount(wrapper);

        if (integer > 0) {
            throw new GuliException(20001,"改手机号已注册！");
        }
        String varCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(varCode)) {
            throw new GuliException(20001,"验证码错误！");
        }

        Member member = new Member();
        //BeanUtils.copyProperties(registerVo,member);
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        baseMapper.insert(member);

    }

    @Override
    public Member getMemberInfo(HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        return baseMapper.selectById(memberId);
    }
}
