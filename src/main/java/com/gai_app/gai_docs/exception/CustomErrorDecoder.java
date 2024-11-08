package com.gai_app.gai_docs.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == HttpStatus.NOT_FOUND.value()) {
            // Вернуть исключение с детальным сообщением, если получен статус 404
            // Регулярное выражение для поиска числа после /owners/
            Pattern pattern = Pattern.compile("/owners/(\\d+)");
            Matcher matcher = pattern.matcher(response.request().toString());
            String message;
            if (matcher.find()) {
                String ownerId = matcher.group(1);
                message = "Owner with id " + ownerId + " not found";
            } else {
                message = "Not found exception";
            }
            throw new ResourceNotFoundException(message);
        }
        // Для остальных ошибок используем стандартный Feign-обработчик
        return new Default().decode(methodKey, response);
    }
}
