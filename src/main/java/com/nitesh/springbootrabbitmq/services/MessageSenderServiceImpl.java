package com.nitesh.springbootrabbitmq.services;

import com.rabbitmq.client.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
public class MessageSenderServiceImpl implements MessageSenderService {

    private String QUEUE_NAME = "rabbitmq-sample";

    @Override
    public String sendMessage(String message) throws TimeoutException, IOException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,
                false,
                false,
                false,
                null);
        channel.basicPublish("",
                QUEUE_NAME,
                null,
                message.getBytes("UTF-8"));
        channel.close();
        connection.close();
        return "Success";
    }

    @Override
    public String receiveMessage() throws TimeoutException, IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,
                false, false,false, null);
        System.out.println("[!] Waiting for messages. To exit press Ctrl+C");

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("[x] Message Received' " + message + "'");
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
        return null;
    }

}
