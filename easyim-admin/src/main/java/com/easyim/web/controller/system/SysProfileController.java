package com.easyim.web.controller.system;

import com.easyim.common.config.FastDFSConfig;
import com.easyim.common.fastdfs.client.FastDFSClient;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.easyim.common.annotation.Log;
import com.easyim.common.config.Global;
import com.easyim.common.core.controller.BaseController;
import com.easyim.common.core.domain.AjaxResult;
import com.easyim.common.enums.BusinessType;
import com.easyim.common.utils.StringUtils;
import com.easyim.common.utils.file.FileUploadUtils;
import com.easyim.framework.shiro.service.SysPasswordService;
import com.easyim.framework.util.ShiroUtils;
import com.easyim.system.domain.SysUser;
import com.easyim.system.service.ISysUserService;
import sun.misc.BASE64Encoder;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;

/**
 * 个人信息 业务处理
 * 
 * @author emessage
 */
@Controller
@RequestMapping("/system/user/profile")
public class SysProfileController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(SysProfileController.class);

    private String prefix = "system/user/profile";

    @Autowired
    private ISysUserService userService;
    
    @Autowired
    private SysPasswordService passwordService;

    @Autowired
    private FastDFSConfig fastDFSConfig;

    /**
     * 个人信息
     */
    @GetMapping()
    public String profile(ModelMap mmap)
    {
        SysUser user = ShiroUtils.getSysUser();
        mmap.put("user", user);
        mmap.put("roleGroup", userService.selectUserRoleGroup(user.getUserId()));
        mmap.put("postGroup", userService.selectUserPostGroup(user.getUserId()));
        return prefix + "/profile";
    }

    @GetMapping("/checkPassword")
    @ResponseBody
    public boolean checkPassword(String password)
    {
        SysUser user = ShiroUtils.getSysUser();
        if (passwordService.matches(user, password))
        {
            return true;
        }
        return false;
    }

    @GetMapping("/resetPwd")
    public String resetPwd(ModelMap mmap)
    {
        SysUser user = ShiroUtils.getSysUser();
        mmap.put("user", userService.selectUserById(user.getUserId()));
        return prefix + "/resetPwd";
    }

    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    @ResponseBody
    public AjaxResult resetPwd(String oldPassword, String newPassword)
    {
        SysUser user = ShiroUtils.getSysUser();
        if (StringUtils.isNotEmpty(newPassword) && passwordService.matches(user, oldPassword))
        {
            user.setSalt(ShiroUtils.randomSalt());
            user.setPassword(passwordService.encryptPassword(user.getLoginName(), newPassword, user.getSalt()));
            if (userService.resetUserPwd(user) > 0)
            {
                ShiroUtils.setSysUser(userService.selectUserById(user.getUserId()));
                return success();
            }
            return error();
        }
        else
        {
            return error("修改密码失败，旧密码错误");
        }
    }

    /**
     * 修改用户
     */
    @GetMapping("/edit")
    public String edit(ModelMap mmap)
    {
        SysUser user = ShiroUtils.getSysUser();
        mmap.put("user", userService.selectUserById(user.getUserId()));
        return prefix + "/edit";
    }

    /**
     * 修改头像
     */
    @GetMapping("/avatar")
    public String avatar(ModelMap mmap)
    {
        SysUser user = ShiroUtils.getSysUser();
        mmap.put("user", userService.selectUserById(user.getUserId()));
        return prefix + "/avatar";
    }

    /**
     * 修改用户
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    @ResponseBody
    public AjaxResult update(SysUser user)
    {
        SysUser currentUser = ShiroUtils.getSysUser();
        currentUser.setUserName(user.getUserName());
        currentUser.setEmail(user.getEmail());
        currentUser.setPhonenumber(user.getPhonenumber());
        if (StringUtils.isNotEmpty(user.getSign())) {
            currentUser.setSign(user.getSign());
        }
        currentUser.setSex(user.getSex());
        if (userService.updateUserInfo(currentUser) > 0)
        {
            ShiroUtils.setSysUser(userService.selectUserById(currentUser.getUserId()));
            return success();
        }
        return error();
    }

    /**
     * 保存头像
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PostMapping("/updateAvatar")
    @ResponseBody
    public AjaxResult updateAvatar(@RequestParam("avatarfile") MultipartFile file)
    {
        SysUser currentUser = ShiroUtils.getSysUser();
        try
        {
            if (!file.isEmpty())
            {
                FastDFSClient client = new FastDFSClient();
                Map<String,String> mps = new HashMap<String,String>();
                mps.put("fileName",file.getOriginalFilename());
                String suffix = FileUploadUtils.getExtension(file);
                mps.put("fileExtName",suffix);
                mps.put("contentType",file.getContentType());
                mps.put("fileLength",file.getSize()+"");
                String filename = client.upload(file.getInputStream(),StringUtils.getUUID(Boolean.TRUE)+"."+suffix,mps);
                currentUser.setAvatar(filename);
                if (userService.updateUserInfo(currentUser) > 0) {
                    String token = FastDFSClient.getToken(filename,fastDFSConfig.getHttp_secret_key());
                    String avatarUrl = fastDFSConfig.getDownload_url() + filename +"?" + token;
                    SysUser sysUser = ShiroUtils.getSysUser();
                    sysUser.setAvatarUrl(avatarUrl);
                    ShiroUtils.setSysUser(sysUser);
                    return success();
                }
            }
            return error();
        }
        catch (Exception e)
        {
            log.error("修改头像失败！", e);
            return error(e.getMessage());
        }
    }
}
