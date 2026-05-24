package com.example.Employee.controller;



import com.example.Employee.Controller.EmployeeController;
import com.example.Employee.Entity.Employee;
import com.example.Employee.Service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {


    @Autowired
    MockMvc mockMvc;


    @MockBean
    EmployeeService service;


    @Autowired
    ObjectMapper objectMapper;



    // -------------------------
    // GET BY ID
    // -------------------------

    @Test
    void shouldReturnEmployeeById()
            throws Exception {

        Employee emp =
                new Employee(
                        1L,
                        "Ganesh",
                        "g@gmail.com",
                        "India",
                        50000.0,
                        10L
                );


        when(service.getEmployee(1L))
                .thenReturn(emp);


        mockMvc.perform(
                        get("/employees/1")
                )

                .andExpect(status().isOk())

                .andExpect(
                        jsonPath("$.name")
                                .value("Ganesh")
                )

                .andExpect(
                        jsonPath("$.country")
                                .value("India")
                );

    }



    // -------------------------
    // GET ALL
    // -------------------------

    @Test
    void shouldReturnAllEmployees()
            throws Exception {


        List<Employee> list =
                List.of(

                        new Employee(
                                1L,
                                "Ganesh",
                                "g@gmail",
                                "India",
                                1000.0,
                                1L
                        ),

                        new Employee(
                                2L,
                                "Ravi",
                                "r@gmail",
                                "USA",
                                2000.0,
                                2L
                        )

                );


        when(service.getAllEmployees())
                .thenReturn(list);



        mockMvc.perform(
                        get("/employees")
                )

                .andExpect(status().isOk())

                .andExpect(
                        jsonPath("$.size()")
                                .value(2)
                );

    }




    // -------------------------
    // CREATE
    // -------------------------

    @Test
    void shouldCreateEmployee()
            throws Exception {

        Employee emp =
                new Employee(
                        1L,
                        "Ganesh",
                        "g@gmail",
                        "India",
                        5000.0,
                        1L
                );


        when(service.createEmployee(
                any(Employee.class)
        )).thenReturn(emp);



        mockMvc.perform(

                        post("/employees")

                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )

                                .content(
                                        objectMapper
                                                .writeValueAsString(emp)
                                )

                )

                .andExpect(status().isOk())

                .andExpect(
                        jsonPath("$.name")
                                .value("Ganesh")
                );

    }




    // -------------------------
    // UPDATE
    // -------------------------

    @Test
    void shouldUpdateEmployee()
            throws Exception {


        Employee updated =
                new Employee(
                        1L,
                        "Updated",
                        "u@gmail",
                        "USA",
                        7000.0,
                        2L
                );


        when(
                service.updateEmployee(
                        eq(1L),
                        any(Employee.class)
                )
        )
                .thenReturn(updated);



        mockMvc.perform(

                        put("/employees/1")

                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )

                                .content(
                                        objectMapper
                                                .writeValueAsString(updated)
                                )
                )

                .andExpect(status().isOk())

                .andExpect(
                        jsonPath("$.name")
                                .value("Updated")
                );

    }




    // -------------------------
    // DELETE
    // -------------------------

    @Test
    void shouldDeleteEmployee()
            throws Exception {


        mockMvc.perform(

                        delete("/employees/1")

                )

                .andExpect(status().isOk());

    }


}