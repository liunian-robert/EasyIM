package com.easyim.system.service.impl;

import com.easyim.system.domain.SysGroupMember;
import com.easyim.system.mapper.SysGroupMemberMapper;
import com.easyim.system.service.ISysGroupMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ISysGroupMemberServiceImpl implements ISysGroupMemberService {
    @Autowired
    private SysGroupMemberMapper sysGroupMemberMapper;
    @Override
    public int saveSysGroupMember(SysGroupMember sysGroupMember) {
        if (sysGroupMember == null) {
            return 0;
        }
        return sysGroupMemberMapper.saveSysGroupMember(sysGroupMember);
    }

    @Override
    public List<SysGroupMember> findSysGroupMember(String groupid) {
        return sysGroupMemberMapper.findSysGroupMember(groupid);
    }

    @Override
    public SysGroupMember getSysGroupMemberByUserId(String groupid, String userid) {
        return sysGroupMemberMapper.getSysGroupMemberByUserId(groupid,userid);
    }

    @Override
    public int modifyGroupRemark(String groupid, String userid, String remark) {
        return sysGroupMemberMapper.modifyGroupRemark(groupid,userid,remark);
    }

    @Override
    public int deleteGroupMemberById(String id) {
        return sysGroupMemberMapper.deleteGroupMemberById(id);
    }
}
