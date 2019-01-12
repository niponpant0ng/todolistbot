package com.example.todolist.service;

import com.example.todolist.model.Todo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TodoListService {
    private String SEPERATE_MSG = " : ";
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yy");

    private TodoReposistory todoReposistory;

    @Autowired
    public TodoListService(TodoReposistory todoReposistory) {
        this.todoReposistory = todoReposistory;
    }

    public String createTodo(String lineMessage) {
        log.info("Create todo with {}", lineMessage);

        String[] msgs = lineMessage.split(SEPERATE_MSG);

        String todo;
        LocalDate date;
        String time;
        try {
            if(msgs[1].equals("tomorrow")) {
                date = LocalDate.now().plusDays(1);
            } else if (msgs[1].equals("today")) {
                date = LocalDate.now();
            } else {
                date = LocalDate.parse(msgs[1], formatter);
            }

            todo = msgs[0];
            if(msgs.length < 3) {
                time = "12:00";
            } else {
                time = msgs[2];
            }
        } catch (DateTimeParseException ex) {
            log.error("message {} is wrong format", lineMessage, ex);
            return "wrong format";
        }

        try {
            Todo todoList = new Todo(UUID.randomUUID(), todo, date, time);
            todoReposistory.save(todoList);
            log.info("message {} is created", todoList);

            return "your todo on " + date.format(formatter) + " : " + time + " is " + todo;
        } catch (RuntimeException ex) {
            log.error("message {} can't create", lineMessage, ex);

            return "Can't create todo";
        }
    }

    public List<Todo> getTodos() {
        return todoReposistory.findAll().stream()
                .sorted((todo1, todo2) -> todo2.getDate().compareTo(todo1.getDate()))
                .collect(Collectors.toList());
    }
}
