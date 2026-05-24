package com.example.Employee.Controller;



import com.example.Employee.Dto.EmployeeDepartmentResponse;
import com.example.Employee.Entity.Employee;
import com.example.Employee.Service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }
    @GetMapping("/{id}/department")
    public EmployeeDepartmentResponse
    getEmployeeWithDepartment(@PathVariable Long id) {

        return service.getEmployeeWithDepartment(id);
    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return service.createEmployee(employee);
    }

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable Long id) {
        return service.getEmployee(id);
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return service.getAllEmployees();
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id,
                                   @RequestBody Employee employee) {

        return service.updateEmployee(id, employee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        service.deleteEmployee(id);
    }
}
