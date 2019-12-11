package com.easyim.framework.shiro.realm;

import java.util.*;

import com.easyim.common.config.FastDFSConfig;
import com.easyim.common.config.MessageConfig;
import com.easyim.common.fastdfs.client.FastDFSClient;
import com.easyim.common.utils.EmptyUtils;
import com.easyim.framework.shiro.service.SysLoginService;
import com.easyim.framework.util.ShiroUtils;
import com.easyim.system.service.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.easyim.common.exception.user.CaptchaException;
import com.easyim.common.exception.user.RoleBlockedException;
import com.easyim.common.exception.user.UserBlockedException;
import com.easyim.common.exception.user.UserNotExistsException;
import com.easyim.common.exception.user.UserPasswordNotMatchException;
import com.easyim.common.exception.user.UserPasswordRetryLimitExceedException;
import com.easyim.system.domain.SysUser;

/**
 * 自定义Realm 处理登录 权限
 * 
 * @author emessage
 */
public class UserRealm extends AuthorizingRealm
{
    private static final Logger log = LoggerFactory.getLogger(UserRealm.class);

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private SysLoginService loginService;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private FastDFSConfig fastDFSConfig;
    @Autowired
    private MessageConfig messageConfig;

    @Autowired
    private ISysFriendUserService sysFriendUserService;
    @Autowired
    private ISysMessageService sysMessageService;

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0)
    {
        SysUser user = ShiroUtils.getSysUser();
        // 角色列表
        Set<String> roles = new HashSet<String>();
        // 功能列表
        Set<String> menus = new HashSet<String>();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 管理员拥有所有权限
        if (roleService.isAdmin(user.getUserId()))
        {
            info.addRole("admin");
            info.addStringPermission("*:*:*");
        }
        else
        {
            roles = roleService.selectRoleKeys(user.getUserId());
            menus = menuService.selectPermsByUserId(user.getUserId());
            // 角色加入AuthorizationInfo认证对象
            info.setRoles(roles);
            // 权限加入AuthorizationInfo认证对象
            info.setStringPermissions(menus);
        }
        return info;
    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException
    {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        String password = "";
        if (upToken.getPassword() != null)
        {
            password = new String(upToken.getPassword());
        }

        SysUser user = null;
        try
        {
            String subserver = messageConfig.getSubcribe_server();
            user = loginService.login(username, password);
            user.setSubServer(subserver);
            user.setState(1);
            sysUserService.updateUserState(user.getUserId(),user.getState());
            boolean isAdmin = roleService.isAdmin(user.getUserId());
            user.setAdmin(isAdmin);
        }
        catch (CaptchaException e)
        {
            throw new AuthenticationException(e.getMessage(), e);
        }
        catch (UserNotExistsException e)
        {
            throw new UnknownAccountException(e.getMessage(), e);
        }
        catch (UserPasswordNotMatchException e)
        {
            throw new IncorrectCredentialsException(e.getMessage(), e);
        }
        catch (UserPasswordRetryLimitExceedException e)
        {
            throw new ExcessiveAttemptsException(e.getMessage(), e);
        }
        catch (UserBlockedException e)
        {
            throw new LockedAccountException(e.getMessage(), e);
        }
        catch (RoleBlockedException e)
        {
            throw new LockedAccountException(e.getMessage(), e);
        }
        catch (Exception e)
        {
            log.info("对用户[" + username + "]进行登录验证..验证未通过{}", e.getMessage());
            throw new AuthenticationException(e.getMessage(), e);
        }
        if (EmptyUtils.isNotEmpty(user.getAvatar())) {
            String avatarUrl = fastDFSConfig.getDownload_url() + user.getAvatar();
            user.setAvatarUrl(avatarUrl);
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
        return info;
    }

    /**
     * 清理缓存权限
     */
    public void clearCachedAuthorizationInfo()
    {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }
}
