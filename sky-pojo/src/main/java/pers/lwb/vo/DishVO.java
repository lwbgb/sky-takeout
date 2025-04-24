package pers.lwb.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishVO {
    private Long id;

    private String image;

    private String name;

    private double price;

    private Integer status;

    @Schema(description = "1-菜品分类 2-套餐分类")
    private Long categoryId;

    private String description;

    private LocalDateTime updateTime;

    private String categoryName;
}
