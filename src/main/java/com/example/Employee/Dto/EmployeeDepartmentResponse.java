package com.example.Employee.Dto;


import com.example.Employee.Entity.Employee;

public class EmployeeDepartmentResponse {

    private Employee employee;
    private DepartmentDto department;

    public EmployeeDepartmentResponse() {
    }

    public EmployeeDepartmentResponse(Employee employee,
                                      DepartmentDto department) {

        this.employee = employee;
        this.department = department;
    }

    public Employee getEmployee() {
        return employee;
    }

    public DepartmentDto getDepartment() {
        return department;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setDepartment(DepartmentDto department) {
        this.department = department;
    }
}

