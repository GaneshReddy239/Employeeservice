package com.example.Employee.Service;
import com.example.Employee.Dto.CountryDto;
import com.example.Employee.Dto.DepartmentDto;
import com.example.Employee.Dto.EmployeeCountryResponse;
import com.example.Employee.Dto.EmployeeDepartmentResponse;
import com.example.Employee.Entity.Employee;
import com.example.Employee.Exception.ResourceNotFoundException;
import com.example.Employee.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
@Service
public class EmployeeService {
    private final WebClient webClient;
    private final EmployeeRepository repository;
    @Value("${country.service.base-url}")
    private String countryBaseUrl;
    @Value("${department.service.url}")
    private String departmentServiceUrl;
    public EmployeeService(WebClient webClient, EmployeeRepository repository) {
        this.webClient = webClient;
        this.repository = repository;
    }
    public Employee createEmployee(Employee employee) {
        return repository.save(employee);
    }
    public Employee getEmployee(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id: " + id));
    }
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }
    public Employee updateEmployee(Long id, Employee employee) {
        Employee existing = getEmployee(id);
        existing.setName(employee.getName());
        existing.setEmail(employee.getEmail());
        existing.setCountry(employee.getCountry());
        existing.setSalary(employee.getSalary());
        existing.setDepartmentId(employee.getDepartmentId());
        return repository.save(existing);
    }
    public EmployeeDepartmentResponse
    getEmployeeWithDepartment(Long id) {

        Employee employee = getEmployee(id);

        DepartmentDto department =
                webClient.get()
                        .uri(departmentServiceUrl+employee.getDepartmentId())
                        .retrieve()
                        .bodyToMono(DepartmentDto.class)
                        .block();

        return new EmployeeDepartmentResponse(
                employee,
                department
        );
    }
    public void deleteEmployee(Long id) {
        repository.deleteById(id);
    }
    public EmployeeCountryResponse
    getEmployeeCountryDetails(Long id) {

        Employee employee = getEmployee(id);

        CountryDto[] response =
                webClient.get()
                        .uri(countryBaseUrl + "/" + employee.getCountry())

                        .retrieve()
                        .bodyToMono(CountryDto[].class)
                        .block();

        CountryDto country = response[0];

        return new EmployeeCountryResponse(
                employee,
                country
        );
    }
}