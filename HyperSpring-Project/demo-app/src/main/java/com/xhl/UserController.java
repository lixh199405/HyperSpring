package com.xhl;

import com.hyperspring.annotation.HyperComponent;
import com.hyperspring.annotation.HyperInject;

/**
 * @Author: xhl
 * @Date: 2026-04-30 00:47
 * @Description:
 */
@HyperComponent
public class UserController {
    @HyperInject
    public UserService userService;

    public void handleRequest() {
        userService.sayHello();
    }
}