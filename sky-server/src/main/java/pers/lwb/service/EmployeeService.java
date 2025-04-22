package pers.lwb.service;

import pers.lwb.dto.EmployeeDTO;
import pers.lwb.dto.EmployeeLoginDTO;
import pers.lwb.entity.Employee;
import pers.lwb.vo.PageVO;

public interface EmployeeService {

    Employee login(EmployeeLoginDTO employeeLoginDTO);

    void insert(Employee employee);

    PageVO<Employee> page(String name, Integer pageNum, Integer pageSize);

    void setStatus(Long id, Integer status);

    Employee getById(Long id);

    void update(EmployeeDTO employeeDTO);
}
