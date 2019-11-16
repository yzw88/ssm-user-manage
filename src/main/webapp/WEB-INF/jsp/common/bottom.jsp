<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2017/11/12
  Time: 15:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--_footer 作为公共模版分离出去-->
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

    // $(function () {
    //     var userInfoJson = window.localStorage.getItem('userInfo');
    //     //转换对象
    //     var userLoginResp = jQuery.parseJSON(userInfoJson);
    //     var username = userLoginResp.username;
    //     $("#userInfo").text(username);
    // });
</script>