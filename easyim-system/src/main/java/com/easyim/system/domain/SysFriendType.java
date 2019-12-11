package com.easyim.system.domain;

public class SysFriendType {
    private String id;
    private String groupname;
    private String userid;
    private Integer sort;

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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SysFriendType{");
        sb.append("id='").append(id).append('\'');
        sb.append(", groupname='").append(groupname).append('\'');
        sb.append(", userid='").append(userid).append('\'');
        sb.append(", sort=").append(sort);
        sb.append('}');
        return sb.toString();
    }
}
