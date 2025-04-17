package pers.lwb.service;

import pers.lwb.dto.EmployeeLoginDTO;
import pers.lwb.entity.Employee;

public interface EmployeeService {

    public Employee login(EmployeeLoginDTO employeeLoginDTO);
}
