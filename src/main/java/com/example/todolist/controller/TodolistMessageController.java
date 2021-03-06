package com.example.todolist.controller;

import com.example.todolist.service.TodoListService;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@LineMessageHandler
public class TodolistMessageController {
    @Autowired
    private TodoListService todoListService;

    @EventMapping
    public Message handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        log.info("Incomming event is {}", event);
        final String originalMessageText = event.getMessage().getText();
        return new TextMessage(todoListService.createTodo(originalMessageText));
    }
}
