package com.gai_app.gai_docs.controller.feign;

import com.gai_app.gai_docs.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "owners", url = "http://owners:8081", configuration = FeignConfig.class)  // название сервиса и его URL в Docker
public interface OwnerClient {
    @GetMapping("/owners/{id}")
    ResponseEntity<Object> getOwnerById(@PathVariable Long id);
}
