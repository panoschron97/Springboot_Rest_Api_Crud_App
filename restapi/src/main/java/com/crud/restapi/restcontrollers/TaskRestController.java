package com.crud.restapi.restcontrollers;

import com.crud.restapi.classes.Task;
import com.crud.restapi.exceptions.NotFoundException;
import com.crud.restapi.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TaskRestController
{

    private TaskService taskservice;
    private ObjectMapper objectmapper;

    @Autowired
    public TaskRestController(TaskService taskservice, ObjectMapper objectmapper)
    {
        this.taskservice = taskservice;
        this.objectmapper = objectmapper;
    }
    @PostMapping("/tasks")
    public Task addTask(@RequestBody Task task)
    {
        //task.setId(null); //if you have private Integer id;
        task.setId(0);
        Task task1 = taskservice.addOrUpdateTask(task);
        return task1;
    }

    @PutMapping("/tasks")
    public Task updateTask(@RequestBody Task task)
    {
        //task.setId(null); //if you have private Integer id;
        //task.setId(0);
        Task task1 = taskservice.addOrUpdateTask(task);
        return task1;
    }

    @PutMapping("/tasks/{oldId}/id/{newId}")
    public String updateTaskId(@PathVariable int oldId, @PathVariable int newId) {

        int rows = taskservice.updateTaskId(oldId, newId);

        if (rows > 0)
        {
            return "Successfully updated task id from " + oldId + " to " + newId;

        }
        else
        {
            return "Task with id : " + oldId + " not found.";
        }
    }

    @PatchMapping("/tasks/{taskid}")
    public Task patchTask(@PathVariable int taskid, @RequestBody Map<String, Object>
            patchpayload)
    {
        Task task = taskservice.findTaskById(taskid);

        if(patchpayload.containsKey("id"))
        {
            throw new NotFoundException("taskid : " + taskid + " isn't allowed in request body!");
        }

        Task patchedtask = apply(patchpayload, task);

        Task task1 = taskservice.addOrUpdateTask(patchedtask);

        return task1;
    }

    private Task apply(Map<String, Object> patchpayload, Task task)
    {
        ObjectNode tasknode = objectmapper.convertValue(task, ObjectNode.class);

        ObjectNode patchnode = objectmapper.convertValue(patchpayload, ObjectNode.class);

        tasknode.setAll(patchnode);

        return objectmapper.convertValue(tasknode, Task.class);

    }

    @GetMapping("/tasks/{taskid}")
    public Task getTask(@PathVariable int taskid)
    {
        Task task = taskservice.findTaskById(taskid);

        return task;

    }

    @GetMapping("/tasks")
    public List<Task> findAllTasks()
    {

        return taskservice.findAllTasks();

    }

    @DeleteMapping("/tasks/{taskid}")
    public String deleteTask(@PathVariable int taskid)
    {
        Task task = taskservice.findTaskById(taskid);

        //taskservice.deleteTaskById(taskid);

        taskservice.deleteTaskId(taskid);

        return "Task with id : " + taskid + " deleted successfully!";
    }

}
