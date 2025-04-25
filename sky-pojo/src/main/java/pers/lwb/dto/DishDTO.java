package pers.lwb.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.lwb.entity.DishFlavor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishDTO {

    private Long id;

    @Schema(defaultValue = "1.jpg")
    private String image;

    @Schema(defaultValue = "番茄炒蛋")
    private String name;

    @Schema(defaultValue = "18")
    private double price;

    @Schema(defaultValue = "19", description = "1-菜品分类 2-套餐分类")
    private Long categoryId;

    @Schema(hidden = true, description = "菜品描述")
    private String description;

    @Schema(hidden = true, description = "菜品口味")
    private List<DishFlavor> flavors = new ArrayList<>();
}
