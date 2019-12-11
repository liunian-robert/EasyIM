package com.easyim.system.service;

import com.easyim.system.domain.SysFriend;
import com.easyim.system.domain.SysFriendUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 好友用户信息业务处理
 * 
 * @author emessage
 */
public interface ISysFriendUserService
{
    /**
     * 查询群组列表
     * @param typeid 参数用户ID
     * @return 群组列表
     */
    public List<SysFriendUser> selectSysFriendUserByTypeId(String typeid);


    /**
     * 查询推荐好友列表
     * @param condition 参数用户ID
     * @return 推荐好友列表
     */
    public List<SysFriendUser> findSysFriend(String condition,String userid);


    /**
     * 查询好友信息
     * @param userid 查询条件
     * @return 好友信息
     */
    public List<SysFriendUser> findSysFriendByUserId(@Param("userid") String userid);


    public List<SysFriend> getSysFriendByByBelong(String belong, String userid);



    /**
     * 保存好友信息
     * @param sysFriend 好友参数
     * @return int
     */
    public int saveSysFriend(SysFriend sysFriend);


    /**
     * 删除好友
     * @param userid 好友参数
     * @return int
     */
    public int delSysFriend(String userid,String friendid);


    /**
     * 更新好友备注
     * @param userid
     * @return 群组信息
     */
    public int updateFriendRemark(String userid,String friendid,String remark);
}
