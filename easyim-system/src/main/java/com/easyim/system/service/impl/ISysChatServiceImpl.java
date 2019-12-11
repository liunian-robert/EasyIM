package com.easyim.system.service.impl;

import com.easyim.system.domain.SysChat;
import com.easyim.system.domain.SysChatRecord;
import com.easyim.system.mapper.SysChatMapper;
import com.easyim.system.service.ISysChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ISysChatServiceImpl implements ISysChatService {

    @Autowired
    private SysChatMapper sysChatMapper;

    @Override
    public List<SysChat> findSysChatByUserId(String userid) {
        return sysChatMapper.findSysChatByUserId(userid);
    }

    @Override
    public SysChat getSysChatById(String id) {
        return sysChatMapper.getSysChatById(id);
    }

    @Override
    public int delSysChatById(String id) {
        return sysChatMapper.delSysChatById(id);
    }

    @Override
    public int saveSysChat(SysChat sysChat) {
        return sysChatMapper.saveSysChat(sysChat);
    }

    @Override
    public int updateSysChatStatus(String userid) {
        return sysChatMapper.updateSysChatStatus(userid);
    }

    @Override
    public List<SysChatRecord> findChatRecord(String toid, String fromid) {
        return sysChatMapper.findChatRecord(toid,fromid);
    }

    @Override
    public List<SysChatRecord> findGroupChatRecord(String groupid) {
        return sysChatMapper.findGroupChatRecord(groupid);
    }
}
