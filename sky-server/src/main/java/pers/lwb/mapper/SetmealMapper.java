package pers.lwb.mapper;

import org.apache.ibatis.annotations.Mapper;
import pers.lwb.annotation.AutoFill;
import pers.lwb.dto.SetmealPageDTO;
import pers.lwb.entity.Setmeal;
import pers.lwb.enumeration.OperationType;
import pers.lwb.vo.Dish4SetmealVO;
import pers.lwb.vo.SetmealVO;

import java.util.List;

@Mapper
public interface SetmealMapper {

    Long countByCategoryId(Long categoryId);

    List<SetmealVO> page(SetmealPageDTO setmealPageDTO);

    List<Dish4SetmealVO> getDishById(Long id);

    @AutoFill(OperationType.INSERT)
    int insert(Setmeal setmeal);

    Setmeal getById(Long id);

    int delete(List<Long> ids);

    List<Setmeal> list(Setmeal setmeal);

    @AutoFill(OperationType.UPDATE)
    int update(Setmeal setmeal);
}
