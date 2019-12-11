package com.easyim.system.service;

import com.easyim.system.domain.SysFriendUser;
import com.easyim.system.domain.SysNotify;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 消息 相关逻辑处理
 * 
 * @author emessage
 */
public interface ISysNotifyService
{
    /**
     * 添加系统消息
     * @param sysNotify
     * @return int
     */
    public int saveSysNotify(SysNotify sysNotify);


    /**
     * 更新消息状态
     * @param id
     * @return int
     */
    public int updateSysNotifyStatus(String id,Integer status);



    /**
     * 根据发送人、接收人、消息类型查询消息
     * @param from
     * @Param to
     * @Param type
     * @return int
     */
    public List<SysNotify> findSysNotify(String from, String to, String groupid,Integer type);


    /**
     * 根据发送人、接收人、消息类型查询消息
     * @param handlerid
     * @return int
     */
    public List<SysNotify> findSysNotifyByHandlerId(String handlerid);



    /**
     * 根据消息ID查询消息
     * @param id
     * @return int
     */
    public SysNotify getSysNotifyById(String id);
}
