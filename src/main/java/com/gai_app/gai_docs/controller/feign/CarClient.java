package com.gai_app.gai_docs.controller.feign;

import com.gai_app.gai_docs.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cars-service", url = "http://cars:8082", configuration = FeignConfig.class)  // название сервиса и его URL в Docker
public interface CarClient {
    @GetMapping("/cars/{id}")
    Object getCarById(@PathVariable Long id);
}