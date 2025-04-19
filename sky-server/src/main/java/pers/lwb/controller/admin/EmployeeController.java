package pers.lwb.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

@Tag(name = "EmployeeController")
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


    @Operation(summary = "login", description = "管理员用户登录请求")
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        HashMap<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());

        String jws = JwtUtils.createJws(claims, properties.getAdminTtl());

        log.info("登录成功！生成 JWT令牌：{}", jws);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .name(employee.getName())
                .username(employee.getUsername())
                .token(jws)
                .build();

        return Result.success(employeeLoginVO);
    }
}
