<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head lang="en">
    <script type="text/javascript" src="${path}/static/H-ui/lib/jquery/1.9.1/jquery.min.js"></script>
    <script type="text/javascript" src="${path}/static/H-ui/lib/layer/2.4/layer.js"></script>
    <script type="text/javascript" src="${path}/static/H-ui/common/ajax.js"></script>
    <title>用户测试主页</title>
    <script>
        $(function () {
            var userInfoJson = window.localStorage.getItem('userInfo');
            //转换对象
            var userLoginResp = jQuery.parseJSON(userInfoJson);
            // var username = userLoginResp.username;
            // alert("当前登录用户:username=" + username);
            // $("#username").text("当前登录用户:" + username);

            var curWwwPath=window.document.location.href;
            // alert(curWwwPath);
            var url = curWwwPath.split("/view/")[0];
            alert(url);
        });
    </script>
</head>
<body>
<h2>Hello World! user==</h2>
<h1 id="username"></h1>
</body>
</html>
