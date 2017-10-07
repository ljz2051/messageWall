package com.springapp.mvc.entity;

import java.util.Date;

public class Message {
    private Integer id;

    private String userid;

    private Integer likenum;

    private Short anonymous;

    private Date createtime;

    private String content;

    public Message(String userid, Integer likenum, Short anonymous, Date createtime, String content) {
        this.userid = userid;
        this.likenum = likenum;
        this.anonymous = anonymous;
        this.createtime = createtime;
        this.content = content;
    }

    public Message(Integer id, String userid, Integer likenum, Short anonymous, Date createtime, String content) {
        this.id = id;
        this.userid = userid;
        this.likenum = likenum;
        this.anonymous = anonymous;
        this.createtime = createtime;
        this.content = content;
    }

    public Message() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public Integer getLikenum() {
        return likenum;
    }

    public void setLikenum(Integer likenum) {
        this.likenum = likenum;
    }

    public Short getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(Short anonymous) {
        this.anonymous = anonymous;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

}