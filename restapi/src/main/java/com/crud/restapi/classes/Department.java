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

    @Column(name="companyid")
    private int companyId;

    public Department(){}

    public Department(String departmentName, int companyId) {
        this.departmentName = departmentName;
        this.companyId = companyId;
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

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", departmentName='" + departmentName + '\'' +
                '}';
    }
}
