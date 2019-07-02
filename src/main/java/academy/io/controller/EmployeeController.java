package academy.io.controller;

import academy.io.entity.Employee;
import academy.io.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;


//@Controller
//@ResponseBody
@RestController
@RequestMapping(value = "/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Employee> findAll(){
        return service.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}",
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Employee findone(@PathVariable("id") String empId){
        return service.findOne(empId);
    }

    @RequestMapping(method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Employee create(@RequestBody Employee emp){
        return service.create(emp);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}",
                   consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
                   produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Employee update(@PathVariable("id") String empId, @RequestBody Employee emp){
        return service.update(empId, emp);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public void delete(@PathVariable("id") String empId){
        service.delete(empId);
    }
}
