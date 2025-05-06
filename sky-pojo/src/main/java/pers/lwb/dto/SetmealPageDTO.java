package pers.lwb.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SetmealPageDTO {

    @Schema(defaultValue = "16")
    private Long categoryId;

    @Schema(defaultValue = "鱼")
    private String name;

    @Schema(defaultValue = "1")
    private Integer page;

    @Schema(defaultValue = "10")
    private Integer pageSize;

    @Schema(defaultValue = "0")
    private Integer status;
}
