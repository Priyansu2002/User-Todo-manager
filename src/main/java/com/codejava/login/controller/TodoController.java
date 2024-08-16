package com.codejava.login.controller;

import com.codejava.login.entity.Todo;
import com.codejava.login.entity.User;
import com.codejava.login.service.TodoService;
import com.codejava.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users/{userId}/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String viewTodos(@PathVariable Long userId, Model model) {
        List<Todo> todos = todoService.getTodosByUserId(userId);
        model.addAttribute("todos", todos);
        model.addAttribute("userId", userId);
        return "todo-list";
    }

    @GetMapping("/add")
    public String addTodoForm(@PathVariable Long userId, Model model) {
        Todo todo = new Todo();
        model.addAttribute("todo", todo);
        model.addAttribute("userId", userId);
        return "todo-form";
    }

    @PostMapping("/add")
    public String addTodo(@PathVariable Long userId, @ModelAttribute Todo todo) {
        User user = userService.getUserById(userId);
        todo.setUser(user);
        todoService.saveTodoForUser(todo);
        return "redirect:/users/" + userId + "/todos";
    }

    @GetMapping("/edit/{todoId}")
    public String editTodoForm(@PathVariable Long userId, @PathVariable Long todoId, Model model) {
        Todo todo = todoService.getTodoById(todoId);
        model.addAttribute("todo", todo);
        model.addAttribute("userId", userId);
        return "todo-form";
    }

    @PostMapping("/edit/{todoId}")
    public String updateTodo(@PathVariable Long userId, @PathVariable Long todoId, @ModelAttribute Todo todo) {
        // Set the ID of the todo to ensure the correct todo is updated
        todo.setId(todoId);
        todoService.updateTodoWithUser(todo, userId);
        return "redirect:/users/" + userId + "/todos";
    }

    @GetMapping("/delete/{todoId}")
    public String deleteTodo(@PathVariable Long userId, @PathVariable Long todoId) {
        todoService.deleteTodoById(todoId);
        return "redirect:/users/" + userId + "/todos";
    }
}
