package com.sky.controller.admin;


import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dish")
@Slf4j
@Api(tags = "菜品相关接口")
public class DishController {

    @Autowired
    private DishService dishService;
    /**
     * 新增菜品
     * @param dishDTO
     * @return
     */
    @PostMapping()
    @ApiOperation(value = "新增菜品")
    public Result save(@RequestBody DishDTO dishDTO){

        log.info("新增菜品：{}",dishDTO);

        dishService.saveWithFlavor(dishDTO);

        return Result.success();
    }

    /**
     * 分页查询
     * @param dishPageQueryDTO
     * @return
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/page")
    public Result<PageResult> pageQuery(DishPageQueryDTO dishPageQueryDTO){

        log.info("分页查询菜品：{}",dishPageQueryDTO);

        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);

        return Result.success(pageResult);
    }

    /**
     *批量删除
     * @param list
     * @return
     */
    @DeleteMapping()
    @ApiOperation(value = "菜品批量删除")
    public Result delete(@RequestParam(value = "ids") List<Long> list){

        log.info("批量删除菜品：{}",list);
        dishService.delete(list);

        return Result.success();
    }

    /**
     * 根据id查询菜品
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询菜品")
    public Result<DishVO> getById(@PathVariable Long id){

        log.info("根据id查询菜品：{}",id);
        DishVO dishVO = dishService.getById(id);
        return Result.success(dishVO);
    }

    /**
     * 修改菜品
     * @param dishDTO
     * @return
     */

    @PutMapping()
    @ApiOperation(value = "修改菜品")
    public Result updateDish(@RequestBody DishDTO dishDTO){

        log.info("修改菜品：{}",dishDTO);

        dishService.updateDish(dishDTO);
        return Result.success();
    }

    /**
     * 菜品起售停售
     * @param status
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation(value = "菜品起售停售")
    public Result startAndStop(@PathVariable Integer status, Long id){

        log.info("菜品起售停售,状态：{}",status);
        dishService.startAndStop(status,id);

        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation(value = "根据分类id查询菜品")
    public Result<List> selectByCategoryId(Long categoryId){

        log.info("根据分类id查询菜品:{}",categoryId);
        List<Dish> dishes = dishService.selectByCategoryId(categoryId);

        return Result.success(dishes);
    }
}
