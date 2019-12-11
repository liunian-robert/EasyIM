package com.easyim.web.controller.system.message.response;

import com.easyim.system.domain.SysFriendUser;

import java.util.List;

public class SysFriendTypeResponse {
    private String id;
    private String groupname;
    private List<SysFriendUser> list;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public List<SysFriendUser> getList() {
        return list;
    }

    public void setList(List<SysFriendUser> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SysFriendTypeResponse{");
        sb.append("id='").append(id).append('\'');
        sb.append(", groupname='").append(groupname).append('\'');
        sb.append(", list=").append(list);
        sb.append('}');
        return sb.toString();
    }
}
