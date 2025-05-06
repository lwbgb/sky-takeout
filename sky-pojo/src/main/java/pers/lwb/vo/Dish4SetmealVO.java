package pers.lwb.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dish4SetmealVO {

    private String name;

    private String image;

    private String description;

    private Integer copies;
}
