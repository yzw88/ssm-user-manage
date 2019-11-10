package pers.can.manage.web.user.output;

import lombok.Data;

import java.io.Serializable;

/**
 * 图形验证码响应对象
 *
 * @author Waldron Ye
 * @date 2019/11/10 11:08
 */
@Data
public class ImgValidCodeResp implements Serializable {
    private static final long serialVersionUID = -308320177470086576L;

    /**
     * base64图形验证码
     */
    private String base64Code;

    /**
     * 图形验证码缓存中的key
     */
    private String codeKey;

}
