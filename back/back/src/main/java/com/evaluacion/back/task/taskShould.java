package com.evaluacion.back.task;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.evaluacion.back.models.Status;
import com.evaluacion.back.models.task;
import com.evaluacion.back.service.emailService;
import com.evaluacion.back.service.taskService;

public class taskShould {

	@Autowired
	
	private taskService data;
	
	@Autowired
    private emailService email;
	
	
	@Scheduled(cron = "0 0 8 * * ?") // A las 8 AM todos los días
    public void notificar() {
        List<task> listaTask = data.findAll();

        for (task task : listaTask) {
            if (task.getDueDate().isBefore(LocalDate.now()) && task.getStatus() == Status.vencida) {
                email.sendEmailNotification(task, "La tarea está vencida.");
            }
            if (task.getDueDate().isBefore(LocalDate.now().plusDays(5)) && task.getStatus() == Status.pendiente) {
                email.sendEmailNotification(task, "La tarea está próxima a vencer.");
            }
        }
	}
}
	 

