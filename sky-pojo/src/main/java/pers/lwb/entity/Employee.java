package pers.lwb.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Schema(title = "Employee")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Serializable {
    private Long id;

    @Schema(defaultValue = "sayuri")
    private String name;

    @Schema(defaultValue = "sayuri")
    private String username;

    private String password;

    @Schema(defaultValue = "15961592121")
    private String phone;

    @Schema(defaultValue = "男")
    private String sex;

    @Schema(defaultValue = "320205202004052394")
    private String idNumber;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createUser;

    private Long updateUser;
}
