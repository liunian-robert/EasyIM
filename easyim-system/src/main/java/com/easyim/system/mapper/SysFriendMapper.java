package com.easyim.system.mapper;

import com.easyim.system.domain.SysFriend;
import com.easyim.system.domain.SysFriendUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 群组 数据层
 * 
 * @author emessage
 */
public interface SysFriendMapper
{
    /**
     * 查询群组信息
     * @param typeid 好友类型ID
     * @return 群组信息
     */
    public List<SysFriendUser> selectSysFriendByTypeId(String typeid);

    /**
     * 查询好友信息
     * @param condition 查询条件
     * @return 好友信息
     */
    public List<SysFriendUser> findSysFriend(@Param("condition") String condition,@Param("userid") String userid);

    /**
     * 查询好友信息
     * @param userid 查询条件
     * @return 好友信息
     */
    public List<SysFriendUser> findSysFriendByUserId(@Param("userid") String userid);

    /**
     * 查询好友列表
     * @param belong 查询条件
     * @return 好友信息
     */
    public List<SysFriend> getSysFriendByByBelong(@Param("belong")String belong, @Param("userid") String userid);



    /**
     * 查询群组信息
     * @param sysFriend
     * @return 群组信息
     */
    public int saveSysFriend(SysFriend sysFriend);


    /**
     * 删除好友
     * @param userid
     * @return 群组信息
     */
    public int delSysFriend(@Param("userid") String userid, @Param("friendid") String friendid);


    /**
     * 更新好友备注
     * @param userid
     * @return 群组信息
     */
    public int updateFriendRemark(@Param("userid") String userid, @Param("friendid") String friendid,@Param("remark")String remark);
}