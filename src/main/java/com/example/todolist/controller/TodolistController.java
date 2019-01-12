package com.example.todolist.controller;

import com.example.todolist.model.Todo;
import com.example.todolist.service.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/todo")
public class TodolistController {
    @Autowired
    private TodoListService todoListService;

    @GetMapping("/")
    public List<Todo> getTodos() {
        return todoListService.getTodos();
    }
}
