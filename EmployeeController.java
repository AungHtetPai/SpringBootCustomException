package com.springboot.SpringBootcrud.main.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.SpringBootcrud.main.exceptions.ResourceNotFoundException;
import com.springboot.SpringBootcrud.main.model.Employee;
import com.springboot.SpringBootcrud.main.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

	@Autowired
	EmployeeRepository repository;

	@GetMapping("/employees")
	public List<Employee> getAllEmployee() {
		List<Employee> list = repository.findAll();
		return list;
	}

	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeId(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException { 
		Employee emp = repository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
		return ResponseEntity.ok().body(emp);
	}

	@PostMapping("/employees")
	public Employee createEmployeeId(@RequestBody Employee employee) {
		return repository.save(employee);
	}

	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId,
			@RequestBody Employee employeeDetails) throws ResourceNotFoundException {
		Employee emp = repository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Emplpoyee not found for this id :: " + employeeId));
		emp.setFirstName(employeeDetails.getFirstName());
		emp.setLastName(employeeDetails.getLastName());
		emp.setEmail(employeeDetails.getEmail());
		final Employee updateEmployee = repository.save(emp);
		return ResponseEntity.ok(updateEmployee);
	}

	@DeleteMapping("/employees/{id}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		Employee employee = repository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this Id :: " + employeeId));
		repository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
