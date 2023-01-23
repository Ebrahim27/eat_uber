package com.ebi.snap_food.utils.sms;

public class SendSmsRequestc {


    private String username ;
    private  String password;
    private  String from ;
    private  String[] to;
    private  String text;
    private String chanell;

    public String getChanell() {
        return chanell;
    }

    public void setChanell(String chanell) {
        this.chanell = chanell;
    }

    public void setTo(String[] to) {
        this.to = to;
    }

    public void setText(String text) {
        this.text = text;
    }
/*public SendSmsRequest(String text, String... to) {
        this.to = to;
        this.text = text;
    }*/



    public String getFrom() {
        return from;
    }

    public String[] getTo() {
        return to;
    }

    public String getText() {
        return text;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
