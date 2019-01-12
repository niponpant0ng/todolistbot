package com.example.todolist.service;

import com.example.todolist.model.Todo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class TodolistServiceTest {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yy");

    @Mock
    private TodoReposistory todoReposistory;

    @InjectMocks
    private TodoListService todoListService;

    @Test
    public void testCreateTodoWhenWrongFormat() {
        String msg = "Buy milk : 13:00";

        String txt = todoListService.createTodo(msg);

        assertThat(txt, is("wrong format"));
    }

    @Test
    public void testCreateTodoWhenCanNotCreateTodo() {
        String msg = "Buy milk : tomorrow : 13:00";
        doThrow(RuntimeException.class).when(todoReposistory).save(any(Todo.class));

        String txt = todoListService.createTodo(msg);

        assertThat(txt, is("Can't create todo"));
    }

    @Test
    public void testCreateTodoWhenTimeIsEmpty() {
        String msg = "Buy milk : 2/5/18";
        ArgumentCaptor<Todo> todoArgumentCaptor = ArgumentCaptor.forClass(Todo.class);
        doReturn(null).when(todoReposistory).save(todoArgumentCaptor.capture());

        String txt = todoListService.createTodo(msg);

        LocalDate expectDate = LocalDate.parse("2/5/18", formatter);
        assertThat(txt, is("your todo on 2/5/18 : 12:00 is Buy milk"));
        assertThat(todoArgumentCaptor.getValue().getTodo(), is("Buy milk"));
        assertThat(todoArgumentCaptor.getValue().getDate(), is(expectDate));
        assertThat(todoArgumentCaptor.getValue().getTime(), is("12:00"));
    }

    @Test
    public void testCreateTodoWhenFormatDateIsDateFormat() {
        String msg = "Buy milk : 2/5/18 : 13:00";
        ArgumentCaptor<Todo> todoArgumentCaptor = ArgumentCaptor.forClass(Todo.class);
        doReturn(null).when(todoReposistory).save(todoArgumentCaptor.capture());

        String txt = todoListService.createTodo(msg);

        LocalDate expectDate = LocalDate.parse("2/5/18", formatter);
        assertThat(txt, is("your todo on 2/5/18 : 13:00 is Buy milk"));
        assertThat(todoArgumentCaptor.getValue().getTodo(), is("Buy milk"));
        assertThat(todoArgumentCaptor.getValue().getDate(), is(expectDate));
        assertThat(todoArgumentCaptor.getValue().getTime(), is("13:00"));
    }

    @Test
    public void testCreateTodoWhenFormatDateIsTodayText() {
        String msg = "Buy milk : today : 13:00";
        ArgumentCaptor<Todo> todoArgumentCaptor = ArgumentCaptor.forClass(Todo.class);
        doReturn(null).when(todoReposistory).save(todoArgumentCaptor.capture());

        String txt = todoListService.createTodo(msg);

        LocalDate expectDate = LocalDate.now();
        assertThat(txt, is("your todo on " + expectDate.format(formatter) + " : 13:00 is Buy milk"));
        assertThat(todoArgumentCaptor.getValue().getTodo(), is("Buy milk"));
        assertThat(todoArgumentCaptor.getValue().getDate(), is(expectDate));
        assertThat(todoArgumentCaptor.getValue().getTime(), is("13:00"));
    }

    @Test
    public void testCreateTodoWhenFormatDateIsTomorowText() {
        String msg = "Buy milk : tomorrow : 13:00";
        ArgumentCaptor<Todo> todoArgumentCaptor = ArgumentCaptor.forClass(Todo.class);
        doReturn(null).when(todoReposistory).save(todoArgumentCaptor.capture());

        String txt = todoListService.createTodo(msg);

        LocalDate expectDate = LocalDate.now().plusDays(1);
        assertThat(txt, is("your todo on " + expectDate.format(formatter) + " : 13:00 is Buy milk"));
        assertThat(todoArgumentCaptor.getValue().getTodo(), is("Buy milk"));
        assertThat(todoArgumentCaptor.getValue().getDate(), is(expectDate));
        assertThat(todoArgumentCaptor.getValue().getTime(), is("13:00"));
    }

    @Test
    public void testGetTodos() {
        Todo todo1 = new Todo(UUID.randomUUID(), "test", LocalDate.now(), "13:00");
        Todo todo2 = new Todo(UUID.randomUUID(), "test", LocalDate.now(), "14:00");
        Todo todo3 = new Todo(UUID.randomUUID(), "test", LocalDate.now().plusDays(1), "13:00");
        List<Todo> todos = Arrays.asList(todo1, todo2, todo3);
        doReturn(todos).when(todoReposistory).findAll();

        List<Todo> todoList = todoListService.getTodos();

        assertThat(todoList.get(0), equalTo(todo3));
        assertThat(todoList.get(1), equalTo(todo1));
        assertThat(todoList.get(2), equalTo(todo2));
    }
}
