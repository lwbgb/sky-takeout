package pers.lwb.service.Impl;

import org.springframework.stereotype.Service;
import pers.lwb.dto.EmployeeLoginDTO;
import pers.lwb.entity.Employee;
import pers.lwb.mapper.EmployeeMapper;
import pers.lwb.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeMapper mapper;

    public EmployeeServiceImpl(EmployeeMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Employee login(EmployeeLoginDTO dto) {
        return mapper.getByUername(dto.getUsername());
    }
}
