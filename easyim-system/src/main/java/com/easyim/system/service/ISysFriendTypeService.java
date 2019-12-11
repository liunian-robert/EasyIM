package com.easyim.system.service;

import com.easyim.system.domain.SysFriend;
import com.easyim.system.domain.SysFriendType;
import com.easyim.system.domain.SysGroup;

import java.util.List;

/**
 * 群组业务处理
 * 
 * @author emessage
 */
public interface ISysFriendTypeService
{
    /**
     * 查询群组列表
     * @param userid 参数用户ID
     * @return 群组列表
     */
    public List<SysFriendType> selectSysFriendTypeByUserId(String userid);



    /**
     * 查询群组列表
     * @param sysFriendType 参数用户ID
     * @return 群组列表
     */
    public int saveSysFriendType(SysFriendType sysFriendType);



    /**
     * 更新分组名称
     * @param id 参数用户ID
     * @return int
     */
    public int updateSysFriendType(String id,String groupname);



    /**
     * 删除分组
     * @param id 参数用户ID
     * @return int
     */
    public int delSysFriendType(String id);




    /**
     * 查询分组顺序
     * @param userid
     * @return 群组信息
     */
    public int selectSort(String userid);
}
