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

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH/*, CascadeType.REMOVE*/})
    @JoinColumn(name="departmentid")
    private Department department;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH/*, CascadeType.REMOVE*/})
    @JoinColumn(name="taskid")
    private Task task;

    @Transient
    private double netSalary;

    public Information(){}

    public Information(String firstName, String lastName, int age, char sex, Date dateBirth, boolean jobStatus, String levelOfEducation, double salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.sex = sex;
        this.dateBirth = dateBirth;
        this.jobStatus = jobStatus;
        this.levelOfEducation = levelOfEducation;
        this.salary = salary;
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public double getNetSalary()
    {
        return salary - (salary * 0.257);
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
                ", netSalary=" + getNetSalary() +
                '}';
    }
}
