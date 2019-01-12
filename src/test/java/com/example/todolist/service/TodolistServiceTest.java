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

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class TodolistServiceTest {
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
    public void testCreateTodoWhenFormatDateIsDateFormat() {
        String msg = "Buy milk : 2/5/18 : 13:00";
        ArgumentCaptor<Todo> todoArgumentCaptor = ArgumentCaptor.forClass(Todo.class);
        doReturn(null).when(todoReposistory).save(todoArgumentCaptor.capture());

        String txt = todoListService.createTodo(msg);

        assertThat(txt, is("your todo on 2/5/18 : 13:00 is Buy milk"));
        assertThat(todoArgumentCaptor.getValue().getTodo(), is("Buy milk"));
        assertThat(todoArgumentCaptor.getValue().getDate(), is("2/5/18"));
        assertThat(todoArgumentCaptor.getValue().getTime(), is("13:00"));
    }

    @Test
    public void testCreateTodoWhenFormatDateIsTodayText() {
        String msg = "Buy milk : today : 13:00";
        ArgumentCaptor<Todo> todoArgumentCaptor = ArgumentCaptor.forClass(Todo.class);
        doReturn(null).when(todoReposistory).save(todoArgumentCaptor.capture());

        String txt = todoListService.createTodo(msg);

        String expectDate = LocalDate.now().format(DateTimeFormatter.ofPattern("d/M/YY"));
        assertThat(txt, is("your todo on " + expectDate + " : 13:00 is Buy milk"));
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

        String expectDate = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("d/M/YY"));
        assertThat(txt, is("your todo on " + expectDate + " : 13:00 is Buy milk"));
        assertThat(todoArgumentCaptor.getValue().getTodo(), is("Buy milk"));
        assertThat(todoArgumentCaptor.getValue().getDate(), is(expectDate));
        assertThat(todoArgumentCaptor.getValue().getTime(), is("13:00"));
    }
}
