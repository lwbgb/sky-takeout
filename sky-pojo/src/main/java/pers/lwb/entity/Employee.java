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

    @Schema(defaultValue = "1")
    private String sex;

    @Schema(defaultValue = "320205202004052394")
    private String idNumber;

    private Integer status;

//    @JsonFormat(pattern = "yy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

//    @JsonFormat(pattern = "yy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private Long createUser;

    private Long updateUser;

    public void fillInfo(LocalDateTime createTime, LocalDateTime updateTime, Long createUser, Long updateUser) {
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.createUser = createUser;
        this.updateUser = updateUser;
    }

    public void fillInfo(LocalDateTime updateTime, Long updateUser) {
        this.updateTime = updateTime;
        this.updateUser = updateUser;
    }
}
