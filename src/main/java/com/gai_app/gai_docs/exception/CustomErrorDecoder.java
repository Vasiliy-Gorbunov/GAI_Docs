package com.gai_app.gai_docs.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == HttpStatus.NOT_FOUND.value()) {
            // Вернуть исключение с детальным сообщением, если получен статус 404
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner not found for the given ID");
        }
        // Для остальных ошибок используем стандартный Feign-обработчик
        return new Default().decode(methodKey, response);
    }
}
