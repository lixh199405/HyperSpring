package com.hyperspring.core;

/**
 * @Author: xhl
 * @Date: 2026-04-29 19:51
 * @Description:
 */
import java.util.HashMap;
import java.util.Map;

public class HyperContainer {
    private final Map<Class<?>, Object> beans = new HashMap<>();

    // 注册 Bean：利用生成的工厂类
    public <T> void registerBean(Class<T> clazz) {
        try {
            // 1. 找到自动生成的工厂类
            Class<?> factoryClass = Class.forName(clazz.getName() + "$$HyperFactory");

            // 2. 调用工厂类的静态方法 create()
            // 这里虽然用了反射调用工厂，但工厂内部创建对象是完全无反射的
            Object bean = factoryClass.getMethod("create").invoke(null);

            beans.put(clazz, bean);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create bean: " + clazz.getName(), e);
        }
    }

    public <T> T getBean(Class<T> clazz) {
        return (T) beans.get(clazz);
    }
}