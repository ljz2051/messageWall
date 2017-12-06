package com.springapp.mvc.dto;

import java.util.Date;
import java.util.List;

/**
 * @author
 * @Description:
 * @date : 2017/12/3
 **/
public class MessageToReturn {
    private Integer id;
    private Integer likenum;

    private Short anonymous;

    private Date createtime;

    private String fakenickname;

    private String fakeavatorurl;

    private String content;

    private Boolean likeFlag;

    //private String userid;

    //private List<Integer> likeUserList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        this.fakenickname = fakenickname;
    }

    public String getFakeavatorurl() {
        return fakeavatorurl;
    }

    public void setFakeavatorurl(String fakeavatorurl) {
        this.fakeavatorurl = fakeavatorurl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getLikeFlag() {
        return likeFlag;
    }

    public void setLikeFlag(Boolean likeFlag) {
        this.likeFlag = likeFlag;
    }

}
