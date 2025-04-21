package pers.lwb.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema(title = "EmployeeDTO")
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    @Schema(title = "员工姓名", defaultValue = "sayuri")
    private String name;

    @Schema(title = "用户名", defaultValue = "sayuri")
    private String username;

    @Schema(title = "电话号码", defaultValue = "15961592121")
    private String phone;

    @Schema(title = "性别", defaultValue = "男")
    private String sex;

    @Schema(title = "身份证号码", defaultValue = "320205202004052394")
    private String idNumber;
}
