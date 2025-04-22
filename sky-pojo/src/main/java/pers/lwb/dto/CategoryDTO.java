package pers.lwb.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private Long id;

    @Schema(description = "分类名称")
    private String name;

    @Schema(description = "排序字段")
    private Integer sort;

    @Schema(description = "1-菜品分类 2-套餐分类")
    private Integer type;
}
