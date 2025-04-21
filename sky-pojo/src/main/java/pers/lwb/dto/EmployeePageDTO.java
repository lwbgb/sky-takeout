package pers.lwb.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema(description = "分页查询传输对象")
@AllArgsConstructor
@NoArgsConstructor
public class EmployeePageDTO {

    @Schema(defaultValue = "sayuri")
    private String name;

    @Schema(name = "page", defaultValue = "1")
    private Integer page;

    @Schema(defaultValue = "10")
    private Integer pageSize;
}
