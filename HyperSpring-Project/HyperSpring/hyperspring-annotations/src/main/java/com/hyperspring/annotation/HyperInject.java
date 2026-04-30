package com.hyperspring.annotation;

/**
 * @Author: xhl
 * @Date: 2026-04-29 19:01
 * @Description:
 */


import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface HyperInject {
}