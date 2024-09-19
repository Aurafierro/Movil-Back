package com.evaluacion.back.interfaceService;

import java.util.List;
import java.util.Optional;

import com.evaluacion.back.models.task;


public interface ITaskService {

	
	    public String save(task task);
	    public List<task> findAll();
	    public List<task> filtroGeneral(String filtro);
	
	    public int delete(Integer id_task);
		public Optional<task> findOne(Integer id_task);
		public void updateTaskStatus(task existingTask);

}
