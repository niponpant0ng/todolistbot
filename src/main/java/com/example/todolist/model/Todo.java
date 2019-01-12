package com.example.todolist.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "todo")
@Data
@AllArgsConstructor
@ToString
public class Todo {
    public Todo() {
    }

    @Id
    private UUID id;
    private String todo;
    private String date;
    private String time;
}
