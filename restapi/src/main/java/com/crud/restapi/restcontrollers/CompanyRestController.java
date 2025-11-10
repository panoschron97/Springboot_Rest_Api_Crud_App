package com.crud.restapi.restcontrollers;

import com.crud.restapi.classes.Company;
import com.crud.restapi.exceptions.NotFoundException;
import com.crud.restapi.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CompanyRestController
{

private CompanyService companyservice;
private ObjectMapper objectmapper;

@Autowired
public CompanyRestController(CompanyService companyservice, ObjectMapper objectmapper)
{
    this.companyservice = companyservice;
    this.objectmapper = objectmapper;
}
    @PostMapping("/companies")
    public Company addCompany(@RequestBody Company company)
    {
        //company.setId(null); //if you have private Integer id;
        company.setId(0);
        Company company1 = companyservice.addOrUpdateCompany(company);
        return company1;
    }

    @PutMapping("/companies")
    public Company updateCompany(@RequestBody Company company)
    {
        //company.setId(null); //if you have private Integer id;
        //company.setId(0);
        Company company1 = companyservice.addOrUpdateCompany(company);
        return company1;
    }

    @PutMapping("/companies/{oldId}/id/{newId}")
    public String updateCompanyId(@PathVariable int oldId, @PathVariable int newId) {

        int rows = companyservice.updateCompanyId(oldId, newId);

        if (rows > 0)
        {
            return "Successfully updated company id from " + oldId + " to " + newId;

        }
        else
        {
            return "Company with id : " + oldId + " not found.";
        }
    }

    @PatchMapping("/companies/{companyid}")
    public Company patchCompany(@PathVariable int companyid, @RequestBody Map<String, Object>
            patchpayload)
    {
        Company company = companyservice.findCompanyById(companyid);

        if(patchpayload.containsKey("id"))
        {
            throw new NotFoundException("companyid : " + companyid + " isn't allowed in request body!");
        }

        Company patchedcompany = apply(patchpayload, company);

        Company company1 = companyservice.addOrUpdateCompany(patchedcompany);

        return company1;
    }

    private Company apply(Map<String, Object> patchpayload, Company company)
    {
        ObjectNode companynode = objectmapper.convertValue(company, ObjectNode.class);

        ObjectNode patchnode = objectmapper.convertValue(patchpayload, ObjectNode.class);

        companynode.setAll(patchnode);

        return objectmapper.convertValue(companynode, Company.class);

    }

    @GetMapping("/companies/{companyid}")
    public Company getCompany(@PathVariable int companyid)
    {
        Company company = companyservice.findCompanyById(companyid);

        return company;

    }

    @GetMapping("/companies")
    public List<Object/*Company*/> findAllCompanies()
    {

     return companyservice.findAllCompanies();

    }

    @DeleteMapping("/companies/{companyid}")
    public String deleteCompany(@PathVariable int companyid)
    {
        Company company = companyservice.findCompanyById(companyid);

            //companyservice.deleteCompanyById(companyid);

            companyservice.deleteCompanyId(companyid);

            return "Company with id : " + companyid + " deleted successfully!";
    }

}
