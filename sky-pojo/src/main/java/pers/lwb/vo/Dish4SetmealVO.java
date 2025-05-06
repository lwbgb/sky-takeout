package pers.lwb.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dish4SetmealVO {

    @Schema(defaultValue = "薯条")
    private String name;

    @Schema(defaultValue = "https://sky-takeout-sayuri.oss-cn-hangzhou.aliyuncs.com/image/70bcf005-0bad-4914-aaf6-9210f527ba23.png")
    private String image;

    @Schema(defaultValue = "中份")
    private String description;

    @Schema(defaultValue = "1")
    private Integer copies;
}
