package com.crud.restapi.repositories;

import com.crud.restapi.classes.Department;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Integer>
{

    @Query("SELECT d FROM Department d")
    List<Object> selectDepartment();

    @Query("SELECT d FROM Department d WHERE d.id = :id")
    Department selectDepartmentById(@Param("id") int id);

    @Modifying
    @Transactional
    @Query("UPDATE Department d SET d.id = :newId WHERE d.id = :oldId")
    int updateDepartmentId(@Param("oldId") int oldId, @Param("newId") int newId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Department d WHERE d.id = :oldId")
    int deleteDepartmentId(@Param("oldId") int oldId);
}
