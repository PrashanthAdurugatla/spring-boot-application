package academy.io.service;

import academy.io.entity.Employee;
import academy.io.exception.BadRequestException;
import academy.io.exception.ResourceNotFoundException;
import academy.io.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@Component
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository repository;

    @Override
    public List<Employee> findAll() {
        return (List<Employee>) repository.findAll();
    }

    @Override
    public Employee findOne(String id) {
        Optional<Employee> existing = repository.findById(id);

        if(!existing.isPresent()){
            throw new ResourceNotFoundException("Employee with id=" + id + "NOT FOUND");
        }
        else{
            return existing.get();
        }
    }

    @Override
    @Transactional
    public Employee create(Employee emp) {
        Optional<Employee> existing = repository.findByEmail(emp.getEmail());
        if(existing.isPresent()){
            throw new BadRequestException("Employee with email " + emp.getEmail() + "already exists.") ;
        }
        return repository.save(emp);
    }

    @Override
    @Transactional
    public Employee update(String id, Employee emp) {
        Optional<Employee> existing = repository.findById(id);

        if(!existing.isPresent()){
            throw new BadRequestException("Employee with id " + id + "doesn't exists.");
        }
        return repository.save(emp);
    }

    @Override
    @Transactional
    public void delete(String id) {
        Optional<Employee> existing = repository.findById(id);

        if(!existing.isPresent()){
            throw new ResourceNotFoundException("Employee with id " + id + "doesn't exists.");
        }
        repository.delete(existing.get());

    }
}
