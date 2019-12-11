package com.easyim.system.domain;

public class MessageResult {
    private Integer messages;
    private Integer requested;
    private Integer subscribers;
    private String last_message_id;

    public Integer getMessages() {
        return messages;
    }

    public void setMessages(Integer messages) {
        this.messages = messages;
    }

    public Integer getRequested() {
        return requested;
    }

    public void setRequested(Integer requested) {
        this.requested = requested;
    }

    public Integer getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Integer subscribers) {
        this.subscribers = subscribers;
    }

    public String getLast_message_id() {
        return last_message_id;
    }

    public void setLast_message_id(String last_message_id) {
        this.last_message_id = last_message_id;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MessageResult{");
        sb.append("messages=").append(messages);
        sb.append(", requested=").append(requested);
        sb.append(", subscribers=").append(subscribers);
        sb.append(", last_message_id=").append(last_message_id);
        sb.append('}');
        return sb.toString();
    }
}
