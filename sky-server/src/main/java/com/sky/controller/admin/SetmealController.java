package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@Slf4j
@Api(tags = "套餐相关接口")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    /**
     * 新增套餐
     * @param setmealDTO
     * @return
     */
    @PostMapping()
    @ApiOperation(value = "新增套餐")
    public Result save(@RequestBody SetmealDTO setmealDTO){
        log.info("新增套餐：{}",setmealDTO);

        setmealService.save(setmealDTO);

        return Result.success();
    }


    /**
     * 套餐分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value = "套餐分页查询")
    public Result<PageResult> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO){

        PageResult pageResult = setmealService.pageQuery(setmealPageQueryDTO);

        return Result.success(pageResult);
    }


    @DeleteMapping()
    @ApiOperation(value = "批量删除套餐")
    public Result deleteByIds(List<Long> ids){

        log.info("批量删除套餐：{}",ids);
        setmealService.deleteByIds(ids);

        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询套餐")
    public Result<Setmeal> selectById(@PathVariable Long id){

        log.info("根据id查询套餐：{}",id);
        Setmeal setmeal = setmealService.selectById(id);
        return Result.success(setmeal);
    }


    @ApiOperation(value = "起售停售套餐")
    @PostMapping("/status/{status}")
    public Result startAndStop(@PathVariable Integer status,Long id){

        setmealService.startAndStop(status,id);

        return Result.success();
    }


    @PutMapping()
    @ApiOperation(value = "修改套餐")
    public Result updateSetmeal(@RequestBody SetmealDTO setmealDTO){

        setmealService.updateSetmeal(setmealDTO);

        return Result.success();
    }
}
