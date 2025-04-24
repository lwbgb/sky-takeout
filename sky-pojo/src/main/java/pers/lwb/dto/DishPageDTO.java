package pers.lwb.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema(title = "菜品分页查询 DTO")
@AllArgsConstructor
@NoArgsConstructor
public class DishPageDTO {

    @Schema(defaultValue = "鱼")
    private String name;

    @Schema(defaultValue = "1")
    private Integer status;

    @Schema(defaultValue = "20")
    private Long categoryId;

    @Schema(defaultValue = "1")
    private int page;

    @Schema(defaultValue = "10")
    private int pageSize;
}
