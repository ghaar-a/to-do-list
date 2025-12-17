package com.example.todolist.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade que representa uma tarefa no sistema To-Do List.
 * Mapeada para a tabela "task" no banco de dados PostgreSQL.
 */
@Entity
@Table(name = "task")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Título da tarefa – obrigatório.
     */
    @NotBlank(message = "O título da tarefa é obrigatório")
    @Size(max = 100, message = "O título não pode ter mais de 100 caracteres")
    private String title;

    /**
     * Descrição detalhada da tarefa – opcional.
     */
    @Size(max = 500, message = "A descrição não pode ter mais de 500 caracteres")
    private String description;

    /**
     * Indica se a tarefa foi concluída.
     * Valor padrão: false (não concluída)
     */
    private boolean completed = false;

    /**
     * Categoria da tarefa (ex: Trabalho, Pessoal, Estudos, Casa).
     * Feature extra para organizar melhor as tarefas.
     */
    @Size(max = 50, message = "A categoria não pode ter mais de 50 caracteres")
    private String category;
}