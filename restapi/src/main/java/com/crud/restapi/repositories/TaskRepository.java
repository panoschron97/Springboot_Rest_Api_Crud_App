package com.crud.restapi.repositories;

import com.crud.restapi.classes.Task;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskRepository extends JpaRepository<Task, Integer>
{
    @Modifying
    @Transactional
    @Query("UPDATE Task t SET t.id = :newId WHERE t.id = :oldId")
    int updateTaskId(@Param("oldId") int oldId, @Param("newId") int newId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Task t WHERE t.id = :oldId")
    int deleteTaskId(@Param("oldId") int oldId);
}