package com.crud.restapi.repositories;

import com.crud.restapi.classes.Information;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InformationRepository extends JpaRepository<Information, Integer>
{
    @Modifying
    @Transactional
    @Query("UPDATE Information i SET i.id = :newId WHERE i.id = :oldId")
    int updateInformationId(@Param("oldId") int oldId, @Param("newId") int newId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Information i WHERE i.id = :oldId")
    int deleteInformationId(@Param("oldId") int oldId);
}