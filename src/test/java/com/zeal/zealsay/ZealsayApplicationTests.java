package com.zeal.zealsay;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class ZealsayApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println("------------>"+new BCryptPasswordEncoder().encode("123456"));
    }
}
