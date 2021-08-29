package com.nitesh.springbootrabbitmq.controllers;

import com.nitesh.springbootrabbitmq.services.MessageSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@RestController
public class TestController {

    @Autowired
    private MessageSenderService messageSenderService;

    @PostMapping("publish")
    public String publish(@RequestBody String message) throws IOException, TimeoutException {
        messageSenderService.sendMessage(message);
        return "Success";
    }

    @GetMapping("consume")
    public String consume() throws IOException, TimeoutException {
        return messageSenderService.receiveMessage();
    }
}
