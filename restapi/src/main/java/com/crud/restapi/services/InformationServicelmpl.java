package com.crud.restapi.services;

import com.crud.restapi.classes.Information;
import com.crud.restapi.exceptions.NotFoundException;
import com.crud.restapi.repositories.InformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InformationServicelmpl implements InformationService
{

    private InformationRepository informationrepository;

    @Autowired
    public InformationServicelmpl(InformationRepository informationrepository)
    {
        this.informationrepository = informationrepository;
    }

    @Override
    public Information addOrUpdateInformation(Information information)
    {
        return informationrepository.save(information);
    }

    /*@Override
    public void deleteInformationById(int id)
    {
        informationrepository.deleteById(id);
    }*/

    @Override
    public Information findInformationById(int id)
    {
        Optional<Information> information = informationrepository.findById(id);
        Information information1 = null;

        if(information.isPresent())
        {
            information1 = information.get();
        }
        else
        {
            throw new NotFoundException("Employee with id : " + id + " doesn't exists!");
        }

        return information1;
    }

    @Override
    public List<Information> findAllInformation()
    {

        return informationrepository.findAll();

    }

    public int updateInformationId(int oldId, int newId)
    {
        return informationrepository.updateInformationId(oldId, newId);
    }
    public int deleteInformationId(int oldId)
    {
        return informationrepository.deleteInformationId(oldId);
    }
}

