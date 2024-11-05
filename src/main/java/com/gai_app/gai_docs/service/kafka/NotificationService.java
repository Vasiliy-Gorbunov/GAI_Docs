package com.gai_app.gai_docs.service.kafka;

import com.gai_app.gai_docs.model.LicenseModel;
import com.gai_app.gai_docs.model.PassportModel;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public NotificationService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void getLicenseModelCreateMessageAndSend(LicenseModel model, String cause) {
        String message = "License with id owner " + model.getOwnerId() + " has been " + cause + "\n"
                + "License: " + model;
        sendNotification(message);
    }

    public void getPassportModelCreateMessageAndSend(PassportModel model, String cause) {
        String message = "Passport with id owners " + model.getOwnersId() + " has been " + cause + "\n"
                + "Passport: " + model;
        sendNotification(message);
    }

    public void sendNotification(String message) {
        kafkaTemplate.send("notification-topic", message);
    }
}