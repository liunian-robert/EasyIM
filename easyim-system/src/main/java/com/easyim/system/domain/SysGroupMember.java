package com.easyim.system.domain;

public class SysGroupMember {
    private String id;
    private String groupid;
    private String userid;
    private String remark;
    private Long timestamp;
    private Integer status;
    private Integer type;

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

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SysGroupMember{");
        sb.append("id='").append(id).append('\'');
        sb.append(", groupid='").append(groupid).append('\'');
        sb.append(", userid='").append(userid).append('\'');
        sb.append(", remark='").append(remark).append('\'');
        sb.append(", timestamp=").append(timestamp);
        sb.append(", status=").append(status);
        sb.append(", type=").append(type);
        sb.append('}');
        return sb.toString();
    }
}
