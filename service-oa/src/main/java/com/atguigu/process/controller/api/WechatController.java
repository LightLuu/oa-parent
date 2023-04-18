package com.atguigu.process.controller.api;

import com.alibaba.fastjson.JSON;
import com.atguigu.auth.service.SysUserService;
import com.atguigu.common.jwt.JwtHelper;
import com.atguigu.common.result.Result;
import com.atguigu.model.system.SysUser;
import com.atguigu.vo.wechat.BindPhoneVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequestMapping("/admin/wechat")
@CrossOrigin //跨域
public class WechatController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private WxMpService wxMpService;

    @Value("${wechat.userInfoUrl}")
    private String userInfoUrl;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl,
                            HttpServletRequest request) {
        //buildAuthorizationUrl三个参数
        //第一个参数：授权路径，在哪个路径获取微信信息
        //第二个参数：固定值，授权类型 WxConsts.OAuth2Scope.SNSAPI_USERINFO
        //第三个参数：授权成功之后，跳转路径  'guiguoa' 转换成  '#'
        String redirectUrl = null;
        System.out.println(returnUrl);
        try {
            redirectUrl = wxMpService.getOAuth2Service()
                    .buildAuthorizationUrl(userInfoUrl,
                            WxConsts.OAuth2Scope.SNSAPI_USERINFO,
                            URLEncoder.encode(returnUrl.replace("guiguoa", "#"),"utf-8"));
            System.out.println(redirectUrl);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
       //redirectUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx1ea5b7be5284ca5c&redirect_uri=http%3A%2F%2Fafraid.5gzvip.91tunnel.com%2Fadmin%2Fwechat%2FuserInfo&response_type=code&scope=snsapi_userinfo&state=http%3A%2F%2Fafraid.5gzvip.91tunnel.com%2F%23%2F&connect_redirect=1#wechat_redirect ";
        return "redirect:" + redirectUrl;
    }

    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,
                           @RequestParam("state") String returnUrl) throws Exception {
        //获取accessToken
        /*try {
            WxOAuth2AccessToken accessToken = wxMpService.getOAuth2Service().getAccessToken(code);//使用accessToken获取openId
            String openId = accessToken.getOpenId();
            System.out.println("openId: "+openId);

            //获取微信用户信息
            WxOAuth2UserInfo wxMpUser = wxMpService.getOAuth2Service().getUserInfo(accessToken, null);
            System.out.println("微信用户信息: "+JSON.toJSONString(wxMpUser));

            //根据openid查询用户表
            LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysUser::getOpenId,openId);
            SysUser sysUser = sysUserService.getOne(wrapper);
            String token = "";
            //判断openid是否存在
            if(sysUser != null) {
                token = JwtHelper.createToken(sysUser.getId(),sysUser.getUsername());
            }
            if(returnUrl.indexOf("?") == -1) {
                return "redirect:" + returnUrl + "?token=" + token + "&openId=" + openId;
            } else {
                return "redirect:" + returnUrl + "&token=" + token + "&openId=" + openId;
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;*/

        System.out.println("yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
        //查看redis中是否有这个code对应的accessToken
        String access = (String) redisTemplate.opsForValue().get(code);
        WxOAuth2AccessToken accessToken = null;
        if(access == null){
            accessToken = wxMpService.getOAuth2Service().getAccessToken(code);//使用accessToken获取openId
            //保存accessToken
            redisTemplate.opsForValue().set(code, JSON.toJSONString(accessToken));
        }else {
            accessToken = (WxOAuth2AccessToken)JSON.parseObject(access,WxOAuth2AccessToken.class);
        }


        String openId = accessToken.getOpenId();
        System.out.println("openId: "+openId);

        //获取微信用户信息
        WxOAuth2UserInfo wxMpUser = wxMpService.getOAuth2Service().getUserInfo(accessToken, null);
        System.out.println("微信用户信息: "+JSON.toJSONString(wxMpUser));

        //根据openid查询用户表
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getOpenId,openId);
        SysUser sysUser = sysUserService.getOne(wrapper);
        String token = "";
        //判断openid是否存在
        if(sysUser != null) {
            token = JwtHelper.createToken(sysUser.getId(),sysUser.getUsername());
        }
        if(returnUrl.indexOf("?") == -1) {
            System.out.println();
            return "redirect:" + returnUrl + "?token=" + token + "&openId=" + openId;

        } else {
           // return null;
            System.out.println();
            return "redirect:" + returnUrl + "&token=" + token + "&openId=" + openId;

        }
    }

    @PostMapping("/bindPhone")
    @ResponseBody
    public Result bindPhone(@RequestBody BindPhoneVo bindPhoneVo) {
        //1 根据手机号查询数据库
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getPhone,bindPhoneVo.getPhone());
        SysUser sysUser = sysUserService.getOne(wrapper);

        //2 如果存在，更新记录 openid
        if(sysUser != null) {
            sysUser.setOpenId(bindPhoneVo.getOpenId());
            sysUserService.updateById(sysUser);

            String token = JwtHelper.createToken(sysUser.getId(),sysUser.getUsername());
            return Result.ok(token);
        } else {
            return Result.fail("手机号不存在，请联系管理员修改");
        }
    }
}
