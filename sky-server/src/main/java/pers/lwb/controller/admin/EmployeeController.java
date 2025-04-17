package pers.lwb.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.lwb.constant.JwtClaimsConstant;
import pers.lwb.dto.EmployeeLoginDTO;
import pers.lwb.entity.Employee;
import pers.lwb.properties.JwtProperties;
import pers.lwb.result.Result;
import pers.lwb.service.EmployeeService;
import pers.lwb.utils.JwtUtils;
import pers.lwb.vo.EmployeeLoginVO;

import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/admin/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    private final JwtProperties properties;

    public EmployeeController(EmployeeService employeeService, JwtProperties properties) {
        this.employeeService = employeeService;
        this.properties = properties;
    }

    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        HashMap<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());

        String jws = JwtUtils.createJws(claims, properties.getAdminTtl());

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .name(employee.getName())
                .username(employee.getUsername())
                .token(jws)
                .build();

        return Result.success(employeeLoginVO);
    }
}
