package com.easyim.system.service.impl;

import com.easyim.common.utils.StringUtils;
import com.easyim.system.domain.SysFriendType;
import com.easyim.system.mapper.SysFriendTypeMapper;
import com.easyim.system.service.ISysFriendTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 好友分组 服务层实现
 * @author emessage
 */
@Service
public class ISysFriendTypeServiceImpl implements ISysFriendTypeService
{
    @Autowired
    private SysFriendTypeMapper friendTypeMapper;

    @Override
    public List<SysFriendType> selectSysFriendTypeByUserId(String userid) {
        if (StringUtils.isEmpty(userid)) {
            return null;
        }
        return friendTypeMapper.selectSysFriendTypeByUserId(userid);
    }

    @Override
    public int saveSysFriendType(SysFriendType sysFriendType) {
        if (sysFriendType == null) {
            return 0;
        }
        return friendTypeMapper.saveSysFriendType(sysFriendType);
    }

    @Override
    public int updateSysFriendType(String id, String groupname) {
        return friendTypeMapper.updateSysFriendType(id,groupname);
    }

    @Override
    public int delSysFriendType(String id) {
        return friendTypeMapper.delSysFriendType(id);
    }

    @Override
    public int selectSort(String userid) {
        return friendTypeMapper.selectSort(userid)+1;
    }
}
