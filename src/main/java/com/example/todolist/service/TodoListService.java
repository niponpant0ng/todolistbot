package com.example.todolist.service;

import com.example.todolist.model.Todo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@Service
public class TodoListService {
    private String SEPERATE_MSG = " : ";
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/YY");

    private TodoReposistory todoReposistory;

    @Autowired
    public TodoListService(TodoReposistory todoReposistory) {
        this.todoReposistory = todoReposistory;
    }

    public String createTodo(String lineMessage) {
        log.info("Create todo with {}", lineMessage);

        String[] msgs = lineMessage.split(SEPERATE_MSG);
        if(msgs.length < 3) {
            log.error("message {} is wrong format", lineMessage);
            return "wrong format";
        }

        String todo = msgs[0];
        String date = msgs[1];
        String time = msgs[2];

        String todoDate;
        if(date.equals("tomorrow")) {
            todoDate = LocalDate.now().plusDays(1).format(formatter);
        } else if (date.equals("today")) {
            todoDate = LocalDate.now().format(formatter);
        } else {
            todoDate = date;
        }

        try {
            Todo todoList = new Todo(UUID.randomUUID(), todo, todoDate, time);
            todoReposistory.save(todoList);
            log.info("message {} is created", todoList);

            return "your todo on " + todoDate + " : " + time + " is " + todo;
        } catch (RuntimeException ex) {
            log.error("message {} can't create", lineMessage, ex);

            return "Can't create todo";
        }
    }
}
