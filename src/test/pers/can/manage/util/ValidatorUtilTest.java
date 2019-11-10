package pers.can.manage.util;

import pers.can.manage.web.common.input.PayReq;

public class ValidatorUtilTest {

    public static void main(String[] args) {

        PayReq  payReq = new PayReq();
        payReq.setProductName("");

        ValidatorUtil.validateObject(payReq);
    }
}