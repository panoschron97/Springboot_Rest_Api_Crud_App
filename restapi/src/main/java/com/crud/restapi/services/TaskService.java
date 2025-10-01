package com.crud.restapi.services;

import com.crud.restapi.classes.Task;

import java.util.List;

public interface TaskService
{
    Task addOrUpdateTask(Task task);

    int deleteTaskId(int id);

    //void deleteTaskById(int id);

    Task findTaskById(int id);

    List<Object/*Task*/> findAllTasks();

    int updateTaskId(int oldId, int newId);
}
