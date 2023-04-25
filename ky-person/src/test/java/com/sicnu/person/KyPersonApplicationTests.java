package com.sicnu.person;

import com.sicnu.common.utils.CheckEmailOrPhone;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KyPersonApplicationTests {

    @Test
    void contextLoads() {
    }
    @Test
    void test01(){
        System.out.println(CheckEmailOrPhone.checkMobileNumber("18116699781"));
    }
}
