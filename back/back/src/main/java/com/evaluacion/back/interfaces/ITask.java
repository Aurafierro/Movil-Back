package com.evaluacion.back.interfaces;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.evaluacion.back.models.task;

@Repository
public interface ITask extends CrudRepository<task, Integer> {
   
	
	
	@Query("SELECT t FROM task t WHERE t.title LIKE %?1%")
	List<task> findByTitleContaining(String title);


    @Query("SELECT t FROM task t WHERE t.status = 'pendiente'")
    List<task> findPendingTasks();

    @Query("SELECT t FROM task t WHERE t.status = 'vencida'")
    List<task> findExpiredTasks();

    @Query("SELECT t FROM task t WHERE t.dueDate BETWEEN :startDate AND :endDate AND t.status = 'pendiente'")
    List<task> findTasksDueSoon(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
