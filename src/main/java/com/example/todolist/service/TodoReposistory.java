package com.example.todolist.service;

import com.example.todolist.model.Todo;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TodoReposistory extends CrudRepository<Todo, UUID> {
}
