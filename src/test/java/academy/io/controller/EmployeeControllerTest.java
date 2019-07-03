package academy.io.controller;

import academy.io.entity.Employee;
import academy.io.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
//By default it starts a web Application and tell to start it in a Mock Fashion instead running on any specified Port
        webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@AutoConfigureMockMvc
//MockMvc provides a client to make RESTful calls as in real we use our browsers or Postman
@ActiveProfiles("integrationtest")

public class EmployeeControllerTest {

    @Autowired
    private MockMvc mvc;


    @Autowired
    private EmployeeRepository repository;

    private List<Employee> employeeList;

    @Before
    public void setup(){

        Employee emp = new Employee();
        emp.setid("jsmith-id");
        emp.setFirstName("John");
        emp.setLastName("Smith");
        emp.setEmail("jsmith@gmail.com");
        repository.save(emp);

        Employee emp2 = new Employee();
        emp2.setid("padurugatla-id");
        emp2.setFirstName("Prashanth");
        emp2.setLastName("Adurugatla");
        emp2.setEmail("padurugatla9@gmail.com");
        repository.save(emp2);





    }


    @After
    public void clean(){

        repository.deleteAll();
    }







    @Test
    public void findAll() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status()
                                                .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email", Matchers.is("jsmith@gmail.com")));
    }

    @Test
    public void findone() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/employees/padurugatla-id"))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("padurugatla9@gmail.com")));

    }

    @Test
    public void findone404() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/employees/fail-id"))
                .andExpect(MockMvcResultMatchers.status()
                        .isNotFound());
    }


    @Test
    public void create() throws Exception{

        //Object mapper is used to transfer the emp object to json payload format
        ObjectMapper mapper = new ObjectMapper();
        Employee emp = new Employee();
        emp.setFirstName("Vijay");
        emp.setLastName("Kumar");
        emp.setEmail("vkumar@gmail.com");


        mvc.perform(MockMvcRequestBuilders.post("/employees")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsBytes(emp))
                    )

            .andExpect(MockMvcResultMatchers.status()
                    .isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.notNullValue()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("vkumar@gmail.com")));



//        After Creating an Object Just confirm whether it has been stored in DB. Use get request to verify

        mvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)));


    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void delete() throws Exception {
    }
}