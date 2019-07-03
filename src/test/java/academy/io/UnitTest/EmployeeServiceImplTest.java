package academy.io.UnitTest;

import academy.io.entity.Employee;
import academy.io.exception.BadRequestException;
import academy.io.exception.ResourceNotFoundException;
import academy.io.repository.EmployeeRepository;
import academy.io.service.EmployeeService;
import academy.io.service.EmployeeServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@RunWith(SpringRunner.class)
public class EmployeeServiceImplTest {

    @TestConfiguration
    static  class EmployeeServiceImplTestConfiguration{

        @Bean
        public EmployeeService getService(){
            return new EmployeeServiceImpl();
        }
    }

    @Autowired
    private EmployeeServiceImpl service;

    @MockBean
    private EmployeeRepository repository;

    private List<Employee> employees;
    @Before
    public void setup(){
        Employee emp = new Employee();

        emp.setFirstName("John");
        emp.setLastName("Smith");
        emp.setEmail("jsmith@gmail.com");

        employees.add(emp);
        repository.save(emp);

        Employee emp2 = new Employee();
        emp2.setFirstName("Prashanth");
        emp2.setLastName("Adurugatla");
        emp2.setEmail("padurugatla9@gmail.com");

        employees.add(emp2);
        repository.save(emp2);

        Employee emp3 = new Employee();
        emp3.setFirstName("Vivek");
        emp3.setLastName("Krishna");
        emp3.setEmail("vkrishna@gmail.com");

        employees.add(emp3);
        repository.save(emp3);

        employees = Collections.singletonList(emp);

        //when
        Mockito.when(repository.findAll())
                .thenReturn(employees);

        Mockito.when(repository.findById(emp.getId()))
                .thenReturn(Optional.of(emp));


    }

    @After
    public void cleanup(){
        System.out.println("After");
    }


    @Test
    public void findAll() {
        List<Employee> result = service.findAll();
        Assert.assertEquals("Employee List Should Match", employees, result);
    }




    @Test
    public void findOne() {
        Employee result = service.findOne(employees.get(0).getId());
        Assert.assertEquals("Employee should match with", employees.get(0), result);
    }
    @Test(expected = ResourceNotFoundException.class)
    public void findOneNotFound() {
        Employee result = service.findOne("krnv");

//        No need to have Assert here bcoz you dont reach that end point
//        Assert.assertEquals("Employee should not match with", employees.get(0), result);
    }




    @Test
    public void create() {
    }
    @Test(expected = BadRequestException.class)
    public void createBadRequest() {
    }




    @Test
    public void update() {
    }
    @Test(expected = BadRequestException.class)
    public void updateBadRequest() {
    }




    @Test
    public void delete() {
    }
    @Test(expected = ResourceNotFoundException.class)
    public void deleteNotFound() {
    }
}