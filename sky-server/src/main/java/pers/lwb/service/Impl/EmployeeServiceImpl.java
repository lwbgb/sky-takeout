package pers.lwb.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import pers.lwb.constant.MessageConstant;
import pers.lwb.constant.PasswordConstant;
import pers.lwb.constant.StatusConstant;
import pers.lwb.context.LocalContext;
import pers.lwb.dto.EmployeeDTO;
import pers.lwb.dto.EmployeeLoginDTO;
import pers.lwb.entity.Employee;
import pers.lwb.exception.*;
import pers.lwb.mapper.EmployeeMapper;
import pers.lwb.service.EmployeeService;
import pers.lwb.vo.PageVO;

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
     *
     * @param dto 用户登录时传输给客户端的数据类型
     * @return 登录员工对象
     */
    @Override
    public Employee login(EmployeeLoginDTO dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();

        Employee employee = mapper.getByUername(username);

        // 1. 验证用户名是否存在
        if (employee == null)
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);

        // 2. 验证账号是否被锁定
        if (employee.getStatus() == 0)
            throw new AccountLockedException(MessageConstant.LOGIN_FAILED + MessageConstant.ACCOUNT_LOCKED);

        // 3. 验证密码是否正确
        password = DigestUtils.md5DigestAsHex(password.getBytes()); // MD5 编码处理
        log.info("MD5 编码：{}", password);
        if (!password.equals(employee.getPassword()))
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);

        return employee;
    }

    /**
     * 新增员工
     *
     * @param employee 员工对象
     */
    @Override
    @Transactional(rollbackFor = BaseException.class) // 设置出现新增员工异常则回滚
    public void insert(Employee employee) {
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
            throw new InsertException(MessageConstant.EMPLOYEE_INSERT_ERROR);
    }

    /**
     * 分页查询
     *
     * @param name     员工姓名
     * @param pageNum  页数
     * @param pageSize 查询条数
     * @return 查询结果
     */
    @Override
    public PageVO<Employee> page(String name, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Employee> list = mapper.list(name);
        Page<Employee> page = (Page<Employee>) list;
        return new PageVO<>(page.getTotal(), page.getResult());
    }

    /**
     * 启用/禁用账号
     *
     * @param status 账号状态
     */
    @Override
    @Transactional(rollbackFor = BaseException.class)
    public void setStatus(Long id, Integer status) {
        Employee employee = Employee.builder()
                .id(id)
                .status(status)
                .build();
        int n = mapper.update(employee);
        if (n < 0)
            throw new SetStatusErrorException(MessageConstant.ACCOUNT_SET_STATUS_ERROR);
    }

    /**
     * 根据 id 查询员工信息
     *
     * @param id 员工 id
     * @return 员工信息
     */
    @Override
    public Employee getById(Long id) {
        Employee employee = mapper.getById(id);

        if (employee == null)
            throw new EmployeeNotFoundException(MessageConstant.EMPLOYEE_NOT_FOUND);

        employee.setPassword("******");
        return employee;
    }

    /**
     * 更新员工信息
     *
     * @param employeeDTO 员工信息
     */
    @Override
    @Transactional(rollbackFor = BaseException.class)
    public void update(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        // 设置更新时间和操作人
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(LocalContext.getCurrentId());

        int n = mapper.update(employee);
        if (n <= 0)
            throw new UpdateException(MessageConstant.EMPLOYEE_UPDATE_ERROR);
    }
}






