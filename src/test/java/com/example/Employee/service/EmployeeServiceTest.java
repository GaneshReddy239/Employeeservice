package com.example.Employee.service;


import com.example.Employee.Dto.CountryDto;
import com.example.Employee.Dto.EmployeeCountryResponse;
import com.example.Employee.Dto.EmployeeDepartmentResponse;
import com.example.Employee.Entity.Employee;
import com.example.Employee.Exception.ResourceNotFoundException;
import com.example.Employee.Repository.EmployeeRepository;
import com.example.Employee.Service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.List;
import reactor.core.publisher.Mono;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.Employee.Dto.DepartmentDto;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    EmployeeRepository repository;

    @Mock
    WebClient webClient;

    @InjectMocks
    EmployeeService service;

    @Mock
    WebClient.RequestHeadersUriSpec uriSpec;

    @Mock
    WebClient.RequestHeadersSpec headersSpec;

    @Mock
    WebClient.ResponseSpec responseSpec;

    @Test
    void shouldReturnEmployee_whenEmployeeExists() {

        // Arrange

        Employee emp =
                new Employee(
                        1L,
                        "Ganesh",
                        "g@gmail.com",
                        "India",
                        50000.0,
                        10L
                );

        when(repository.findById(1L))
                .thenReturn(Optional.of(emp));


        // Act

        Employee result =
                service.getEmployee(1L);


        // Assert

        assertNotNull(result);

        assertEquals(
                "Ganesh",
                result.getName()
        );

        assertEquals(
                "India",
                result.getCountry()
        );


        verify(repository,times(1))
                .findById(1L);

    }
    @Test
    void shouldThrowException_whenEmployeeNotFound() {

        // Arrange

        when(repository.findById(99L))
                .thenReturn(Optional.empty());


        // Act + Assert

        ResourceNotFoundException exception =
                assertThrows(
                        ResourceNotFoundException.class,

                        () -> service.getEmployee(99L)
                );


        // Verify message

        assertEquals(
                "Employee not found with id: 99",
                exception.getMessage()
        );


        verify(repository,times(1))
                .findById(99L);

    }
    @Test
    void shouldCreateEmployee() {

        // Arrange

        Employee emp =
                new Employee(
                        1L,
                        "Ganesh",
                        "g@gmail.com",
                        "India",
                        50000.0,
                        10L
                );

        when(repository.save(emp))
                .thenReturn(emp);


        // Act

        Employee result =
                service.createEmployee(emp);


        // Assert

        assertNotNull(result);

        assertEquals(
                "Ganesh",
                result.getName()
        );

        verify(repository,times(1))
                .save(emp);

    }
    @Test
    void shouldReturnAllEmployees() {

        // Arrange

        List<Employee> employees =
                List.of(

                        new Employee(
                                1L,
                                "Ganesh",
                                "g@gmail.com",
                                "India",
                                50000.0,
                                10L
                        ),

                        new Employee(
                                2L,
                                "Ravi",
                                "r@gmail.com",
                                "USA",
                                60000.0,
                                20L
                        )

                );


        when(repository.findAll())
                .thenReturn(employees);


        // Act

        List<Employee> result =
                service.getAllEmployees();


        // Assert

        assertEquals(
                2,
                result.size()
        );

        assertEquals(
                "Ganesh",
                result.get(0).getName()
        );

        verify(repository,times(1))
                .findAll();

    }
    @Test
    void shouldUpdateEmployee() {

        Employee existing =
                new Employee(
                        1L,
                        "Old",
                        "old@gmail.com",
                        "India",
                        5000.0,
                        1L
                );

        Employee updated =
                new Employee(
                        1L,
                        "Ganesh",
                        "new@gmail.com",
                        "USA",
                        7000.0,
                        2L
                );


        when(repository.findById(1L))
                .thenReturn(Optional.of(existing));

        when(repository.save(any(Employee.class)))
                .thenReturn(updated);


        Employee result =
                service.updateEmployee(
                        1L,
                        updated
                );


        assertEquals(
                "Ganesh",
                result.getName()
        );

        assertEquals(
                "USA",
                result.getCountry()
        );

        verify(repository)
                .save(any(Employee.class));

    }
    @Test
    void shouldDeleteEmployee() {

        service.deleteEmployee(1L);

        verify(repository,times(1))
                .deleteById(1L);

    }
    @Test
    void shouldReturnEmployeeWithDepartment() {

        Employee emp =
                new Employee(
                        1L,
                        "Ganesh",
                        "g@gmail",
                        "India",
                        1000.0,
                        10L
                );

        DepartmentDto dept =
                new DepartmentDto();

        dept.setDepartmentName(
                "IT"
        );


        when(repository.findById(1L))
                .thenReturn(Optional.of(emp));


        when(webClient.get())
                .thenReturn(uriSpec);

        when(uriSpec.uri(any(String.class)))
                .thenReturn(headersSpec);

        when(headersSpec.retrieve())
                .thenReturn(responseSpec);

        when(responseSpec.bodyToMono(
                DepartmentDto.class))
                .thenReturn(
                        Mono.just(dept)
                );


        EmployeeDepartmentResponse result =
                service.getEmployeeWithDepartment(
                        1L
                );


        assertEquals(
                "Ganesh",
                result.getEmployee()
                        .getName()
        );

    }
    @Test
    void shouldReturnCountryDetails() {

        Employee emp =
                new Employee(
                        1L,
                        "Ganesh",
                        "g@gmail",
                        "India",
                        1000.0,
                        1L
                );

        CountryDto dto =
                new CountryDto();

        CountryDto.Name name =
                new CountryDto.Name();

        name.setCommon("India");

        dto.setName(name);


        when(repository.findById(1L))
                .thenReturn(Optional.of(emp));


        when(webClient.get())
                .thenReturn(uriSpec);

        when(uriSpec.uri(any(String.class)))
                .thenReturn(headersSpec);

        when(headersSpec.retrieve())
                .thenReturn(responseSpec);

        when(responseSpec.bodyToMono(
                CountryDto[].class
        ))
                .thenReturn(
                        Mono.just(
                                new CountryDto[]{
                                        dto
                                }
                        )
                );


        EmployeeCountryResponse result =
                service.getEmployeeCountryDetails(
                        1L
                );


        assertEquals(
                "India",
                result.getCountry()
                        .getName()
                        .getCommon()
        );

    }

}
