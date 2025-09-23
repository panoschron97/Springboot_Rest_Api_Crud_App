package com.crud.restapi.services;

import com.crud.restapi.classes.Company;

import java.util.List;

public interface CompanyService
{
    Company addOrUpdateCompany(Company company);

    int deleteCompanyId(int id);

    //void deleteCompanyById(int id);

    Company findCompanyById(int id);

    List<Company> findAllCompanies();

int updateCompanyId(int oldId, int newId);
}
