package com.evaluacion.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.evaluacion.back.models.task;





@Service
public class emailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void enviarNotificacion(String destinatario, String subject, String cuerpo) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destinatario);
        mensaje.setSubject(subject);
        mensaje.setText(cuerpo);
        javaMailSender.send(mensaje);
    }

    public void sendEmailNotification(task task, String message) {
        String destinatario = task.getAssignedTo();
        String subject = "Notificación de Tarea";
        String cuerpo = "Tarea: " + task.getTitle() + "\n" + message;
        enviarNotificacion(destinatario, subject, cuerpo);
    }
}
