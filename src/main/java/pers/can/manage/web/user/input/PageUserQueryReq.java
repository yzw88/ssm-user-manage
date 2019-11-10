package pers.can.manage.web.user.input;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.can.manage.common.dto.BasePageQueryDTO;

/**
 * 用户分页查询对象
 *
 * @author Waldron Ye
 * @date 2019/11/10 13:46
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PageUserQueryReq extends BasePageQueryDTO {
    private static final long serialVersionUID = -8798107207439814599L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 手机号码
     */
    private String mobile;
}
