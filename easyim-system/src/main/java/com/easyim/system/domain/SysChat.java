package com.easyim.system.domain;

public class SysChat {

    private String id;
    private  String fromid;
    private String to_userid;
    private String to_groupid;
    private String content;
    private Long timestamp;
    private String type;
    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromid() {
        return fromid;
    }

    public void setFromid(String fromid) {
        this.fromid = fromid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTo_userid() {
        return to_userid;
    }

    public void setTo_userid(String to_userid) {
        this.to_userid = to_userid;
    }

    public String getTo_groupid() {
        return to_groupid;
    }

    public void setTo_groupid(String to_groupid) {
        this.to_groupid = to_groupid;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SysChat{");
        sb.append("id='").append(id).append('\'');
        sb.append(", fromid='").append(fromid).append('\'');
        sb.append(", to_userid='").append(to_userid).append('\'');
        sb.append(", to_groupid='").append(to_groupid).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append(", timestamp=").append(timestamp);
        sb.append(", type='").append(type).append('\'');
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
