package academy.io.controller;

import academy.io.entity.Employee;
import academy.io.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;


//@Controller
//@ResponseBody
@RestController
@RequestMapping(value = "/employees")
@Api(description = "Employee Related endpoints")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Find All Employees",
                  notes = "Returns a list of all employees available in the database"
                 )

    public List<Employee> findAll(){
        return service.findAll();
    }


    @RequestMapping(method = RequestMethod.GET, value = "/{id}",
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Find an Employee",
                  notes = "Returns an employee data if it's available in the database"
                  )

    public Employee findone(@PathVariable("id") String empId){
        return service.findOne(empId);
    }



    @RequestMapping(method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Add an Employee",
                  notes = "Add's an employee data in the database"
                 )

    public Employee create(@RequestBody Employee emp){
        return service.create(emp);
    }



    @RequestMapping(method = RequestMethod.PUT, value = "/{id}",
                   consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
                   produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Update an Employee",
                  notes = "Updates an employee data if it's available in the database"
                 )

    public Employee update(@PathVariable("id") String empId, @RequestBody Employee emp){
        return service.update(empId, emp);
    }



    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @ApiOperation(value = "Delete an Employee",
                  notes = "Deletes an employee data if it's available in the database"
                 )

    public void delete(@PathVariable("id") String empId){
        service.delete(empId);
    }
}
