package pers.lwb.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema(title = "EmployeeLoginDTO", description = "用户登录时传输给客户端的数据类型")
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeLoginDTO {

    @Schema(title = "用户名", defaultValue = "admin")
    private String username;

    @Schema(title = "密码", defaultValue = "sayuri")
    private String password;
}
