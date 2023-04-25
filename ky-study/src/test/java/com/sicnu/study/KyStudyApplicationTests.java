package com.sicnu.study;

import com.sicnu.common.utils.JasyptUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KyStudyApplicationTests {

    @Test
    void contextLoads() {
    }
    
    @Test
    void encrypt()
    {
        JasyptUtil.encrypt("root", "1234", "sicnu");
    }

}
