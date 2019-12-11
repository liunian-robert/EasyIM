package com.easyim.system.mapper;

import com.easyim.system.domain.SysFriendType;
import com.easyim.system.domain.SysNotify;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统消息
 * @author emessage
 */
public interface SysNotifyMapper
{
    /**
     * 添加系统消息
     * @param sysNotify
     * @return int
     */
    public int saveSysNotify(SysNotify sysNotify);



    /**
     * 根据发送人、接收人、消息类型查询消息
     * @param from
     * @Param to
     * @Param type
     * @return int
     */
    public List<SysNotify> findSysNotify(@Param("from") String from, @Param("to") String to,@Param("groupid") String groupid, @Param("type") Integer type);


    /**
     * 根据发送人、接收人、消息类型查询消息
     * @param handlerid
     * @return int
     */
    public List<SysNotify> findSysNotifyByHandlerId(@Param("handlerid") String handlerid);



    /**
     * 根据ID查询消息
     * @param id
     * @return int
     */
    public SysNotify getSysNotifyById(@Param("id") String id);



    /**
     * 根据ID查询消息
     * @param id
     * @return int
     */
    public int updateSysNotifyStatus(@Param("id") String id,@Param("status") Integer status);
}