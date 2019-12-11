package com.easyim.web.controller.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.easyim.common.constant.UserConstants;
import com.easyim.framework.shiro.service.SysPasswordService;
import com.easyim.framework.util.ShiroUtils;
import com.easyim.system.domain.SysUser;
import com.easyim.system.service.ISysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.easyim.common.core.controller.BaseController;
import com.easyim.common.core.domain.AjaxResult;
import com.easyim.common.utils.ServletUtils;
import com.easyim.common.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录验证
 * 
 * @author emessage
 */
@Controller
public class SysLoginController extends BaseController {
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private SysPasswordService passwordService;

    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        // 如果是Ajax请求，返回Json字符串。
        if (ServletUtils.isAjaxRequest(request))
        {
            return ServletUtils.renderString(response, "{\"code\":\"1\",\"msg\":\"未登录或登录超时。请重新登录\"}");
        }

        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public AjaxResult ajaxLogin(String username, String password, Boolean rememberMe) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        Subject subject = SecurityUtils.getSubject();
        try
        {
            subject.login(token);
            return success();
        }
        catch (AuthenticationException e)
        {
            String msg = "用户或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage()))
            {
                msg = e.getMessage();
            }
            return error(msg);
        }
    }

    @PostMapping("/register")
    @ResponseBody
    public AjaxResult ajaxRegister(String username, String phone,String password, String email) {
        SysUser sysUser = new SysUser();
        sysUser.setUserName(username);
        sysUser.setLoginName(username);
        sysUser.setState(0);
        sysUser.setPhonenumber(phone);
        sysUser.setUserId(StringUtils.getUUID(Boolean.TRUE));
        sysUser.setEmail(email);
        if (UserConstants.USER_NAME_NOT_UNIQUE.equals(sysUserService.checkLoginNameUnique(username)))
        {
            return error("新增用户'" + username + "'失败，登录账号已存在");
        }
        else if (UserConstants.USER_EMAIL_NOT_UNIQUE.equals(sysUserService.checkEmailUnique(sysUser)))
        {
            return error("新增用户'" + username + "'失败，邮箱账号已存在");
        }
        sysUser.setSalt(ShiroUtils.randomSalt());
        sysUser.setPassword(passwordService.encryptPassword(username, password, sysUser.getSalt()));
        sysUser.setCreateBy(username);
        return toAjax(sysUserService.registerUser(sysUser));
    }


    @GetMapping("/registerSuccess")
    public String registerSuccess(HttpServletRequest request, HttpServletResponse response) {
        return "registerSuccess";
    }

    @GetMapping("/unauth")
    public String unauth()
    {
        return "error/unauth";
    }
}
