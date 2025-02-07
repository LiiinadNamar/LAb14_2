package com.example.demo.service;

import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.repository.TaskRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
  private final TaskRepository taskRepository;

  public TaskService(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  public Task createTask(Task task) {
    return taskRepository.save(task);
  }

  public List<Task> getTasksForUser(User user) {
    return taskRepository.findByUser(user);
  }

  public Optional<Task> updateTask(Task task) {
    if (taskRepository.existsById(task.getId())) {
      return Optional.of(taskRepository.save(task));
    }
    return Optional.empty();
  }

  public void deleteTask(Long taskId) {
    taskRepository.deleteById(taskId);
  }
}

