package pers.lwb.mapper;

import org.apache.ibatis.annotations.Mapper;
import pers.lwb.entity.Employee;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    Employee getByUername(String username);

    int insert(Employee employee);

    List<Employee> list(String name);

    List<Employee> page(Integer start, Integer pageSize);

    int update(Employee employee);

    Employee getById(Long id);
}
