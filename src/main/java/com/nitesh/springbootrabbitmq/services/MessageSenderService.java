package com.nitesh.springbootrabbitmq.services;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface MessageSenderService {
    String sendMessage(String message) throws TimeoutException, IOException;
    String receiveMessage() throws TimeoutException, IOException;
}
