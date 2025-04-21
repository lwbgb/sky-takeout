package pers.lwb.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Schema(title = "EmployeeLoginVO", description = "用户登录时用于前端页面显示")
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeLoginVO {
    @Schema(title = "员工 id")
    private Long id;

    @Schema(title = "用户名")
    private String username;

    @Schema(title = "员工姓名")
    private String name;

    @Schema(title = "JWT 令牌")
    private String token;
}
