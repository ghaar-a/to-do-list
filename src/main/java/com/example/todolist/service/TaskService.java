package com.example.todolist.service;

import com.example.todolist.entity.Task;
import com.example.todolist.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável pela lógica de negócios relacionada às tarefas.
 * Contém operações de CRUD e integração com envio de email ao criar uma nova tarefa.
 */
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final JavaMailSender mailSender;

    /**
     * Injeção via construtor (prática recomendada pelo Spring a partir da versão 4.3+).
     * Evita o uso de @Autowired em campos e facilita testes unitários.
     */
    public TaskService(TaskRepository taskRepository, JavaMailSender mailSender) {
        this.taskRepository = taskRepository;
        this.mailSender = mailSender;
    }

    /**
     * Retorna todas as tarefas cadastradas.
     */
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    /**
     * Busca uma tarefa pelo ID.
     *
     * @param id ID da tarefa
     * @return Optional contendo a tarefa, ou vazio se não existir
     */
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    /**
     * Cria uma nova tarefa e envia notificação por email.
     *
     * @param task Objeto com os dados da nova tarefa
     * @return A tarefa salva com ID gerado
     */
    public Task createTask(Task task) {
        Task savedTask = taskRepository.save(task);

        // Feature extra: notificação imediata ao criar tarefa
        sendNotificationEmail(savedTask);

        return savedTask;
    }

    /**
     * Atualiza uma tarefa existente.
     *
     * @param id ID da tarefa a ser atualizada
     * @param taskDetails Dados atualizados da tarefa
     * @return A tarefa atualizada
     * @throws EntityNotFoundException se a tarefa não existir
     */
    public Task updateTask(Long id, Task taskDetails) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada com ID: " + id));

        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setCompleted(taskDetails.isCompleted());
        task.setCategory(taskDetails.getCategory());

        return taskRepository.save(task);
    }

    /**
     * Remove uma tarefa pelo ID.
     *
     * @param id ID da tarefa a ser deletada
     */
    public void deleteTask(Long id) {
        // Poderia validar existência antes, mas deleteById é idempotente
        taskRepository.deleteById(id);
    }

    // <p>
    // TODO: Em produção, o destinatário deveria vir de configuração externa
    // (application.properties) ou ser associado ao usuário autenticado.
    // </p>
    private void sendNotificationEmail(Task task) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("todolist@exemplo.com"); // Recomendado definir um from fixo
        message.setTo("seuemail@gmail.com");     // Troque pelo seu email real para testes
        message.setSubject("Nova tarefa criada: " + task.getTitle());
        message.setText("""
                Uma nova tarefa foi adicionada ao seu To-Do List!

                Título: %s
                Descrição: %s
                Categoria: %s
                Concluída: %s

                Acesse a aplicação para gerenciar suas tarefas.
                """.formatted(
                task.getTitle(),
                task.getDescription() != null ? task.getDescription() : "Sem descrição",
                task.getCategory() != null ? task.getCategory() : "Sem categoria",
                task.isCompleted() ? "Sim" : "Não"
        ));

        mailSender.send(message);
    }
}