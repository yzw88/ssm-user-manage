package pers.can.manage.web.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import pers.can.manage.util.Base64Util;
import pers.can.manage.util.Md5Util;
import pers.can.manage.util.ValidCodeUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
public class UserControllerTest {

    @Test
    public void test() throws IOException {
        int height = 40;
        int width = 150;
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        String randomText = ValidCodeUtil.drawRandomText(width, height, bufferedImage);
        log.info("验证码为:randomText={}", randomText);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", outputStream);
        String base64Str = "data:image/jpg;base64," + Base64Util.encode(outputStream.toByteArray());
        log.info("base64Str={}", base64Str);

        String str = Md5Util.md5To32Encrypt(randomText);
        log.info("加密后:{}", str);

    }

    /**
     * 获取用户密码
     */
    @Test
    public void getUserPassword() {
        String salt = "SqGgmCWQzA9Jh4GrEawC";
        String password = "123456";
        String password2 = Md5Util.md5To16Encrypt(salt + password);
        log.info("生成的密码为:password2={}",password2);
    }

}