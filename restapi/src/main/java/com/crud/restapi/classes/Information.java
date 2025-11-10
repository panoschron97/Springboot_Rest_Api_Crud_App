package com.crud.restapi.classes;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "information")
public class Information
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "age")
    private int age;

    @Column(name = "sex")
    private char sex;

    @Column(name = "datebirth")
    private Date dateBirth;

    @Column(name = "jobstatus")
    private boolean jobStatus;

    @Column(name = "levelofeducation")
    private String levelOfEducation;

    @Column(name = "salary")
    private double salary;

    @Column(name="departmentid")
    private Integer departmentId;

    @Column(name="taskid")
    private Integer taskId;

    @Transient
    private double netSalary;

    public Information(){}

    public Information(String firstName, String lastName, int age, char sex, Date dateBirth, boolean jobStatus, String levelOfEducation, double salary, Integer departmentId, Integer taskId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.sex = sex;
        this.dateBirth = dateBirth;
        this.jobStatus = jobStatus;
        this.levelOfEducation = levelOfEducation;
        this.salary = salary;
        this.departmentId = departmentId;
        this.taskId = taskId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public Date getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(Date dateBirth) {
        this.dateBirth = dateBirth;
    }

    public boolean isJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(boolean jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getLevelOfEducation() {
        return levelOfEducation;
    }

    public void setLevelOfEducation(String levelOfEducation) {
        this.levelOfEducation = levelOfEducation;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getNetSalary()
    {
        return salary - (salary * 0.257);
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", dateBirth=" + dateBirth +
                ", jobStatus=" + jobStatus +
                ", levelOfEducation='" + levelOfEducation + '\'' +
                ", salary=" + salary +
                ", departmentid=" + departmentId +
                ", taskid=" + taskId +
                ", netSalary=" + getNetSalary() +
                '}';
    }
}
