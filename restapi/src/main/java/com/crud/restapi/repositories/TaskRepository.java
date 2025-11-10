package com.crud.restapi.repositories;

import com.crud.restapi.classes.Information;
import com.crud.restapi.classes.Task;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer>
{
    @Query("SELECT t FROM Task t")
    List<Object> selectTask();

    @Query("SELECT t FROM Task t WHERE t.id = :id")
    Task selectTaskById(@Param("id") int id);

    @Modifying
    @Transactional
    @Query("UPDATE Task t SET t.id = :newId WHERE t.id = :oldId")
    int updateTaskId(@Param("oldId") int oldId, @Param("newId") int newId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Task t WHERE t.id = :oldId")
    int deleteTaskId(@Param("oldId") int oldId);
}