package com.gai_app.gai_docs.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == HttpStatus.NOT_FOUND.value()) {
            // Вернуть исключение с детальным сообщением, если получен статус 404
            throw new ResourceNotFoundException("Owner with this id not found");
        }
        // Для остальных ошибок используем стандартный Feign-обработчик
        return new Default().decode(methodKey, response);
    }
}
