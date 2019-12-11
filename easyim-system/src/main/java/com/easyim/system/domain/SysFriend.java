package com.easyim.system.domain;

public class SysFriend {
    private String id;
    private  String groupid;
    private String userid;
    private String remark;
    private String belong;
    private Long timestamp;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBelong() {
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SysFriend{");
        sb.append("id='").append(id).append('\'');
        sb.append(", groupid='").append(groupid).append('\'');
        sb.append(", userid='").append(userid).append('\'');
        sb.append(", remark='").append(remark).append('\'');
        sb.append(", belong='").append(belong).append('\'');
        sb.append(", timestamp=").append(timestamp);
        sb.append('}');
        return sb.toString();
    }
}
