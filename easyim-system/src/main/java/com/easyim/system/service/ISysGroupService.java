package com.easyim.system.service;

import com.easyim.system.domain.SysConfig;
import com.easyim.system.domain.SysFriendType;
import com.easyim.system.domain.SysGroup;

import java.util.List;

/**
 * 群组业务处理
 * 
 * @author emessage
 */
public interface ISysGroupService
{
    /**
     * 查询群组列表
     * @param userid 参数用户ID
     * @return 群组列表
     */
    public List<SysGroup> selectSysGroupByUserId(String userid);


    /**
     * 查询群组列表
     * @param userid 参数用户ID
     * @return 群组列表
     */
    public List<SysGroup> findSysGroup(String condition,String userid);



    /**
     * 新建群聊
     * @param sysGroup 参数用户ID
     * @return 群组列表
     */
    public int saveSysGroup(SysGroup sysGroup);



    /**
     * 查询群组列表
     * @param id 参数用户ID
     * @return 群组列表
     */
    public SysGroup getSysGroupById(String id);


    /**
     * 删除群
     * @param id 群ID
     * @return int
     */
    public int deleteGroupById(String id);


}
