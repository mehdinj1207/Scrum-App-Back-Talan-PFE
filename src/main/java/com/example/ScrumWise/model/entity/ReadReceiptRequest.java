package com.example.ScrumWise.model.entity;

public class ReadReceiptRequest {
    private String channel;
    private String sender;
    private String receiver;

    public ReadReceiptRequest() {
        super();
    }
    public String getChannel() {
        return channel;
    }
    public void setChannel(String channel) {
        this.channel = channel;
    }
    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
    public String getReceiver() {
        return receiver;
    }
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
