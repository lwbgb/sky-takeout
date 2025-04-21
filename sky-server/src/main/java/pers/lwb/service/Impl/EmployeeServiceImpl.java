package pers.lwb.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import pers.lwb.constant.MessageConstant;
import pers.lwb.constant.PasswordConstant;
import pers.lwb.constant.StatusConstant;
import pers.lwb.dto.EmployeeLoginDTO;
import pers.lwb.entity.Employee;
import pers.lwb.exception.AccountNotFoundException;
import pers.lwb.exception.EmployeeInsertException;
import pers.lwb.exception.PasswordErrorException;
import pers.lwb.mapper.EmployeeMapper;
import pers.lwb.service.EmployeeService;
import pers.lwb.vo.EmployeePageVO;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeMapper mapper;

    public EmployeeServiceImpl(EmployeeMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * 员工登录
     * @param dto   用户登录时传输给客户端的数据类型
     * @return      登录员工对象
     */
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

    /**
     * 新增员工
     * @param employee   员工对象
     * @return           插入个数(0/1)
     */
    @Override
    public boolean insert(Employee employee) {
        // 补充员工属性
        String defaultPwd = DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes());
        employee.setPassword(defaultPwd);
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        employee.setStatus(StatusConstant.ENABLE);

        // 从 ThreadLocal 中取出 empId
//        Long empId = LocalContext.getCurrentId();
//        log.info("empId：{}", empId);

        int n = mapper.insert(employee);
        if (n <= 0)
            throw new EmployeeInsertException(MessageConstant.EMPLOYEE_INSERT_ERROR);
        return true;
    }

    /**
     * 分页查询
     * @param name      员工姓名
     * @param pageNum   页数
     * @param pageSize  查询条数
     * @return  查询结果
     */
    @Override
    public EmployeePageVO page(String name, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Employee> list = mapper.list(name);
        Page<Employee> page = (Page<Employee>) list;
        return new EmployeePageVO(page.getTotal(), page.getResult());
    }
}






