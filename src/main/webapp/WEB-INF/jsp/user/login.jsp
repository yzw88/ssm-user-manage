<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head lang="en">
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
    <%--引入头部--%>
    <jsp:include page="../common/top.jsp"/>
    <link rel="stylesheet" type="text/css" href="${path}/static/H-ui/static/h-ui.admin/css/H-ui.login.css"/>
    <title>用户管理系统后台登录</title>
</head>

<body>

<div class="header" style="background-image:url('${path}/static/H-ui/static/h-ui.admin/images/logo.png')"></div>
<div class="loginWraper">
    <div id="loginform" class="loginBox">
        <form class="form form-horizontal">
            <div class="row cl">
                <label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60d;</i></label>
                <div class="formControls col-xs-4">
                    <input id="username" name="username" type="text" placeholder="输入用户名" class="input-text size-L">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60e;</i></label>
                <div class="formControls col-xs-4">
                    <input id="password" name="password" type="password" placeholder="输入密码" class="input-text size-L">
                </div>
            </div>

            <div class="row cl">
                <div class="formControls col-xs-8 col-xs-offset-3">
                    <input class="input-text size-L" type="text" id="imgCode" placeholder="验证码" style="width:150px;">
                    <img id="validCode" src="">
                    <a id="updateCode" href="javascript:;">看不清，换一张</a>
                </div>
            </div>

            <div class="row cl">

                <div class="formControls col-xs-8 col-xs-offset-3">
                    <input name="" type="button" class="btn btn-success radius size-L"
                           value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;" id="login">
                    &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;
                    <input name="" type="reset" class="btn btn-default radius size-L"
                           value="&nbsp;清&nbsp;&nbsp;&nbsp;&nbsp;空&nbsp;">
                </div>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript" src="${path}/static/H-ui/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="${path}/static/H-ui/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="${path}/static/H-ui/common/ajax.js"></script>
<script>

    //打开页面执行
    $(function () {

        //定义验证码key
        var codeKey;

        //登录方法
        function login() {
            var username = $("#username").val();
            var password = $("#password").val();
            var imgCode = $("#imgCode").val();
            if (username === '') {
                layer.msg('请输入用户名', {icon: '-1', time: 1000});
                $("#username").focus();
                return;
            }
            if (password === '') {
                layer.msg('请输入密码', {icon: '-1', time: 1000});
                $("#password").focus();
                return;
            }
            if (imgCode === '') {
                layer.msg('请输入验证码', {icon: '-1', time: 1000});
                $("#imgCode").focus();
                return;
            }

            var loginReq = {};
            loginReq.codeKey = codeKey;
            loginReq.username = username;
            loginReq.password = password;
            loginReq.imgCode = imgCode;

            sendPostAjax('${path}/user/login', loginReq, toIndexCallBack);
        }

        //登录成功跳转主页方法
        function toIndexCallBack(result) {
            //把相关信息保存到localStorage
            var userLoginResp = result.data;
            var userInfoJson = JSON.stringify(userLoginResp);
            window.localStorage.setItem("userInfo", userInfoJson);

            //从定向到主页
            location.href = '${path}/view/user/userList';
        }

        //图形验证码回调
        var codeCallBack = function codeCallBack(result) {
            var base64Code = result.data.base64Code;
            codeKey = result.data.codeKey;
            $("#validCode").attr("src", base64Code);
        };

        //获取图形验证码
        function getValidCode() {
            sendGetAjax('${path}/user/getUserValidCode', null, codeCallBack);
        }

        //调用获取图形验证码
        getValidCode();

        //更新验证码函数
        $("#updateCode").click(function () {
            getValidCode();
        });

        //登录操作
        $('#login').click(function () {
            login();
        });

        //监听键盘事件
        $(document).keyup(function (event) {
            if (event.keyCode == 13) {
                login();
            }
        });
    });

</script>
</body>
</html>