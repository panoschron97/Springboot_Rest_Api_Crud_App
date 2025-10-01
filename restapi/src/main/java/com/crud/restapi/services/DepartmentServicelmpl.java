package com.crud.restapi.services;

import com.crud.restapi.classes.Department;
import com.crud.restapi.exceptions.NotFoundException;
import com.crud.restapi.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServicelmpl implements DepartmentService
{

    private DepartmentRepository departmentrepository;

    @Autowired
    public DepartmentServicelmpl(DepartmentRepository departmentrepository)
    {
        this.departmentrepository = departmentrepository;
    }

    @Override
    public Department addOrUpdateDepartment(Department department)
    {
        return departmentrepository.save(department);
    }

    /*@Override
    public void deleteDepartmentById(int id)
    {
        departmentrepository.deleteById(id);
    }*/

    @Override
    public Department findDepartmentById(int id)
    {
        Optional<Department> department = Optional.ofNullable(departmentrepository.selectDepartmentById(id));/*.findById(id);*/
        Department department1 = null;

        if(department.isPresent())
        {
            department1 = department.get();
        }
        else
        {
            throw new NotFoundException("Department with id : " + id + " doesn't exists!");
        }

        return department1;
    }

    @Override
    public List<Object/*Department*/> findAllDepartments()
    {

        return departmentrepository.selectDepartment();/*.findAll();*/

    }

    public int updateDepartmentId(int oldId, int newId)
    {
        return departmentrepository.updateDepartmentId(oldId, newId);
    }
    public int deleteDepartmentId(int oldId)
    {
        return departmentrepository.deleteDepartmentId(oldId);
    }
}

