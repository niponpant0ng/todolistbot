package com.example.todolist.service;

import com.example.todolist.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TodoReposistory extends JpaRepository<Todo, UUID> {
}
