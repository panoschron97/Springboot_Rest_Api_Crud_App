package com.crud.restapi.restcontrollers;

import com.crud.restapi.classes.Department;
import com.crud.restapi.exceptions.NotFoundException;
import com.crud.restapi.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DepartmentRestController
{

    private DepartmentService departmentservice;
    private ObjectMapper objectmapper;

    @Autowired
    public DepartmentRestController(DepartmentService departmentservice, ObjectMapper objectmapper)
    {
        this.departmentservice = departmentservice;
        this.objectmapper = objectmapper;
    }
    @PostMapping("/departments")
    public Department addDepartment(@RequestBody Department department)
    {
        //department.setId(null); //if you have private Integer id;
        department.setId(0);
        Department department1 = departmentservice.addOrUpdateDepartment(department);
        return department1;
    }

    @PutMapping("/departments")
    public Department updateDepartment(@RequestBody Department department)
    {
        //department.setId(null); //if you have private Integer id;
        //department.setId(0);
        Department department1 = departmentservice.addOrUpdateDepartment(department);
        return department1;
    }

    @PutMapping("/departments/{oldId}/id/{newId}")
    public String updateDepartmentId(@PathVariable int oldId, @PathVariable int newId) {

        int rows = departmentservice.updateDepartmentId(oldId, newId);

        if (rows > 0)
        {
            return "Successfully updated department id from " + oldId + " to " + newId;

        }
        else
        {
            return "Department with id : " + oldId + " not found.";
        }
    }

    @PatchMapping("/departments/{departmentid}")
    public Department patchDepartment(@PathVariable int departmentid, @RequestBody Map<String, Object>
            patchpayload)
    {
        Department department = departmentservice.findDepartmentById(departmentid);

        if(patchpayload.containsKey("id"))
        {
            throw new NotFoundException("departmentid : " + departmentid + " isn't allowed in request body!");
        }

        Department patcheddepartment = apply(patchpayload, department);

        Department department1 = departmentservice.addOrUpdateDepartment(patcheddepartment);

        return department1;
    }

    private Department apply(Map<String, Object> patchpayload, Department department)
    {
        ObjectNode departmentnode = objectmapper.convertValue(department, ObjectNode.class);

        ObjectNode patchnode = objectmapper.convertValue(patchpayload, ObjectNode.class);

        departmentnode.setAll(patchnode);

        return objectmapper.convertValue(departmentnode, Department.class);

    }

    @GetMapping("/departments/{departmentid}")
    public Department getDepartment(@PathVariable int departmentid)
    {
        Department department = departmentservice.findDepartmentById(departmentid);

        return department;

    }

    @GetMapping("/departments")
    public List<Object/*Department*/> findAllDepartments()
    {

        return departmentservice.findAllDepartments();

    }

    @DeleteMapping("/departments/{departmentid}")
    public String deleteDepartment(@PathVariable int departmentid)
    {
        Department department = departmentservice.findDepartmentById(departmentid);

        //departmentservice.deleteDepartmentById(departmentid);

        departmentservice.deleteDepartmentId(departmentid);

        return "Department with id : " + departmentid + " deleted successfully!";
    }

}
