package pers.lwb.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import pers.lwb.constant.MessageConstant;
import pers.lwb.dto.EmployeeLoginDTO;
import pers.lwb.entity.Employee;
import pers.lwb.exception.AccountNotFoundException;
import pers.lwb.exception.PasswordErrorException;
import pers.lwb.mapper.EmployeeMapper;
import pers.lwb.service.EmployeeService;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeMapper mapper;

    public EmployeeServiceImpl(EmployeeMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Employee login(EmployeeLoginDTO dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();

        Employee employee = mapper.getByUername(username);

        // 1. 验证用户名是否存在
        if (employee == null)
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);

        // 2. 验证密码是否正确
        password = DigestUtils.md5DigestAsHex(password.getBytes()); // MD5 编码处理
        log.info("MD5 编码：{}", password);
        if (!password.equals(employee.getPassword()))
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);

        return employee;
    }
}
