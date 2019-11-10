package pers.can.manage.util;

import com.github.pagehelper.PageInfo;
import pers.can.manage.common.dto.PageResp;
import pers.can.manage.common.dto.ResponseDataDTO;
import pers.can.manage.common.enums.ResultEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * 返回数据组装工具类
 *
 * @author Waldron Ye
 * @date 2019/4/12 22:42
 */
public class ResultUtil {


    /**
     * 获取成功信息
     *
     * @param data object data
     * @param <T>  object
     * @return responseDataDTO
     */
    public static <T> ResponseDataDTO<T> getSuccessResult(T data) {
        ResponseDataDTO<T> responseDataDTO = new ResponseDataDTO<>();
        responseDataDTO.setCode(ResultEnum.SUCCESS.getCode());
        responseDataDTO.setMessage(ResultEnum.SUCCESS.getMsg());
        responseDataDTO.setData(data);
        return responseDataDTO;
    }

    /**
     * 获取信息
     *
     * @param resultEnum resultEnum
     * @param <T>        object
     * @return responseDataDTO
     */
    public static <T> ResponseDataDTO<T> getResult(ResultEnum resultEnum) {
        ResponseDataDTO<T> responseDataDTO = new ResponseDataDTO<>();
        responseDataDTO.setCode(resultEnum.getCode());
        responseDataDTO.setMessage(resultEnum.getMsg());
        return responseDataDTO;
    }

    /**
     * 获取信息
     *
     * @param code    响应编码
     * @param message 消息
     * @param <T>     object
     * @return responseDataDTO
     */
    public static <T> ResponseDataDTO<T> getResult(int code, String message) {
        ResponseDataDTO<T> responseDataDTO = new ResponseDataDTO<>();
        responseDataDTO.setCode(code);
        responseDataDTO.setMessage(message);
        return responseDataDTO;
    }

    /**
     * 获取系统异常信息
     *
     * @param msg 异常信息
     * @param <T> object
     * @return responseDataDTO
     */
    public static <T> ResponseDataDTO<T> getSystemErrorResult(String msg) {
        ResponseDataDTO<T> responseDataDTO = new ResponseDataDTO<>();
        responseDataDTO.setCode(ResultEnum.ERROR_SYS.getCode());
        responseDataDTO.setMessage(msg);
        return responseDataDTO;
    }

    /**
     * 获取服务异常信息
     *
     * @param <T> object
     * @return responseDataDTO
     */
    public static <T> ResponseDataDTO<T> getServiceErrorResult() {
        ResponseDataDTO<T> responseDataDTO = new ResponseDataDTO<>();
        responseDataDTO.setCode(ResultEnum.ERROR_SERVICE.getCode());
        responseDataDTO.setMessage(ResultEnum.ERROR_SERVICE.getMsg());
        return responseDataDTO;
    }

    /**
     * 获取分页数据
     *
     * @param pageList list
     * @param <T>      object
     * @return responseDataDTO
     */
    public static <T> ResponseDataDTO<PageResp<T>> getPageRespResult(List<T> pageList) {
        ResponseDataDTO<PageResp<T>> responseDataDTO = new ResponseDataDTO<>();
        responseDataDTO.setCode(ResultEnum.SUCCESS.getCode());
        responseDataDTO.setMessage(ResultEnum.SUCCESS.getMsg());

        PageInfo<T> pageInfo = new PageInfo<>(pageList == null || pageList.isEmpty() ? new ArrayList<>() : pageList);
        PageResp<T> pageResp = new PageResp<>();
        pageResp.setList(pageInfo.getList());
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setPageNum(pageInfo.getPageNum());
        pageResp.setPageSize(pageInfo.getPageSize());

        responseDataDTO.setData(pageResp);
        return responseDataDTO;
    }
}
