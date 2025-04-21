package pers.lwb.service;

import pers.lwb.dto.EmployeeLoginDTO;
import pers.lwb.entity.Employee;

public interface EmployeeService {

    Employee login(EmployeeLoginDTO employeeLoginDTO);

    boolean insert(Employee employee);
}
