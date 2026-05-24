package com.example.Employee.Exception;



import com.example.Employee.Controller.EmployeeController;
import com.example.Employee.Service.EmployeeService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(EmployeeController.class)

@Import(
        com.example.Employee.Exception
                .GlobalExceptionHandler.class
)

class GlobalExceptionHandlerTest {


    @Autowired
    MockMvc mockMvc;


    @MockBean
    EmployeeService service;



    // -----------------------
    // 404 test
    // -----------------------

    @Test
    void shouldReturn404_whenEmployeeMissing()
            throws Exception {


        when(service.getEmployee(99L))

                .thenThrow(
                        new ResourceNotFoundException(
                                "Employee not found with id: 99"
                        )
                );



        mockMvc.perform(
                        get("/employees/99")
                )

                .andExpect(
                        status().isNotFound()
                )

                .andExpect(
                        jsonPath("$.message")
                                .value(
                                        "Employee not found with id: 99"
                                )
                )

                .andExpect(
                        jsonPath("$.status")
                                .value(404)
                );

    }




    // -----------------------
    // Generic 500 test
    // -----------------------

    @Test
    void shouldReturn500_whenUnexpectedError()
            throws Exception {


        when(service.getEmployee(1L))

                .thenThrow(
                        new RuntimeException(
                                "Unexpected error"
                        )
                );



        mockMvc.perform(
                        get("/employees/1")
                )

                .andExpect(
                        status()
                                .isInternalServerError()
                )

                .andExpect(
                        jsonPath("$.status")
                                .value(500)
                );

    }

}