package com.example.todolist.controller;

import com.example.todolist.model.Todo;
import com.example.todolist.service.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/todo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TodolistController {
    @Autowired
    private TodoListService todoListService;

    @GetMapping
    public List<Todo> getTodos() {
        return todoListService.getTodos();
    }

    @PutMapping("/{id}/isImportant")
    public Todo isImportantTodo(@PathVariable("id") UUID id) {
        return todoListService.isImportant(id);
    }

    @PutMapping("/{id}/isFinished")
    public Todo isFinishedTodo(@PathVariable("id") UUID id) {
        return todoListService.isFinished(id);
    }
}
