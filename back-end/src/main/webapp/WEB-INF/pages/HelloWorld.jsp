<%--
  Created by IntelliJ IDEA.
  User: LiJinZhong
  Date: 2017/10/3
  Time: 11:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>公共接口：</h2>
<p>1. 登录:（小程序中调用wx.login 返回code，将code发送到后端换取sessionId,需将sessionId和userId保存在本地）</p>
<br/>
<form method="post" action="LoginController/codeForSessionKey">
    <label>code: </label>
    <input name="code"  type="text">
    <button type="submit">提交</button>
</form>
<br/>

<p>2. 上传头像和昵称：（登陆后，小程序中调用wx.getUserInfo，获取用户的昵称和头像等信息，将其发送到服务器端，其中rawData，signature用于验证数据的正确性）</p>
<form method="post" action="LoginController/uploadNickName">
    sessionId:<input name="sessionId" type="text"/>
    昵称（nickname）：<input name="nickName" type="text"/>
    头像url（avatarUrl）:<input name="avatarUrl" type="text"/>
    rawData:<input name="rawData" type="text"/>
    signature<input name="signature" type="text"/>
    <button type="submit">提交</button>
</form>
<br/>
<a href="CommonController/getAccessToken">获取access_token</a>
<hr/>

<h2>个人足迹</h2>
<p>1. 获取微信的头像和昵称：</p>
<p>用户登陆后，便调用wx.getUserInfo，除了将信息发送上述“上传头像和昵称”的接口外，还需存储到globaldata中</p>
<br/>

<p>2. 上传背景照片（返回背景照片url）：</p>
<p>小程序页面通过 wx.chooseImage接口获得本地相册图片的文件途径，然后通过wx.uploadFile接口上传服务器</p>
<form method="post" action="MyMessageController/uploadBackPhoto" enctype="multipart/form-data">
    sessionId:<input name="sessionId" type="text"/>
    选择文件:<input type="file" name="file"/>（注：小程序中此处只需通过wx.chooseImage获取本地图片url，将其赋给filePath即可）
    <button type="submit">提交</button>
</form>
<br/>
<p>3. 获取背景照片（返回背景照片url，没有的话，返回null）：</p>
<form method="post" action="MyMessageController/gainBackPhoto">
    sessionId:<input name="sessionId" type="text"/>
    <button type="submit">提交</button>
</form>
<br/>
<p>4. 获得用户表白信息：</p>
<form method="post" action="MyMessageController/showMineByPage">
    sessionId : <input name="sessionId" type="text"/>
    用户id：<input name="userId" type="text">
    当前页面号（currentPage）:<input name="pageNum" type="text"/>
    页面大小（pageSize）：<input name="pageSize" type="text"/>
    排序依据（id,或createTime等）<input name="order" type="text"/>
    正逆序（asc / desc）<input name="sort" type="text"/>
    <button type="submit">提交</button>
</form>
<hr/>
<h2>表白弹窗</h2>
<p>用户想要发布表白，必须授权获取头像和昵称，若用户登陆时拒绝，此处应再次申请授权，并
    将用户昵称和头像通过接口发送后台服务器，同时存储到globaldata中</p>
<p>匿名状态下可以选择使用以前上传过的匿名头像，或者上传新的匿名头像</p>
<%--<p>1. 上传新的匿名头像（小程序逻辑与上传背景照片相似）：</p>
<form method="post" action="MyMessageController/uploadFakePhoto" enctype="multipart/form-data">
    sessionId:<input type="text" name="sessionId">
    选择文件：<input type="file" name="file"/>
    <button type="submit">提交</button>
</form>--%>

<p>1. 查询以前使用过的匿名头像列表：</p>
<form method="post" action="MyMessageController/selectAvatarList">
    sessionId:<input type="text" name="sessionId"/>
    <button type="submit">提交</button>
</form>
<br/>
<p>2. 发布表白（如果用以前的头像，则通过此接口）</p>
<form method="post" action="MyMessageController/writeMessage">
    sessionId:<input name="sessionId" type="text"/>
    是否匿名：(1表示匿名，0表示不匿名)<input name="anonymous" type="text"/>
    匿名状态下昵称<input type="text" name="fakeName" />
    匿名状态下头像url(形如"/resource/imgs/1ewewqe3214213321.jpg")：<input type="text" name="fakeAvatarUrl"/>
    表白信息：<textarea name="content" cols="30" rows="5"></textarea>
    <button type="submit">提交</button>
</form>
<br/>
<p>3. 发布表白（如果用新的匿名头像，则通过此接口）</p>
<form method="post" action="MyMessageController/writeMessage2" enctype="multipart/form-data">
    sessionId:<input name="sessionId" type="text"/>
    是否匿名：(1表示匿名，0表示不匿名)<input name="anonymous" type="text"/>
    匿名状态下昵称<input type="text" name="fakeName" />
    匿名状态下头像：选择文件：<input type="file" name="file"/>
    表白信息：<textarea name="content" cols="30" rows="5"></textarea>
    <button type="submit">提交</button>
</form>
<hr/>
<h2>首页（分页查询）</h2>
<p>1.获取所有用户表白信息(返回结果中，fakenickname和fakeavatorurl在匿名模式下为匿名的昵称和头像，非匿名模式下为真实的昵称和头像)</p>
<form method="post" action="MessageController/showAllByPage">
    用户id：<input name="userId" type="text"/>
    当前页面号（currentPage）:<input name="pageNum" type="text"/>
    页面大小（pageSize）：<input name="pageSize" type="text"/>
    排序依据（createTime等）<input name="order" type="text"/>
    正逆序（asc / desc）<input name="sort" type="text"/>
    <button type="submit">提交</button>
</form>
<br/>
<p>2. 获取本周点赞数topn的表白</p>
<form method="post" action="MessageController/showTopN">
    n:<input name="topn" type="text"/>
    用户id：<input name="userId" type="text"/>
    <button type="submit">提交</button>
</form>
<br/>
<p>3. 点赞(点赞前必须登录，调用公共接口中的登录接口时，会返回userId，用于此处)</p>
<form method="post" action="MessageController/like">
    用户id：<input name="userId" type="text"/>
    表白信息id：<input name="messageId" type="text"/>
    <button type="submit">提交</button>
</form>
<br/>
<p>4. 取消点赞（只有点赞后才能取消点赞）</p>
<form method="post" action="MessageController/cancelLike">
    用户id：<input name="userId" type="text"/>
    表白信息id：<input name="messageId" type="text"/>
    <button type="submit">提交</button>
</form>
<hr/>
</body>
</html>
