package com.crud.restapi.restcontrollers;

import com.crud.restapi.classes.Information;
import com.crud.restapi.exceptions.NotFoundException;
import com.crud.restapi.services.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class InformationRestController
{

    private InformationService informationservice;
    private ObjectMapper objectmapper;

    @Autowired
    public InformationRestController(InformationService informationservice, ObjectMapper objectmapper)
    {
        this.informationservice = informationservice;
        this.objectmapper = objectmapper;
    }
    @PostMapping("/information")
    public Information addInformation(@RequestBody Information information)
    {
        //information.setId(null); //if you have private Integer id;
        information.setId(0);
        Information information1 = informationservice.addOrUpdateInformation(information);
        return information1;
    }

    @PutMapping("/information")
    public Information updateInformation(@RequestBody Information information)
    {
        //information.setId(null); //if you have private Integer id;
        //information.setId(0);
        Information information1 = informationservice.addOrUpdateInformation(information);
        return information1;
    }

    @PutMapping("/information/{oldId}/id/{newId}")
    public String updateInformationId(@PathVariable int oldId, @PathVariable int newId) {

        int rows = informationservice.updateInformationId(oldId, newId);

        if (rows > 0)
        {
            return "Successfully updated employee id from " + oldId + " to " + newId;

        }
        else
        {
            return "Employee with id : " + oldId + " not found.";
        }
    }

    @PatchMapping("/information/{employeeid}")
    public Information patchInformation(@PathVariable int employeeid, @RequestBody Map<String, Object>
            patchpayload)
    {
        Information information = informationservice.findInformationById(employeeid);

        if(patchpayload.containsKey("id"))
        {
            throw new NotFoundException("employeeid : " + employeeid + " isn't allowed in request body!");
        }

        Information patchedinformation = apply(patchpayload, information);

        Information information1 = informationservice.addOrUpdateInformation(patchedinformation);

        return information1;
    }

    private Information apply(Map<String, Object> patchpayload, Information information)
    {
        ObjectNode informationnode = objectmapper.convertValue(information, ObjectNode.class);

        ObjectNode patchnode = objectmapper.convertValue(patchpayload, ObjectNode.class);

        informationnode.setAll(patchnode);

        return objectmapper.convertValue(informationnode, Information.class);

    }

    @GetMapping("/information/{employeeid}")
    public Information getInformation(@PathVariable int employeeid)
    {
        Information information = informationservice.findInformationById(employeeid);

        return information;

    }

    @GetMapping("/information")
    public List<Object/*Information*/> findAllInformation()
    {

        return informationservice.findAllInformation();

    }

    @DeleteMapping("/information/{employeeid}")
    public String deleteInformation(@PathVariable int employeeid)
    {
        Information information = informationservice.findInformationById(employeeid);

        //informationservice.deleteInformationById(employeeid);

        informationservice.deleteInformationId(employeeid);

        return "Employee with id : " + employeeid + " deleted successfully!";
    }

}

