package pers.lwb.entity;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Schema(title = "菜品口味")
@AllArgsConstructor
@NoArgsConstructor
public class DishFlavor implements Serializable {

    private Long id;

    private Long dishId;

    private String name;

    private String value;
}
