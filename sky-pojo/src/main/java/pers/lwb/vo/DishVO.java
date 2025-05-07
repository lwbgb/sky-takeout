package pers.lwb.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.lwb.entity.DishFlavor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishVO implements Serializable {
    private Long id;

    private String image;

    private String name;

    private BigDecimal price;

    private Integer status;

    @Schema(description = "1-菜品分类 2-套餐分类")
    private Long categoryId;

    private String description;

    private LocalDateTime updateTime;

    private String categoryName;

    @Schema(hidden = true, description = "菜品口味")
    private List<DishFlavor> flavors = new ArrayList<>();
}
