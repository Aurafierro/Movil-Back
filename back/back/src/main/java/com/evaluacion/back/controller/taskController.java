package com.evaluacion.back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.evaluacion.back.interfaceService.ITaskService;
import com.evaluacion.back.models.task;

@RestController
@RequestMapping("/api/v1/tasks")
@CrossOrigin
public class taskController {

    @Autowired
    private ITaskService taskService;

    @PostMapping("/")
    public ResponseEntity<Object> save(@RequestBody task task) {
        List<task> tasks = taskService.filtroGeneral(task.getTitle());
        if (!tasks.isEmpty()) {
            return new ResponseEntity<>("La tarea ya tiene un registro", HttpStatus.BAD_REQUEST);
        }
        if (task.getDueDate() == null) {
            return new ResponseEntity<>("La fecha es un campo obligatorio", HttpStatus.BAD_REQUEST);
        }
        
        if (task.getStatus() == null) {
            return new ResponseEntity<>("El estado es un campo obligatorio", HttpStatus.BAD_REQUEST);
        }

        taskService.save(task); // Este método ya maneja el envío de correos
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<Object> findAll() {
        List<task> listaTasks = taskService.findAll();
        if (listaTasks.isEmpty()) {
            return new ResponseEntity<>(new ErrorResponse("No se encontró ninguna tarea"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listaTasks, HttpStatus.OK);
    }

    public static class ErrorResponse {
        private String title;

        public ErrorResponse(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    @GetMapping("/busquedafiltro/{filtro}")
    public ResponseEntity<Object> findFiltro(@PathVariable String filtro) {
        List<task> listaTasks = taskService.filtroGeneral(filtro);
        return new ResponseEntity<>(listaTasks, HttpStatus.OK);
    }

    @GetMapping("/{id_task}")
    public ResponseEntity<Object> findOne(@PathVariable Integer id_task) {
        var task = taskService.findOne(id_task);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @DeleteMapping("/{id_task}")
    public ResponseEntity<Object> delete(@PathVariable Integer id_task) {
        taskService.delete(id_task);
        return new ResponseEntity<>("Tarea eliminada con éxito", HttpStatus.OK);
    }

    @PutMapping("/{id_task}")
    public ResponseEntity<Object> update(@PathVariable Integer id_task, @RequestBody task taskUpdate) {
        var existingTask = taskService.findOne(id_task).orElse(null);
        if (existingTask == null) {
            return new ResponseEntity<>("Error: tarea no encontrada", HttpStatus.BAD_REQUEST);
        }

        existingTask.setTitle(taskUpdate.getTitle());
        existingTask.setDueDate(taskUpdate.getDueDate());
        existingTask.setAssignedTo(taskUpdate.getAssignedTo());
        existingTask.setStatus(taskUpdate.getStatus());

        taskService.updateTaskStatus(existingTask); // Actualiza el estado antes de guardar
        taskService.save(existingTask); // Este método ya maneja el envío de correos
        return new ResponseEntity<>("Guardado", HttpStatus.OK);
    }
}
