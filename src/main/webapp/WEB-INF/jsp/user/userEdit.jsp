<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>编辑用户信息</title>
    <jsp:include page="../common/top.jsp"/>
</head>
<body>
<!--自定义代码start-->

<article class="cl pd-20">
    <div class="form form-horizontal">

        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>用户名：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text" placeholder="输入用户名" id="username" name="username">
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">邮&nbsp;&nbsp;&nbsp;&nbsp;箱：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text" placeholder="输入邮箱" name="email" id="email">
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>手机号码：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text" placeholder="输入手机号码" name="mobile" id="mobile">
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">用户状态：</label>
            <div class="formControls col-xs-8 col-sm-9 skin-minimal">
                <div class="radio-box">
                    <input name="status" type="radio" value="1" id="userStatus-1" checked>
                    <label for="userStatus-1">启用</label>
                </div>
                <div class="radio-box">
                    <input type="radio" name="status" value="0" id="userStatus-2">
                    <label for="userStatus-2">停用</label>
                </div>
            </div>
        </div>


        <div class="row cl">
            <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
                <input class="btn btn-primary radius" type="button" id="submitFrom" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
            </div>
        </div>
    </div>
</article>
<!--自定义代码end-->

<jsp:include page="../common/bottom.jsp"/>
<script>
    $(function () {

        //把用户信息的值显示到网页上
        var userInfoCallBack = function setUserInfo(result) {
            var userInfo = result.data;
            $("#username").val(userInfo.username);
            $("#email").val(userInfo.email);
            $("#mobile").val(userInfo.mobile);
            $("input:radio[name=status][value= " + userInfo.status + "]").attr("checked", true);
        };

        //获取url参数的用户id
        var userId = getQueryString("userId");

        //用户id不为空则要获取用户信息
        if (userId != null && typeof (userId) != 'undefined') {
            var userReq = {};
            userReq.userId = userId;
            sendGetFormDateAjax("${path}/user/getUserByUserId", userReq, userInfoCallBack);

        }

        //关闭并且刷新父页面
        function closeAndReload() {
            var index = parent.layer.getFrameIndex(window.name);
            parent.location.reload();
            parent.layer.close(index);
        }

        //用户编辑回调函数
        var userEditCallBack = function userEditCallBack() {
            layer.msg('操作成功', {icon: '-1', time: 1000});
            setTimeout(closeAndReload, 1000);
        };

        //表单提交
        $("#submitFrom").click(function () {
            var username = $("#username").val();
            var email = $("#email").val();
            var mobile = $("#mobile").val();
            var status = $("input[name='status']:checked").val();
            if (username === '') {
                layer.msg('请输入用户名', {icon: '-1', time: 1000});
                $("#username").focus();
                return;
            }
            if (email === '') {
                layer.msg('请输入邮箱', {icon: '-1', time: 1000});
                $("#email").focus();
                return;
            }
            if (mobile === '') {
                layer.msg('请输入手机号码', {icon: '-1', time: 1000});
                $("#mobile").focus();
                return;
            }


            var userReq = {};
            userReq.username = username;
            userReq.email = email;
            userReq.mobile = mobile;
            userReq.status = status;
            userReq.userId = userId;

            sendPostAjax('${path}/user/editUser', userReq, userEditCallBack);
        });
    });
</script>
</body>
</html>