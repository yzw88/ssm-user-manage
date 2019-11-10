package pers.can.manage.common.enums;

/**
 * 用户状态枚举
 *
 * @author Waldron Ye
 * @date 2019/11/10 11:28
 */
public enum UserStatusEnum {

    /**
     * 启用
     */
    ENABLE("1"),

    /***
     * 禁用
     */
    DISABLE("0");

    String code;

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    UserStatusEnum(String code) {
        this.code = code;
    }
}
