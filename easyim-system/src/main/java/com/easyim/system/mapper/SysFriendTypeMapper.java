package com.easyim.system.mapper;

import com.easyim.system.domain.SysFriendType;
import com.easyim.system.domain.SysGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 群组 数据层
 * 
 * @author emessage
 */
public interface SysFriendTypeMapper
{
    /**
     * 查询群组信息
     * @param userid 用户ID
     * @return 群组信息
     */
    public List<SysFriendType> selectSysFriendTypeByUserId(String userid);


    /**
     * 保存群组信息
     * @param sysFriendType
     * @return 群组信息
     */
    public int saveSysFriendType(SysFriendType sysFriendType);


    /**
     * 查询分组顺序
     * @param userid
     * @return 群组信息
     */
    public int selectSort(String userid);



    /**
     * 更新分组名称
     * @param id 参数用户ID
     * @return int
     */
    public int updateSysFriendType(@Param("id")String id, @Param("groupname")String groupname);



    /**
     * 删除分组
     * @param id 参数用户ID
     * @return int
     */
    public int delSysFriendType(@Param("id") String id);
}