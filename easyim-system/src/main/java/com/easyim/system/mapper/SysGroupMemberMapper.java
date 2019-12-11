package com.easyim.system.mapper;

import com.easyim.system.domain.SysGroupMember;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 群组成员 数据层
 * 
 * @author emessage
 */
public interface SysGroupMemberMapper
{
   /**
     * 新建群聊
     * @param sysGroupMember 参数用户ID
     * @return 群组列表
     */
    public int saveSysGroupMember(SysGroupMember sysGroupMember);


    /**
     * 查询群聊成员
     * @param groupid 参数用户ID
     * @return 群组列表
     */
    List<SysGroupMember> findSysGroupMember(@Param("groupid") String groupid);



    /**
     * 查询群成员
     * @param userid 参数用户ID
     * @return 群组成员
     */
    public SysGroupMember getSysGroupMemberByUserId(@Param("groupid") String groupid,@Param("userid")String userid);



    /**
     * 修改群成员备注
     * @param userid 参数用户ID
     * @return 群组成员
     */
    public int modifyGroupRemark(@Param("groupid") String groupid,@Param("userid")String userid,@Param("remark")String remark);


    /**
     * 删除群成员
     * @param id 群组成员ID
     * @return int
     */
    public int deleteGroupMemberById(@Param("id") String id);
}