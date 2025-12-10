package com.clownstore.cart.config;

import com.clownstore.cart.feign.OpenFeignError;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    public ErrorDecoder errorDecoder(){
        return new OpenFeignError();
    }
}
