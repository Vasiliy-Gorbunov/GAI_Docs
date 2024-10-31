package com.gai_app.gai_docs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GaiDocsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GaiDocsApplication.class, args);
    }

}
