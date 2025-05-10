package pers.lwb.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;
import pers.lwb.constant.MessageConstant;
import pers.lwb.dto.SetmealDTO;
import pers.lwb.dto.SetmealPageDTO;
import pers.lwb.result.Result;
import pers.lwb.service.SetmealService;
import pers.lwb.vo.PageVO;
import pers.lwb.vo.SetmealVO;

import java.util.List;

@Slf4j
@SuppressWarnings(value = "SpellCheckingInspection")
@Tag(name = "Admin SetmealController")
@RestController("adminSetmealController")
@RequestMapping("/admin/setmeal")
public class SetmealController {

    private final SetmealService setmealService;

    public SetmealController(SetmealService setmealService) {
        this.setmealService = setmealService;
    }

    @Operation(summary = "新增套餐")
    @CacheEvict(cacheNames = "setmealCache", key = "#setmealDTO.categoryId")
    @PostMapping
    public Result<String> insert(@RequestBody SetmealDTO setmealDTO) {
        log.info("新增套餐：{}", setmealDTO);
        setmealService.insert(setmealDTO);
        return Result.success(MessageConstant.SETMEAL_INSERT_SUCCESS);
    }

    @Operation(summary = "根据 id 查询套餐及菜品信息")
    @GetMapping("/{id}")
    public Result<SetmealVO> getById(@PathVariable Long id) {
        log.info("根据 id 查询套餐：{}", id);
        SetmealVO setmealVO = setmealService.getById(id);
        return Result.success(setmealVO);
    }

    @Operation(summary = "套餐分页查询")
    @GetMapping("/page")
    public Result<PageVO<SetmealVO>> page(@ParameterObject SetmealPageDTO setmealPageDTO) {
        log.info("分页查询：{}", setmealPageDTO);
        PageVO<SetmealVO> page = setmealService.page(setmealPageDTO);
        return Result.success(page);
    }

    @Operation(summary = "批量删除套餐")
    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    @DeleteMapping
    public Result<String> delete(@RequestParam List<Long> ids) {
        log.info("批量删除套餐：{}", ids);
        setmealService.delete(ids);
        return Result.success(MessageConstant.SETMEAL_DELETE_SUCCESS);
    }

    @Operation(summary = "修改套餐")
    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    @PutMapping
    public Result<String> update(@RequestBody SetmealDTO setmealDTO) {
        log.info("修改套餐信息：{}", setmealDTO);
        setmealService.update(setmealDTO);
        return Result.success(MessageConstant.SETMEAL_UPDATE_SUCCESS);
    }

    @Operation(summary = "启售/禁售套餐")
    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    @PostMapping("status/{status}")
    public Result<String> setStatus(Long id, @PathVariable Integer status) {
        log.info("{}套餐：{}", status == 1 ? "启售" : "禁售", id);
        setmealService.setStatus(id, status);
        return Result.success(MessageConstant.SETMEAL_SET_STATUS_SUCCESS);
    }
}
