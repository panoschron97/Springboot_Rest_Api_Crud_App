package com.crud.restapi.services;

import com.crud.restapi.classes.Task;
import com.crud.restapi.exceptions.NotFoundException;
import com.crud.restapi.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServicelmpl implements TaskService
{

    private TaskRepository taskrepository;

    @Autowired
    public TaskServicelmpl(TaskRepository taskrepository)
    {
        this.taskrepository = taskrepository;
    }

    @Override
    public Task addOrUpdateTask(Task task)
    {
        return taskrepository.save(task);
    }

    /*@Override
    public void deleteTaskById(int id)
    {
        taskrepository.deleteById(id);
    }*/

    @Override
    public Task findTaskById(int id)
    {
        Optional<Task> task = taskrepository.findById(id);
        Task task1 = null;

        if(task.isPresent())
        {
            task1 = task.get();
        }
        else
        {
            throw new NotFoundException("Task with id : " + id + " doesn't exists!");
        }

        return task1;
    }

    @Override
    public List<Task> findAllTasks()
    {

        return taskrepository.findAll();

    }

    public int updateTaskId(int oldId, int newId)
    {
        return taskrepository.updateTaskId(oldId, newId);
    }
    public int deleteTaskId(int oldId)
    {
        return taskrepository.deleteTaskId(oldId);
    }
}

