package pers.lwb.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Schema(title = "菜品类")
@NoArgsConstructor
@AllArgsConstructor
public class Dish {

    private Long id;

    private String image;

    private String name;

    private double price;

    private Integer status;

    @Schema(description = "1-菜品分类 2-套餐分类")
    private Integer categoryId;

    private String description;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createUser;

    private Long updateUser;
}
