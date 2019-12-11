package com.easyim.system.service.impl;

import com.easyim.common.utils.StringUtils;
import com.easyim.system.domain.SysGroup;
import com.easyim.system.mapper.SysGroupMapper;
import com.easyim.system.service.ISysGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 群组管理 服务层实现
 * @author emessage
 */
@Service
public class ISysGroupServiceImpl implements ISysGroupService
{
    @Autowired
    private SysGroupMapper groupMapper;

    @Override
    public List<SysGroup> selectSysGroupByUserId(String userid) {
        if (StringUtils.isEmpty(userid)) {
            return null;
        }
        return groupMapper.selectSysGroupByUserId(userid);
    }

    @Override
    public List<SysGroup> findSysGroup(String condition, String userid) {
        if (StringUtils.isEmpty(userid)) {
            return null;
        }
        return groupMapper.findSysGroup(condition,userid);
    }

    @Override
    public int saveSysGroup(SysGroup sysGroup) {
        if (sysGroup == null) {
            return 0;
        }
        return groupMapper.saveSysGroup(sysGroup);
    }

    @Override
    public SysGroup getSysGroupById(String id) {
        return groupMapper.getSysGroupById(id);
    }

    @Override
    public int deleteGroupById(String id) {
        return groupMapper.deleteGroupById(id);
    }
}
