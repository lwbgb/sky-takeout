package pers.lwb.service;

import pers.lwb.dto.EmployeeLoginDTO;
import pers.lwb.entity.Employee;
import pers.lwb.vo.EmployeePageVO;

public interface EmployeeService {

    Employee login(EmployeeLoginDTO employeeLoginDTO);

    boolean insert(Employee employee);

    EmployeePageVO page(String name, Integer pageNum, Integer pageSize);
}
