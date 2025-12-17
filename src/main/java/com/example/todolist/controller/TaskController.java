package com.example.todolist.controller;


import com.example.todolist.entity.Task;
import com.example.todolist.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST responsável pelas operações CRUD de tarefas.
 * <p>
 * Todos os endpoints estão protegidos por autenticação básica (configurada em SecurityConfig).
 * Base path: /api/tasks
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    /**
     * Injeção de dependência via construtor (padrão recomendado).
     */
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Lista todas as tarefas cadastradas.
     *
     * @return Lista de tarefas
     */
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    /**
     * Busca uma tarefa específica pelo ID.
     *
     * @param id ID da tarefa
     * @return ResponseEntity com a tarefa (200 OK) ou 404 se não encontrada
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria uma nova tarefa.
     * <p>
     * Valida os campos da entidade com Bean Validation (@NotBlank na entidade).
     *
     * @param task Dados da tarefa recebidos no corpo da requisição
     * @return A tarefa criada com ID gerado e status 201 Created
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@Valid @RequestBody Task task) {
        return taskService.createTask(task);
    }

    /**
     * Atualiza uma tarefa existente.
     *
     * @param id ID da tarefa a ser atualizada
     * @param taskDetails Dados atualizados
     * @return A tarefa atualizada (200 OK)
     */
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id,
                                           @Valid @RequestBody Task taskDetails) {
        Task updatedTask = taskService.updateTask(id, taskDetails);
        return ResponseEntity.ok(updatedTask);
    }

    /**
     * Remove uma tarefa pelo ID.
     *
     * @param id ID da tarefa a ser deletada
     * @return ResponseEntity sem conteúdo (204 No Content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

}
