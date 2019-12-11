package com.easyim.system.service.impl;

import com.easyim.system.domain.SysNotify;
import com.easyim.system.mapper.SysNotifyMapper;
import com.easyim.system.service.ISysNotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ISysNotifyServiceImpl implements ISysNotifyService {

    @Autowired
    private SysNotifyMapper sysNotifyMapper;

    @Override
    public int saveSysNotify(SysNotify sysNotify) {
        return sysNotifyMapper.saveSysNotify(sysNotify);
    }

    @Override
    public int updateSysNotifyStatus(String id, Integer status) {
        return sysNotifyMapper.updateSysNotifyStatus(id,status);
    }

    @Override
    public List<SysNotify> findSysNotify(String from, String to,String groupid, Integer type) {
        return sysNotifyMapper.findSysNotify(from,to,groupid,type);
    }

    @Override
    public List<SysNotify> findSysNotifyByHandlerId(String handlerid) {
        return sysNotifyMapper.findSysNotifyByHandlerId(handlerid);
    }

    @Override
    public SysNotify getSysNotifyById(String id) {
        return sysNotifyMapper.getSysNotifyById(id);
    }
}
