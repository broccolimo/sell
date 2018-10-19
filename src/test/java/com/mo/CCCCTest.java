package com.mo;



import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author 音神
 * @date 2018/10/11 9:29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CCCCTest {

    @Test
    public void c1() {
        log.trace("trace44...");
        log.debug("debug44...");
        log.info("info44...");
        log.warn("warn44...");
        log.error("error44...");
    }
}