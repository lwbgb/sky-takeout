package pers.lwb.mapper;

import org.apache.ibatis.annotations.Mapper;
import pers.lwb.entity.Employee;

@Mapper
public interface EmployeeMapper {

    public Employee getByUername(String username);
}
