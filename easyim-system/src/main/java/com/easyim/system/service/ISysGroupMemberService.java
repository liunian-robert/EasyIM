package com.easyim.system.service;

import com.easyim.system.domain.SysGroup;
import com.easyim.system.domain.SysGroupMember;

import java.util.List;

/**
 * 群组业务处理
 * 
 * @author emessage
 */
public interface ISysGroupMemberService
{
    /**
     * 新建群聊
     * @param sysGroupMember 参数用户ID
     * @return 群组列表
     */
    public int saveSysGroupMember(SysGroupMember sysGroupMember);



    /**
     * 查询群成员
     * @param groupid 参数用户ID
     * @return 群组成员
     */
    public List<SysGroupMember> findSysGroupMember(String groupid);



    /**
     * 查询群成员
     * @param userid 参数用户ID
     * @return 群组成员
     */
    public SysGroupMember getSysGroupMemberByUserId(String groupid,String userid);


    /**
     * 查询群成员
     * @param userid 参数用户ID
     * @return 群组成员
     */
    public int modifyGroupRemark(String groupid,String userid,String remark);


    /**
     * 删除群成员
     * @param id 群组成员ID
     * @return int
     */
    public int deleteGroupMemberById(String id);

}
