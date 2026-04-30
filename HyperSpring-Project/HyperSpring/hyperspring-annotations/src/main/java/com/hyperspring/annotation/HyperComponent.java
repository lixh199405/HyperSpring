package com.hyperspring.annotation;

/**
 * @Author: xhl
 * @Date: 2026-04-29 19:01
 * @Description:
 */


import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE) // 关键：只在源码阶段存在，不进入 class 文件
public @interface HyperComponent {
    String value() default "";
}