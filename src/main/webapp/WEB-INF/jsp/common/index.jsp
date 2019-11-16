<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>用户管理系统</title>
    <link rel="stylesheet" type="text/css" href="${path}/static/H-ui/static/h-ui/css/H-ui.min.css"/>
    <link rel="stylesheet" type="text/css" href="${path}/static/H-ui/static/h-ui.admin/css/H-ui.admin.css"/>
    <link rel="stylesheet" type="text/css" href="${path}/static/H-ui/lib/Hui-iconfont/1.0.8/iconfont.css"/>
    <link rel="stylesheet" type="text/css" href="${path}/static/H-ui/static/h-ui.admin/skin/default/skin.css"
          id="skin"/>
    <link rel="stylesheet" type="text/css" href="${path}/static/H-ui/static/h-ui.admin/css/style.css"/>
    <link rel="stylesheet" href="${path}/static/layui/css/layui.css" media="all">

    <style>
        body {
            margin: 0;
            padding: 0;
        }
    </style>
</head>
<body>
<header class="navbar-wrapper">
    <div class="navbar navbar-fixed-top">
        <div class="container-fluid cl"><a class="logo navbar-logo f-l mr-10 hidden-xs">用户后台管理系统</a>
            <span class="logo navbar-slogan f-l mr-10 hidden-xs"></span>
            <a aria-hidden="false" class="nav-toggle Hui-iconfont visible-xs" href="javascript:;">&#xe667;</a>
            <nav class="nav navbar-nav">

            </nav>
            <nav id="Hui-userbar" class="nav navbar-nav navbar-userbar hidden-xs">
                <ul class="cl">
                    <%--<li>超级管理员</li>--%>
                    <li class="dropDown dropDown_hover">
                        <a href="#" class="dropDown_A" id="userInfo">

                        </a>
                        <ul class="dropDown-menu menu radius box-shadow">
                            <li><a href="#" id="logout">退出</a></li>
                        </ul>
                    </li>
                    <li id="Hui-skin" class="dropDown right dropDown_hover"><a href="javascript:;" class="dropDown_A"
                                                                               title="换肤"><i class="Hui-iconfont"
                                                                                             style="font-size:18px">&#xe62a;</i></a>
                        <ul class="dropDown-menu menu radius box-shadow">
                            <li><a href="javascript:;" data-val="default" title="默认（黑色）">默认（黑色）</a></li>
                            <li><a href="javascript:;" data-val="blue" title="蓝色">蓝色</a></li>
                            <li><a href="javascript:;" data-val="green" title="绿色">绿色</a></li>
                            <li><a href="javascript:;" data-val="red" title="红色">红色</a></li>
                            <li><a href="javascript:;" data-val="yellow" title="黄色">黄色</a></li>
                            <li><a href="javascript:;" data-val="orange" title="橙色">橙色</a></li>
                        </ul>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</header>
<!--/_header 作为公共模版分离出去-->

<!--_menu 作为公共模版分离出去-->
<aside class="Hui-aside">

    <div class="menu_dropdown bk_2">

        <%--dl表示一个菜单--%>
        <dl id="menu-UserList">
            <%--selected表示选中--%>
            <dt>
                <i class="Hui-iconfont selected">&#xe62d;</i>
                用户管理
                <i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
            </dt>
            <%--display: block表示展开--%>
            <dd style="display: block">
                <ul>
                    <%--current样式表示选中--%>
                    <li class="current-li current" id="current-user">
                        <a class="toUrl" href="javascript:void(0)" to-url="${path}/view/user/userList"
                           title="用户列表">用户列表</a>
                    </li>
                </ul>
            </dd>
        </dl>

    </div>
</aside>

<section class="Hui-article-box">
    <div id="iframe_box">
        <div class="show_iframe">
            <div style="display:none" class="loading"></div>
            <iframe id="iframe-welcome" data-scrolltop="0" scrolling="yes" frameborder="0"
                    src=""></iframe>
        </div>
    </div>
</section>
</body>
<script type="text/javascript" src="${path}/static/H-ui/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="${path}/static/H-ui/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="${path}/static/H-ui/static/h-ui/js/H-ui.js"></script>
<script type="text/javascript" src="${path}/static/H-ui/static/h-ui.admin/js/H-ui.admin.page.js"></script>
<!--/_footer /作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="${path}/static/H-ui/lib/My97DatePicker/4.8/WdatePicker.js"></script>
<script type="text/javascript" src="${path}/static/H-ui/lib/jquery.validation/1.14.0/jquery.validate.js"></script>
<script type="text/javascript" src="${path}/static/H-ui/lib/jquery.validation/1.14.0/validate-methods.js"></script>
<script type="text/javascript" src="${path}/static/H-ui/lib/jquery.validation/1.14.0/messages_zh.js"></script>

<%--layui分页--%>
<script type="text/javascript" src="${path}/static/layui/layui.js"></script>
<script type="text/javascript" src="${path}/static/H-ui/common/page.js"></script>
<script type="text/javascript" src="${path}/static/H-ui/common/ajax.js"></script>
<script>
    //获取url上的参数
    function getQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    }

    $(function () {
        var userInfoJson = window.localStorage.getItem('userInfo');
        //转换对象
        var userLoginResp = jQuery.parseJSON(userInfoJson);
        var username = userLoginResp.username;
        $("#userInfo").text(username);

        var selectUrl = $(".current").find("a").attr("to-url");
        //打开url
        $("#iframe-welcome").attr("src", selectUrl);

        $(".toUrl").click(function () {
            var toUrl = $(this).attr("href");
            $("#iframe-welcome").attr("src", toUrl);
        });
    });
</script>

</html>

