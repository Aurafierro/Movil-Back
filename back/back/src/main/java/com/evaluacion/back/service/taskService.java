package com.evaluacion.back.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evaluacion.back.interfaceService.ITaskService;
import com.evaluacion.back.interfaces.ITask;
import com.evaluacion.back.models.Status;
import com.evaluacion.back.models.task;

@Service
public class taskService implements ITaskService {

    @Autowired
    private ITask data;

    @Autowired
    private emailService emailService; 

    @Override
    public String save(task task) {
        updateTaskStatus(task);
        data.save(task);
        return task.getId_task().toString();
    }

    @Override
    public List<task> findAll() {
        return (List<task>) data.findAll();
    }

    @Override
    public List<task> filtroGeneral(String filtro) {
        return data.findByTitleContaining(filtro);
    }

    @Override
    public Optional<task> findOne(Integer id_task) {
        return data.findById(id_task);
    }

    @Override
    public int delete(Integer id_task) {
        data.deleteById(id_task);
        return 1; 
    }

    public void updateTaskStatus(task task) {
        LocalDate today = LocalDate.now();

        if (task.getDueDate().isBefore(today) && task.getStatus() != Status.finalizada) {
            task.setStatus(Status.vencida);
            emailService.sendEmailNotification(task, "La tarea está vencida."); // Usando emailService
        } else if (task.getDueDate().isEqual(today) && task.getStatus() == Status.pendiente) {
            emailService.sendEmailNotification(task, "La tarea está vencida.");
        } else if (task.getDueDate().isBefore(today.plusDays(5)) && task.getStatus() == Status.pendiente) {
            emailService.sendEmailNotification(task, "La tarea está próxima a vencer.");
        }
    }
}
