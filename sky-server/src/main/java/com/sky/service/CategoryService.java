package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;


public interface CategoryService {

    /**
     * 新增分类
     * @param categoryDTO
     */
    void save(CategoryDTO categoryDTO);

    /**
     * 分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 根据id删除员工
     * @param id
     */
    void deleteById(Long id);

    /**
     * 根据类型查询
     * @param type
     * @return
     */
    List<Category> getByType(Integer type);

    /**
     * 启用禁用
     *
     * @param status
     * @param id
     */
    void startAndStop(Integer status, Long id);


    /**
     * 修改分类
     * @param categoryDTO
     */
    void updateCategory(CategoryDTO categoryDTO);
}
