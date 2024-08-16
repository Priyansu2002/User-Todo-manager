package com.codejava.login.service;

import com.codejava.login.entity.Todo;
import com.codejava.login.entity.User;
import com.codejava.login.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserService userService;

    public List<Todo> getTodosByUserId(Long userId) {
        return todoRepository.findByUserId(userId);
    }

    public Todo getTodoById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Todo not found"));
    }

    public void saveTodoForUser(Todo todo) {
        todoRepository.save(todo);
    }

    public void deleteTodoById(Long id) {
        todoRepository.deleteById(id);
    }

    public void updateTodoWithUser(Todo todo, Long userId) {
        // Ensure the todo has an ID and the user association is updated
        if (todo.getId() != null && todoRepository.existsById(todo.getId())) {
            Todo existingTodo = getTodoById(todo.getId());
            existingTodo.setTitle(todo.getTitle());
            existingTodo.setDescription(todo.getDescription());
            existingTodo.setStatus(todo.getStatus());
            // Associate the existing Todo with the user
            User user = userService.getUserById(userId);
            existingTodo.setUser(user);
            saveTodoForUser(existingTodo);
        } else {
            throw new IllegalArgumentException("Todo with ID " + todo.getId() + " not found");
        }
    }
}
