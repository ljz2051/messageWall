package com.springapp.mvc.entity;

public class UserInfo {
    private String openid;

    private String wxnickname;

    private String wxavatarurl;

    private String backphotourl;

    private String fakenickname;

    private String fakeavatarurllist;

    public UserInfo(String openid, String wxnickname, String wxavatarurl, String backphotourl, String fakenickname, String fakeavatarurllist) {
        this.openid = openid;
        this.wxnickname = wxnickname;
        this.wxavatarurl = wxavatarurl;
        this.backphotourl = backphotourl;
        this.fakenickname = fakenickname;
        this.fakeavatarurllist = fakeavatarurllist;
    }

    public UserInfo(String openid){
        this.openid = openid;
    }

    public UserInfo() {
        super();
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getWxnickname() {
        return wxnickname;
    }

    public void setWxnickname(String wxnickname) {
        this.wxnickname = wxnickname == null ? null : wxnickname.trim();
    }

    public String getWxavatarurl() {
        return wxavatarurl;
    }

    public void setWxavatarurl(String wxavatarurl) {
        this.wxavatarurl = wxavatarurl == null ? null : wxavatarurl.trim();
    }

    public String getBackphotourl() {
        return backphotourl;
    }

    public void setBackphotourl(String backphotourl) {
        this.backphotourl = backphotourl == null ? null : backphotourl.trim();
    }

    public String getFakenickname() {
        return fakenickname;
    }

    public void setFakenickname(String fakenickname) {
        this.fakenickname = fakenickname == null ? null : fakenickname.trim();
    }

    public String getFakeavatarurllist() {
        return fakeavatarurllist;
    }

    public void setFakeavatarurllist(String fakeavatarurllist) {
        this.fakeavatarurllist = fakeavatarurllist == null ? null : fakeavatarurllist.trim();
    }
}