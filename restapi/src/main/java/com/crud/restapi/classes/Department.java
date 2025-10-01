package com.crud.restapi.classes;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="department")
public class Department
{

@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="departmentname")
    private String departmentName;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH/*, CascadeType.REMOVE*/})
    @JoinColumn(name="companyid")
    private Company company;

    @OneToMany(mappedBy = "department",cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH, CascadeType.REMOVE},fetch = FetchType.EAGER)
    private List<Task> tasks;

    @OneToMany(mappedBy = "department",cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH, CascadeType.REMOVE},fetch = FetchType.EAGER)
    private List<Information> information;

    public List<Information> getEmployees() {
        return information;
    }

    public void setEmployees(List<Information> information) {
        this.information = information;
    }

    public Department(){}

    public void addEmployee(Information information)
    {
        if(this.information ==null)
        {
            this.information = new ArrayList<>();
        }
        this.information.add(information);
        information.setDepartment(this);
    }

    public void addTask(Task task)
    {
        if(tasks==null)
        {
            tasks = new ArrayList<>();
        }
        tasks.add(task);
        task.setDepartment(this);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Department(String departmentName) {
        this.departmentName = departmentName;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", departmentName='" + departmentName + '\'' +
                '}';
    }
}
