package com.springapp.mvc.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Message {
    private Integer id;

    private String userid;

    private Integer likenum;

    private Short anonymous;

    private Date createtime;

    private String fakenickname;

    private String fakeavatorurl;

    private String content;

    private List<Integer> likeUserList;

    public List<Integer> getLikeUserList() {
        return likeUserList;
    }

    /*public void setLikeUserList(List<Integer> likeUserList) {
        this.likeUserList = likeUserList;
    }*/

    public void addUserInLikeUserList(Integer id){
        if(this.likeUserList == null){
            this.likeUserList = new ArrayList<Integer>();
        }
        this.likeUserList.add(id);
    }

    public boolean deleteUserInLikeUserList(Integer id){
        boolean result = false;
        if(this.likeUserList != null){
            result = this.likeUserList.remove(id);
        }
        return result;
    }

    public Message(Integer id, String userid, Integer likenum, Short anonymous, Date createtime, String fakenickname, String fakeavatorurl, String content, List<Integer> likeUserList) {
        this.id = id;
        this.userid = userid;
        this.likenum = likenum;
        this.anonymous = anonymous;
        this.createtime = createtime;
        this.fakenickname = fakenickname;
        this.fakeavatorurl = fakeavatorurl;
        this.content = content;
        this.likeUserList = likeUserList;
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

    public String getFakenickname() {
        return fakenickname;
    }

    public void setFakenickname(String fakenickname) {
        this.fakenickname = fakenickname == null ? null : fakenickname.trim();
    }

    public String getFakeavatorurl() {
        return fakeavatorurl;
    }

    public void setFakeavatorurl(String fakeavatorurl) {
        this.fakeavatorurl = fakeavatorurl == null ? null : fakeavatorurl.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}