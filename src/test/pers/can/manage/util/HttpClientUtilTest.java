package pers.can.manage.util;

public class HttpClientUtilTest {

    public static void main(String[] args) {
        String json = "{\n" +
                "\t\"productName\":\"产品名称\"\n" +
                "}";
        String url = "http://localhost:8080/validCheck";
        String result = HttpClientUtil.doPost(url, json);
        System.out.println("请求结果:" + result);
    }
}