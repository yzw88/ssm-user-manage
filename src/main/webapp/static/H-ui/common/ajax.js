(function ($) {
    var _ajaxUrl,
        _ajaxJsonData = {},
        //从localStorage中获取token
        _token = window.localStorage.getItem('AuthorizationToken'),
        _contentType,
        _baseUrl = window.document.location.href.split("/view/")[0],
        _cb;

    /**
     * get ajax表单提交请求
     * @param url 请求url
     * @param ajaxData 请求数据(非json格式字符串，使用对象格式)
     * @param cb 回调函数
     */
    function sendGetFormDateAjax(url, ajaxData, cb) {
        _ajaxUrl = url;
        _ajaxJsonData = ajaxData;
        _contentType = "multipart/form-data";
        _cb = cb;
        sendAjax('get');
    }

    /**
     * post ajax请求
     * @param url 请求url
     * @param ajaxData 请求数据(非json格式字符串，使用对象格式)
     * @param cb 回调函数
     */
    function sendPostAjax(url, ajaxData, cb) {
        _ajaxUrl = url;
        _ajaxJsonData = typeof (ajaxData) == "undefined" ? _ajaxJsonData : JSON.stringify(ajaxData);
        _cb = cb;
        _contentType = "application/json;charset=utf-8";
        sendAjax('post');
    }

    /**
     * 请求ajax
     * @param type 请求类型
     */
    function sendAjax(type) {
        $.ajax({
            url: _ajaxUrl,
            data: _ajaxJsonData,
            contentType: _contentType,
            type: type,
            dataType: 'json',
            beforeSend: function (request) {
                //添加请求头
                request.setRequestHeader("AuthorizationToken", _token);
            },
            success: function (result, status, xhr) {
                //1000表示用户登录异常，需要重新登录
                if (result.code === 1000) {
                    layer.msg(result.message, {icon: '-1', time: 1000});
                    //从定向到登录页面
                    top.location.href = _baseUrl + "/view/user/login";
                    return;
                }
                //响应头部处理
                var token = xhr.getResponseHeader("AuthorizationToken");
                if (token != null && typeof (token) != "undefined") {
                    console.log("响应头部token为:" + token);
                    window.localStorage.setItem("AuthorizationToken", token);
                }

                //9999表示成功
                if (result.code === 9999) {
                    //出发回调函数处理
                    if (typeof (_cb) != "undefined" && _cb != null) {
                        _cb(result);
                        return;
                    }
                    //没有回调函数暂不处理
                    return;
                }
                //其它状态则弹出文本框
                layer.msg(result.message, {icon: '-1', time: 1000});
            }
        });
    }

    window.sendGetFormDateAjax = sendGetFormDateAjax;
    window.sendPostAjax = sendPostAjax;
}(jQuery));