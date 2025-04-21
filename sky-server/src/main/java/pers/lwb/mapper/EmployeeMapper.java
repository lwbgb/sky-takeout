package pers.lwb.mapper;

import org.apache.ibatis.annotations.Mapper;
import pers.lwb.entity.Employee;

@Mapper
public interface EmployeeMapper {

    Employee getByUername(String username);

    int insert(Employee employee);
}
