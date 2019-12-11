package com.easyim.system.mapper;

import com.easyim.system.domain.SysChat;
import com.easyim.system.domain.SysChatRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 离线消息 数据层
 * 
 * @author emessage
 */
public interface SysChatMapper
{
    /**
     * 查询离线消息
     * @param userid 用户ID
     * @return 群组信息
     */
    public List<SysChat> findSysChatByUserId(@Param("userid")String userid);


    /**
     * 保存聊天消息
     * @param sysChat
     * @return 群组信息
     */
    public int saveSysChat(SysChat sysChat);


    /**
     * 更新聊天状态
     * @param userid
     * @return
     */
    int updateSysChatStatus(@Param("userid") String userid);


    /**
     * 查看聊天记录
     * @param toid
     * @return
     */
    List<SysChatRecord> findChatRecord(@Param("toid") String toid,@Param("fromid")String fromid);



    /**
     * 查看群聊天记录
     * @param groupid
     * @return
     */
    List<SysChatRecord> findGroupChatRecord(@Param("groupid") String groupid);



    /**
     * 查询聊天消息
     * @param id 聊天消息ID
     * @return SysChat
     */
    public SysChat getSysChatById(@Param("id") String id);



    /**
     * 删除聊天消息
     * @param id 聊天消息ID
     * @return int
     */
    public int delSysChatById(@Param("id") String id);
}