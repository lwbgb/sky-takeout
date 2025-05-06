package pers.lwb.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.lwb.entity.SetmealDish;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SetmealDTO {

    private Long id;

    @Schema(defaultValue ="13", description = "套餐种类")
    private Long categoryId;

    @Schema(defaultValue ="薯条汉堡三剑客", description = "套餐名称")
    private String name;

    @Schema(defaultValue ="29", description = "套餐价格")
    private BigDecimal price;

    private Integer status;

    @Schema(defaultValue ="薯条，汉堡，可乐", description = "套餐描述")
    private String description;

    private String image;

    @Schema(description = "套餐包含的菜品")
    private List<SetmealDish> setmealDishes = new ArrayList<>();
}
