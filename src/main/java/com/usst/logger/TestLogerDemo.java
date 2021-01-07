package com.usst.logger;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class TestLogerDemo {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(TestLogerDemo.class);
        logger.debug("Hello world");

        // 打印内部的状态

    }

}
