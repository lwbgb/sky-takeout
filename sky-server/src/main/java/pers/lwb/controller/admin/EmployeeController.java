package pers.lwb.controller.admin;

import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import pers.lwb.constant.JwtClaimsConstant;
import pers.lwb.constant.MessageConstant;
import pers.lwb.dto.EmployeeDTO;
import pers.lwb.dto.EmployeeLoginDTO;
import pers.lwb.dto.EmployeePageDTO;
import pers.lwb.entity.Employee;
import pers.lwb.properties.JwtProperties;
import pers.lwb.result.Result;
import pers.lwb.service.EmployeeService;
import pers.lwb.utils.JwtUtils;
import pers.lwb.vo.EmployeeLoginVO;
import pers.lwb.vo.EmployeePageVO;

import java.util.HashMap;


@Tag(name = "Admin EmployeeController")
@Slf4j
@RestController("adminEmployeeController")
@RequestMapping("/admin/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    private final JwtProperties properties;

    public EmployeeController(EmployeeService employeeService, JwtProperties properties, HttpServletRequest request) {
        this.employeeService = employeeService;
        this.properties = properties;
    }


    @Operation(summary = "员工登录", description = "管理员用户登录请求")
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        HashMap<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());

        String jwe = JwtUtils.createJwe(properties.getAdminSecretKey(), claims, properties.getAdminTtl());

        log.info("登录成功！生成 JWT令牌：{}", jwe);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .name(employee.getName())
                .username(employee.getUsername())
                .token(jwe)
                .build();

        return Result.success(employeeLoginVO);
    }

    @Operation(summary = "新增员工")
    @PostMapping
    public Result<Object> insert(@RequestBody EmployeeDTO employeeDTO, HttpServletRequest request) {
        log.info("新增员工：{}", employeeDTO);

        // TODO 从 JWE 中获取管理员 id
        String jwe = request.getHeader("token");
        Claims claims = JwtUtils.parseJwe(jwe, properties.getAdminSecretKey());
        Long empId = Long.valueOf(claims.get(JwtClaimsConstant.EMP_ID).toString());
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);

        employeeService.insert(employee);
        return Result.success(MessageConstant.EMPLOYEE_INSERT_SUCCESS);
    }

    @Operation(summary = "员工分页查询")
    @GetMapping("/page")
    public Result<EmployeePageVO> page(@ParameterObject EmployeePageDTO employeePageDTO) {
        log.info("员工分页查询：{}", employeePageDTO);
        EmployeePageVO pageVO = employeeService.page(employeePageDTO.getName(), employeePageDTO.getPage(), employeePageDTO.getPageSize());
        return Result.success(pageVO);
    }

    @Operation(summary = "设置账号状态")
    @Parameters({
            @Parameter(name = "id", in = ParameterIn.DEFAULT),
            @Parameter(name = "status", in = ParameterIn.PATH)
    })
    @PostMapping("/status/{status}")
    public Result<String> setStatus(Long id, @PathVariable Integer status) {
        log.info("启用/禁用员工账号：id: {}, status: {}", id, status);
        employeeService.setStatus(id, status);
        return Result.success(MessageConstant.ACCOUNT_SET_STATUS_SUCCESS);
    }

    @Operation(summary = "根据 id 查询员工")
    @GetMapping("/{id}")
    public Result<Employee> getById(@PathVariable Long id) {
        log.info("根据 id 查询员工：{}", id);
        Employee employee = employeeService.getById(id);
        return Result.success(employee);
    }


    @Operation(summary = "更新员工信息")
    @PutMapping
    public Result<String> update(@RequestBody EmployeeDTO employeeDTO) {
        log.info("更新员工信息：{}", employeeDTO);
        employeeService.update(employeeDTO);
        return Result.success(MessageConstant.EMPLOYEE_UPDATE_SUCCESS);
    }
}







