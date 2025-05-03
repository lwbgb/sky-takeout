package pers.lwb.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.lwb.constant.JwtClaimsConstant;
import pers.lwb.dto.UserLoginDTO;
import pers.lwb.entity.User;
import pers.lwb.properties.JwtProperties;
import pers.lwb.result.Result;
import pers.lwb.service.UserService;
import pers.lwb.utils.JwtUtils;
import pers.lwb.vo.UserLoginVO;

import java.util.HashMap;

@Slf4j
@Tag(name = "UserController")
@RestController
@RequestMapping("/user/user")
public class UserController {

    private final UserService userService;

    private final JwtProperties jwtProperties;

    public UserController(UserService userService, JwtProperties jwtProperties) {
        this.userService = userService;
        this.jwtProperties = jwtProperties;
    }

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result<UserLoginVO> wxLogin(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("微信用户登录：{}", userLoginDTO);
        User user = userService.login(userLoginDTO);

        HashMap<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String jwe = JwtUtils.createJwe(jwtProperties.getUserSecretKey(), claims, jwtProperties.getUserTtl());

        return Result.success(new UserLoginVO(user.getId(), user.getOpenid(), jwe));
    }
}
