package com.crud.restapi.services;

import com.crud.restapi.classes.Company;
import com.crud.restapi.exceptions.NotFoundException;
import com.crud.restapi.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServicelmpl implements CompanyService
{

    private CompanyRepository companyrepository;

    @Autowired
    public CompanyServicelmpl(CompanyRepository companyrepository)
    {
        this.companyrepository = companyrepository;
    }

    @Override
    public Company addOrUpdateCompany(Company company)
    {
       return companyrepository.save(company);
    }

    /*@Override
    public void deleteCompanyById(int id)
    {
        companyrepository.deleteById(id);
    }*/

    @Override
    public Company findCompanyById(int id) {
        Optional<Company> company = companyrepository.findById(id);
        Company company1 = null;

        if (company.isPresent()) {
            company1 = company.get();
        } else {
            throw new NotFoundException("Company with id : " + id + " doesn't exists!");
        }
        return company1;
    }

    @Override
    public List<Company> findAllCompanies()
    {

      return companyrepository.findAll();

    }

    public int updateCompanyId(int oldId, int newId)
    {
        return companyrepository.updateCompanyId(oldId, newId);
    }

    public int deleteCompanyId(int oldId)
    {
        return companyrepository.deleteCompanyId(oldId);
    }
}
