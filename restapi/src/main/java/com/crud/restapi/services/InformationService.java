package com.crud.restapi.services;

import com.crud.restapi.classes.Information;

import java.util.List;

public interface InformationService
{
    Information addOrUpdateInformation(Information information);

    int deleteInformationId(int id);

    //void deleteInformationById(int id);

    Information findInformationById(int id);

    List<Object/*Information*/> findAllInformation();

    int updateInformationId(int oldId, int newId);
}
