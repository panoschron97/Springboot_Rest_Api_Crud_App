package com.crud.restapi.classes;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "task")
public class Task
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "taskname")
    private String taskName;

    @Column(name = "costtask")
    private BigDecimal costTask;

    @Column(name = "startdate")
    private Date startDate;

    @Column(name = "enddate")
    private Date endDate;

    @Transient
    private BigDecimal netCostTask;

    @Transient
    private Integer daysOfTask;

    @Transient
    private Integer monthsOfTask;

    @Transient
    private Integer yearsOfTask;

    @Column(name="departmentid")
    private int departmentId;

    public Task(){}

    public Task(String taskName, BigDecimal costTask, Date startDate, Date endDate) {
        this.taskName = taskName;
        this.costTask = costTask;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public BigDecimal getCostTask() {
        return costTask;
    }

    public void setCostTask(BigDecimal costTask) {
        this.costTask = costTask;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getNetCostTask() {
        if (costTask != null) {
            netCostTask = costTask.multiply(new BigDecimal("1").subtract(new BigDecimal("0.257")));
            return netCostTask;
        }
        return null;
    }

    public Integer getDaysOfTask() {
        if (startDate != null && endDate != null) {
            LocalDate startLocalDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate endLocalDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            daysOfTask = (int) java.time.temporal.ChronoUnit.DAYS.between(startLocalDate, endLocalDate);
            return daysOfTask;
        } else {
            return null;
        }
    }

    public Integer getMonthsOfTask() {
        if (startDate != null && endDate != null) {
            LocalDate startLocalDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate endLocalDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            monthsOfTask = (int) java.time.temporal.ChronoUnit.MONTHS.between(startLocalDate, endLocalDate);
            return  monthsOfTask;
        } else {
            return null;
        }
    }

    public Integer getYearsOfTask() {
        if (startDate != null && endDate != null) {
            LocalDate startLocalDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate endLocalDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            yearsOfTask = (int) java.time.temporal.ChronoUnit.YEARS.between(startLocalDate, endLocalDate);
            return yearsOfTask;
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", taskName='" + taskName + '\'' +
                ", costTask=" + costTask +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", departmentId=" + departmentId +
                ", netCostTask=" + getNetCostTask() +
                ", dayOfTask=" + getDaysOfTask() +
                ", monthsOfTask=" + getMonthsOfTask() +
                ", yearsOfTask=" + getYearsOfTask() +
                '}';
    }
}
