package com.example.Employee.Dto;


import com.example.Employee.Entity.Employee;

public class EmployeeCountryResponse {

    private Employee employee;
    private CountryDto country;

    public EmployeeCountryResponse() {
    }

    public EmployeeCountryResponse(Employee employee,
                                   CountryDto country) {

        this.employee = employee;
        this.country = country;
    }

    public Employee getEmployee() {
        return employee;
    }

    public CountryDto getCountry() {
        return country;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setCountry(CountryDto country) {
        this.country = country;
    }
}

