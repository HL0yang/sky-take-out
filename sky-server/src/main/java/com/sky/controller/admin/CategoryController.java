package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/admin/category")
@Api(tags = "菜品管理相关接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     * @param categoryDTO
     * @return
     */
    @PostMapping()
    @ApiOperation(value = "新增分类")
    public Result save(@RequestBody CategoryDTO categoryDTO){

        log.info("新增分类：{}",categoryDTO);
        categoryService.save(categoryDTO);

        return Result.success();
    }

    /**
     *分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    @ApiOperation(value = "分类分页查询")
    @GetMapping("/page")
    public Result<PageResult> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO){
        log.info("分类分页查询：{}",categoryPageQueryDTO);
        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);

        return Result.success(pageResult);
    }

    /**
     * 根据id删除员工
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id删除")
    @DeleteMapping()
    public Result deleteById(Long id){
        log.info("删除员工id：{}",id);
        categoryService.deleteById(id);

        return Result.success();
    }

    /**
     * 根据类型查询
     * @param type
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "根据类型查询")
    public Result<List> getByType(Integer type){

        log.info("根据菜品类型查询为:{}",type);

        List<Category> list  = categoryService.getByType(type);

        return Result.success(list);
    }

    /**
     * 启用禁用
     * @param status
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation(value = "启用禁用")
    public Result startAndStop(@PathVariable Integer status,Long id){
        log.info("启用禁用状态：{}，id为：{}",status,id);

        categoryService.startAndStop(status,id);
        return Result.success();
    }

    /**
     * 修改分类
     * @param categoryDTO
     * @return
     */
    @PutMapping()
    @ApiOperation(value = "修改分类")
    public Result updateCategory(@RequestBody CategoryDTO categoryDTO){

        log.info("修改分类：{}",categoryDTO);
        categoryService.updateCategory(categoryDTO);

        return Result.success();
    }
}
