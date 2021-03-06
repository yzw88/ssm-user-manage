﻿<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>角色列表</title>
    <jsp:include page="../common/top.jsp"/>
</head>
<body>

<section>
    <nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页
        <span class="c-gray en">&gt;</span>
        用户管理
        <span class="c-gray en">&gt;</span>
        用户列表
        <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px"
           href="javascript:location.replace(location.href);" title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a>
    </nav>
    <div class="Hui-article">
        <article class="cl pd-20">
            <div class="text-c">
                <form id="#form1">
                    用户名：<input type="text" class="input-text" style="width:250px" placeholder="输入昵称" id="username"
                               name="username">
                    手机号码：<input type="text" class="input-text" style="width:250px" placeholder="输入手机号码" id="mobile"
                                name="mobile">
                    <button type="button" class="btn btn-success radius" id="sub" name=""><i
                            class="Hui-iconfont">&#xe665;</i> 查询
                    </button>
                    <button class="btn btn-success radius" id="btn_clear" name="btn_clear"><i
                            class="Hui-iconfont">&#xe66b;</i> 清空
                    </button>

                </form>
            </div>
            <div class="cl pd-5 bg-1 bk-gray mt-20">
                <span class="l">

                    <a href="javascript:;" onclick="addUser()" class="btn btn-primary radius">
                    <i class="Hui-iconfont">&#xe600;</i> 添加用户</a>
                    <%--一个a配一个i--%>
                </span>
                <span class="r">共有数据：<strong id="total">0</strong> 条</span></div>
            <div class="mt-20">
                <table class="table table-border table-bordered table-bg table-hover table-sort">
                    <thead>
                    <tr class="text-c">
                        <th width="25"><input type="checkbox" name="" value=""></th>
                        <th width="80" style="display:none">ID</th>
                        <th width="80px">用id</th>
                        <th width="80px">用户名</th>
                        <th width="80px">手机号码</th>
                        <th width="80">邮箱</th>
                        <th width="120">状态</th>
                        <th width="120">操作</th>
                    </tr>
                    </thead>
                    <tbody id="tbody" class="text-c">
                    </tbody>
                </table>
                <!-- 分页标签 -->
                <div id="page1" class="pager"></div>
            </div>
        </article>
    </div>
</section>


<!--_footer 作为公共模版分离出去-->
<%--引入尾部js--%>
<jsp:include page="../common/bottom.jsp"/>
<!--/_footer /作为公共模版分离出去-->


<%--自己的--%>
<script type="text/javascript">
    function getDate() {
        var insertHtml = function (list) {
            var html = "";
            $.each(list,
                function (i, o) {
                    html += ' <tr class="text-c"> '
                    html += ' <td><input type="checkbox" value="' + o.userId + '" name=""></td> '
                    html += ' <td style="display:none">' + o.userId + '</td> '
                    html += ' <td>' + o.userId + '</td> '
                    html += ' <td>' + o.username + '</td> '
                    html += ' <td>' + o.mobile + '</td> '
                    html += ' <td>' + o.email + '</td> '
                    html += ' <td>' + o.status + '</td> '

                    html += ' <td class="td-manage"> '
                    html += ' <a title="编辑" href="javascript:;" onclick="updateUser(' + "'" + o.userId + "'" + ')" class="ml-5" style="text-decoration:none"> '
                    html += ' <i class="Hui-iconfont">&#xe6df;</i> '
                    html += ' </a> '
                    html += ' <a style="text-decoration:none" onClick="deleteUser(' + "'" + o.userId + "'" + ')" href="javascript:;" title="删除"> '
                    html += ' <i class="Hui-iconfont">&#xe6e2;</i></a> '
                    html += ' </td> '

                    html += ' </tr> ';
                });
            return html;
        };

        var username = $("#username").val();
        var mobile = $("#mobile").val();
        var pageUserQueryReq = {};
        pageUserQueryReq.username = username;
        pageUserQueryReq.mobile = mobile;

        tableInit('${path}/user/getUserPageList', 10, pageUserQueryReq, insertHtml);
    }

    getDate();

    // 提交表单
    $("#sub").click(function () {
        getDate();
    });

    //添加用户
    function addUser() {
        layer_show('添加用户信息', '${path}/view/user/userEdit', '800', '600');
    }

    //更新用户
    function updateUser(object) {
        layer_show('添加用户信息', '${path}/view/user/userEdit?userId=' + object, '800', '600');
    }

    //刷新当前页面
    function localRefresh() {
        location.href = "";
    }

    //用户编辑回调函数
    var deleteUserCallBack = function userEditCallBack() {
        layer.msg('删除成功', {icon: '-1', time: 1000});
        setTimeout(localRefresh, 1000);
    };

    //删除用户
    function deleteUser(object) {
        layer.confirm('确定要删除该用户吗？', function () {
            var userReq = {};
            userReq.userId = object;
            sendGetFormDateAjax("${path}/user/deleteUserByUserId", userReq, deleteUserCallBack);
        });
    }

</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>