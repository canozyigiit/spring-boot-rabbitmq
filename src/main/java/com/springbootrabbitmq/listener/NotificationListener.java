package com.springbootrabbitmq.listener;

import com.springbootrabbitmq.model.Notification;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class NotificationListener {


    @RabbitListener(queues = "detaysoft-queue")
    public void handleMessage(Notification notification) {
        System.out.println("Message received..");
        System.out.println(notification.toString());
    }
}
