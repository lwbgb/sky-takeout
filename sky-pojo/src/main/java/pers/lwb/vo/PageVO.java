package pers.lwb.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Schema(title = "员工分页查询返回给客户端的结果类")
@AllArgsConstructor
@NoArgsConstructor
public class PageVO<T> {

    private Long total;

    private List<T> records;
}
