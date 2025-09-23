package com.crud.restapi.services;

import com.crud.restapi.classes.Department;

import java.util.List;

public interface DepartmentService
{
    Department addOrUpdateDepartment(Department department);

    int deleteDepartmentId(int id);

    //void deleteDepartmentById(int id);

    Department findDepartmentById(int id);

    List<Department> findAllDepartments();

    int updateDepartmentId(int oldId, int newId);
}
