package com.easyim.system.domain;

public class SysGroup {
    private String id;
    private String groupname;
    private String belong;
    private String avatar;
    private String des;
    private String status;
    private Integer number;
    private Long timestamp;

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

    public String getBelong() {
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SysGroup{");
        sb.append("id='").append(id).append('\'');
        sb.append(", groupname='").append(groupname).append('\'');
        sb.append(", belong='").append(belong).append('\'');
        sb.append(", avatar='").append(avatar).append('\'');
        sb.append(", des='").append(des).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", number=").append(number);
        sb.append(", timestamp=").append(timestamp);
        sb.append('}');
        return sb.toString();
    }
}
