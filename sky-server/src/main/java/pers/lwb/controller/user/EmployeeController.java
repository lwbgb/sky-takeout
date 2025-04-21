package pers.lwb.controller.user;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User EmployeeController")
@RestController("userEmployeeController")
@RequestMapping("/user/employee")
public class EmployeeController {

}
