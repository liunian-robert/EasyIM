package com.easyim.system.domain;

public class SysNotify {
    private String id;
    private Integer type;
    private String from;
    private String to;
    private Integer status;
    private String remark;
    private String timestamp;
    private String handlerid;
    private String groupid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getHandlerid() {
        return handlerid;
    }

    public void setHandlerid(String handlerid) {
        this.handlerid = handlerid;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SysNotify{");
        sb.append("id='").append(id).append('\'');
        sb.append(", type=").append(type);
        sb.append(", from='").append(from).append('\'');
        sb.append(", to='").append(to).append('\'');
        sb.append(", status=").append(status);
        sb.append(", remark='").append(remark).append('\'');
        sb.append(", timestamp='").append(timestamp).append('\'');
        sb.append(", handlerid='").append(handlerid).append('\'');
        sb.append(", groupid='").append(groupid).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
