package com.xhl;

import com.hyperspring.core.HyperContainer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: xhl
 * @Date: 2026-04-30 00:50
 * @Description:
 */
@SpringBootApplication
public class DemoApp {
    public static void main(String[] args) {
        var context = SpringApplication.run(DemoApp.class, args);
        var container = context.getBean(HyperContainer.class);

        // 注册并获取 Bean
        container.registerBean(UserController.class);
        UserController controller = container.getBean(UserController.class);

        controller.handleRequest();
    }
}
