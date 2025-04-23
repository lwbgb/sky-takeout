package pers.lwb.mapper;

import org.apache.ibatis.annotations.Mapper;
import pers.lwb.annotation.AutoFill;
import pers.lwb.entity.Employee;
import pers.lwb.enumeration.OperationType;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    Employee getByUername(String username);

    @AutoFill(OperationType.INSERT)
    int insert(Employee employee);

    List<Employee> list(String name);

    List<Employee> page(Integer start, Integer pageSize);

    @AutoFill(OperationType.UPDATE)
    int update(Employee employee);

    Employee getById(Long id);
}
