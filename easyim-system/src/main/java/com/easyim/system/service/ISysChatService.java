package com.easyim.system.service;

import com.easyim.system.domain.SysChat;
import com.easyim.system.domain.SysChatRecord;

import java.util.List;

public interface ISysChatService {

    /**
     * 查询离线消息
     * @param userid 用户ID
     * @return 群组信息
     */
    public List<SysChat> findSysChatByUserId(String userid);



    /**
     * 查询聊天消息
     * @param id 聊天消息ID
     * @return SysChat
     */
    public SysChat getSysChatById(String id);



    /**
     * 删除聊天消息
     * @param id 聊天消息ID
     * @return int
     */
    public int delSysChatById(String id);


    /**
     * 保存聊天消息
     * @param sysChat
     * @return 群组信息
     */
    public int saveSysChat(SysChat sysChat);


    /**
     * 更新聊天消息状态
     * @param userid
     * @return int
     */
    public int updateSysChatStatus(String userid);



    /**
     * 查看个人聊天记录
     * @param toid
     * @return
     */
    List<SysChatRecord> findChatRecord(String toid,String fromid);



    /**
     * 查看群聊天记录
     * @param groupid
     * @return
     */
    List<SysChatRecord> findGroupChatRecord(String groupid);
}
