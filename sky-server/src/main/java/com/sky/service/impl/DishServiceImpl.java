package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;


    /**
     * 新增菜品
     * @param dishDTO
     */
    @Transactional //由于要对两个数据表进行插入操作，所以要引入事务管理。
    public void saveWithFlavor(DishDTO dishDTO) {

        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.save(dish);
        //获取插入语句后的菜品id值
        Long id = dish.getId();

        List<DishFlavor> flavors =dishDTO.getFlavors();
        if(flavors!=null||flavors.size()!=0){
            flavors.forEach(flavor->{
                flavor.setDishId(id);
            });

            dishFlavorMapper.save(flavors);
        }

    }

    /**
     * 分页查询
     * @param dishPageQueryDTO
     * @return
     */
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {

        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.pageSelect(dishPageQueryDTO);

        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 批量删除
     * @param list
     */
    @Transactional
    public void delete(List<Long> list) {

        //1.若要删除的菜品状态是启用状态，则不能删除。
        for(Long id : list){
            Dish dish = dishMapper.getById(id);
            if(dish.getStatus()== StatusConstant.ENABLE){
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        //2.查看当前菜品是否被关联了
        List<SetmealDish> setmealDishes = setmealDishMapper.getByDishIds(list);
        if(setmealDishes!=null&&setmealDishes.size()>=0){
            //当前关联套餐，不能删除
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        //3.删除菜品表
        dishMapper.deleteByIds(list);
        //4.删除关联的口味表
        dishFlavorMapper.deleteByDishIds(list);

    }

    /**
     * 根据id查询菜品
     * @param id
     * @return
     */
    public DishVO getById(Long id) {
        Dish dish = dishMapper.getById(id);
        List<DishFlavor> flavors = dishFlavorMapper.getByDishId(id);

        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish,dishVO);
        dishVO.setFlavors(flavors);

        return dishVO;
    }

    /**
     * 修改菜品
     * @param dishDTO
     */
    @Transactional
    public void updateDish(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);

        dishMapper.updateDish(dish);
        //如果说直接修改口味表，这涉及到数据库中的多条数据一起修改
        //并且前端的修改方法也是多种多样的，可以添加口味，也可以删除某种口味后再添加一种新的口味...
        //实现方法：先删除，再插入

        List<Long> dishes =  new ArrayList<>();
        dishes.add(dishDTO.getId());
        dishFlavorMapper.deleteByDishIds(dishes);

        List<DishFlavor> dishFlavors = dishDTO.getFlavors();
        if(dishFlavors!=null && dishFlavors.size()!=0){
            dishFlavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishDTO.getId());
            });
        }
        dishFlavorMapper.save(dishFlavors);
    }

    /**
     * 菜品起售停售
     * @param status
     */
    public void startAndStop(Integer status) {

        Dish dish = new Dish();
        dish.setStatus(status);
        dishMapper.updateDish(dish);

    }

    @Override
    public List<Dish> selectByCategoryId(Long categoryId) {

        List<Dish> dishes = dishMapper.selectByCategoryId(categoryId);
        return dishes;
    }
}
