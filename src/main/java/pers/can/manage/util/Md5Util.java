package pers.can.manage.util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * md5工具类
 *
 * @author Waldron Ye
 * @date 2018/12/16 10:08
 */
public class Md5Util {

    /**
     * md5加密
     *
     * @param plainText 待加密的字符串
     * @return
     */
    public static String md5To32Encrypt(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer();
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String md5To16Encrypt(String plainText) {
        return Objects.requireNonNull(md5To32Encrypt(plainText)).substring(8, 24);
    }

}