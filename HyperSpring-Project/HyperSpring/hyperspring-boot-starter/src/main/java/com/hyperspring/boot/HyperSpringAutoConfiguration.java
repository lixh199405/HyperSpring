package com.hyperspring.boot;

/**
 * @Author: xhl
 * @Date: 2026-04-29 20:01
 * @Description:
 */

import com.hyperspring.core.HyperContainer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(HyperContainer.class)
public class HyperSpringAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public HyperContainer hyperContainer() {
        return new HyperContainer();
    }
}