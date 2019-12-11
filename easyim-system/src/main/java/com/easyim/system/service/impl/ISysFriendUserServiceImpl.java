package com.easyim.system.service.impl;

import com.easyim.common.utils.StringUtils;
import com.easyim.system.domain.SysFriend;
import com.easyim.system.domain.SysFriendUser;
import com.easyim.system.mapper.SysFriendMapper;
import com.easyim.system.service.ISysFriendUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 好友分组 服务层实现
 * @author emessage
 */
@Service
public class ISysFriendUserServiceImpl implements ISysFriendUserService {
    @Autowired
    private SysFriendMapper friendMapper;

    @Override
    public List<SysFriendUser> selectSysFriendUserByTypeId(String typeid) {
        if (StringUtils.isEmpty(typeid)) {
            return null;
        }
        return friendMapper.selectSysFriendByTypeId(typeid);
    }

    @Override
    public List<SysFriendUser> findSysFriend(String condition, String userid) {
        if (StringUtils.isEmpty(userid)) {
            return null;
        }
        return friendMapper.findSysFriend(condition,userid);
    }

    @Override
    public List<SysFriendUser> findSysFriendByUserId(String userid) {
        return friendMapper.findSysFriendByUserId(userid);
    }

    @Override
    public List<SysFriend> getSysFriendByByBelong(String belong, String userid) {
        return friendMapper.getSysFriendByByBelong(belong,userid);
    }

    @Override
    public int saveSysFriend(SysFriend sysFriend) {
        return friendMapper.saveSysFriend(sysFriend);
    }

    @Override
    public int delSysFriend(String userid, String friendid) {
        return friendMapper.delSysFriend(userid,friendid);
    }

    @Override
    public int updateFriendRemark(String userid, String friendid, String remark) {
        return friendMapper.updateFriendRemark(userid,friendid,remark);
    }
}
