package com.easyim.web.controller.system.message.response;

public class SysGroupResponse {
    private String id;
    private String groupname;
    private String avatar;

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SysGroupResponse{");
        sb.append("id='").append(id).append('\'');
        sb.append(", groupname='").append(groupname).append('\'');
        sb.append(", avatar='").append(avatar).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
