package com.example.project.capstone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.capstone.entity.Employee;
import com.example.project.capstone.repository.EmployeeRepository;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class EmployeeController {

	@Autowired
	EmployeeRepository employeeRepository;
	
	@PostMapping("/addemployee")
	@ResponseStatus(HttpStatus.CREATED)
	public Employee addEmployee(@RequestBody Employee employee){
		return employeeRepository.save(employee);
	}
	
	@PutMapping("/employee")
	public Employee updateEmployee(@RequestBody Employee employee) {
		return employeeRepository.updateById(employee);
	}
	
	@PutMapping("/employee/updatepassword/{id}/{password}")
	public Employee updateEmployeePasswordById(@PathVariable long id,@PathVariable String password) {
		return employeeRepository.updatePasswordById(id, password);
	}
	
	@GetMapping("/employee")
	public List<Employee> getAllEmployees()
	{
		return employeeRepository.findAll();
	}
	@GetMapping("/onlyemployee")
	public List<Employee> getOnlyEmployee()
	{
		return employeeRepository.findByType();
	}
	
	@GetMapping("/onlymanager")
	public List<Employee> getOnlyManager()
	{
		return employeeRepository.findManagers();
	}
	
	@GetMapping("/employee/{email}/{password}")
	public Employee getEmployee(@PathVariable String email,@PathVariable String password)
	{
		return employeeRepository.findByEmailAndPassword(email, password);
	}
	@GetMapping("/employeebyemail/{email}")
	public Employee getEmployeeByEmail(@PathVariable String email)
	{
		return employeeRepository.findByEmail(email);
	}
	
	@GetMapping("/employee/{id}")
	public Employee getEmployee(@PathVariable Long id)
	{
		return employeeRepository.findById(id);
	}
	
	@GetMapping("/employeebymanager/{id}")
	public List<Employee> getEmployeeByManager(@PathVariable Long id)
	{
		return employeeRepository.findEmployeeByManagerId(id);
	}
	
	@DeleteMapping("/employee/{id}")
	public ResponseEntity<Employee> deleteEmployeeById(@PathVariable Long id)
	{
			Employee employee= employeeRepository.findById(id);
			if(employee!=null)	{
				employeeRepository.deleteById(id);
				return ResponseEntity.noContent().build();	
			}
			else
				return ResponseEntity.notFound().build();
			
	}
	
}
