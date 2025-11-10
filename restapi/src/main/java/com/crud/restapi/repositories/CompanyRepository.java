package com.crud.restapi.repositories;

import com.crud.restapi.classes.Company;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Integer>
{

    @Query("SELECT c FROM Company c")
    List<Object> selectCompany();

    @Query("SELECT c FROM Company c WHERE c.id = :id")
    Company selectCompanyById(@Param("id") int id);

    @Modifying
    @Transactional
    @Query("UPDATE Company c SET c.id = :newId WHERE c.id = :oldId")
    int updateCompanyId(@Param("oldId") int oldId, @Param("newId") int newId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Company c WHERE c.id = :oldId")
    int deleteCompanyId(@Param("oldId") int oldId);

}
