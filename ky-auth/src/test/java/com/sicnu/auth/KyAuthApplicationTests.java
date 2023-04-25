package com.sicnu.auth;

import com.sicnu.common.utils.RsaUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import java.security.PublicKey;

@SpringBootTest
class KyAuthApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void test() throws Exception {
        RsaUtils.generateKey("C:\\Users\\25654\\Desktop\\SECURITY\\rsa_key.pub"
        ,"C:\\Users\\25654\\Desktop\\SECURITY\\rsa_key",
        "kaoyan",
                2048);
    }
    @Test
    void test1() throws Exception {
        PublicKey publicKey = RsaUtils.getPublicKey("C:\\Users\\25654\\Desktop\\SECURITY\\rsa_key.pub");
        System.out.println(publicKey);
    }

    @Test
    void test2(){
        System.out.println(111);
    }
}
