package com.example.todolist.repository;

import com.example.todolist.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório JPA para a entidade Task.
 * Fornece operações CRUD automaticamente pelo Spring Data JPA.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // Não implementar nada – JpaRepository já tem save, findAll, findById, deleteById etc.
}
