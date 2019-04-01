 package org.wubin.chat.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
  * 启动函数
 * @author wubin
 * @date 2019/03/14
 */
@SpringBootApplication
@ComponentScan({"org.wubin.chat.server"})
public class ServerMain {

    public static void main(String[] args) {
        SpringApplication.run(ServerMain.class, args);
    }
}
