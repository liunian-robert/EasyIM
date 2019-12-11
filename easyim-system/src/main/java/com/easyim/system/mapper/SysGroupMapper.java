package com.easyim.system.mapper;

import com.easyim.system.domain.SysConfig;
import com.easyim.system.domain.SysGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 群组 数据层
 * 
 * @author emessage
 */
public interface SysGroupMapper
{
    /**
     * 查询群组信息
     * @param userid 用户ID
     * @return 群组信息
     */
    public List<SysGroup> selectSysGroupByUserId(String userid);


    /**
     * 查询群组信息
     * @param userid 用户ID
     * @return 群组信息
     */
    public List<SysGroup> findSysGroup(@Param("condition")String condition,@Param("userid") String userid);


    /**
     * 新建群聊
     * @param sysGroup 参数用户ID
     * @return 群组列表
     */
    public int saveSysGroup(SysGroup sysGroup);



    /**
     * 查询群组信息
     * @param id 群组ID
     * @return 群组信息
     */
    public SysGroup getSysGroupById(@Param("id") String id);


    /**
     * 删除群
     * @param id 群ID
     * @return int
     */
    public int deleteGroupById(@Param("id")String id);
}