package pers.can.manage.web.common.input;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 支付传输对象
 *
 * @author Waldron Ye
 * @date 2019/6/9 22:45
 */
@Data
public class PayReq implements Serializable {
    private static final long serialVersionUID = 5386113917848090105L;

    @NotNull(message = "产品名称不能为空")
    @Size(min = 2, max = 255, message = "产品名称长度非法")
    private String productName;

    @NotNull(message = "金额不能为空")
    @Min(value = 1, message = "金额非法")
    private BigDecimal amount;

    @NotNull(message = "交易类型不能为空")
    private String tradeType;
}
