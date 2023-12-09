package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 添加购物车
     * @param shoppingCartDTO
     */
    public void add(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        //获取当前的userId
        shoppingCart.setUserId(BaseContext.getCurrentId());
        //判断购物车中是否是由菜品或套餐,判断条件有userId,(dishId,flavors),(setmeal)
        List<ShoppingCart> shoppingCarts = shoppingCartMapper.getCart(shoppingCart);

        //判断购物车中是否有商品
        if(shoppingCarts!=null && shoppingCarts.size()!=0){
            //有商品，只需要修改购物车中的数量
            shoppingCart = shoppingCarts.get(0);
            int number = shoppingCart.getNumber()+1;
            shoppingCart.setNumber(number);
            shoppingCartMapper.updateNumber(shoppingCart);
        }else {
            //无商品，将添加到购物车
            //判断是菜品还是套餐
            Long dishId = shoppingCartDTO.getDishId();
            Long setmealId = shoppingCartDTO.getSetmealId();
            if(dishId!=null){
                Dish dish = dishMapper.getById(dishId);
                shoppingCart.setAmount(dish.getPrice());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setName(dish.getName());
            }else {
                Setmeal setmeal = setmealMapper.selectById(setmealId);
                shoppingCart.setAmount(setmeal.getPrice());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setName(setmeal.getName());
            }
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());

            //添加到数据库中
            shoppingCartMapper.insert(shoppingCart);
        }

    }

    /**
     * 查看购物车
     * @return
     */
    public List<ShoppingCart> getShoppingCart() {
        ShoppingCart shoppingCart =  new ShoppingCart();
        List<ShoppingCart> list = shoppingCartMapper.getCart(shoppingCart);

        return list;
    }

    /**
     * 删除购物车中的一个商品
     * @param shoppingCartDTO
     */
    public void subShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        //判断购物车中是否有商品
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());

        List<ShoppingCart> list = shoppingCartMapper.getCart(shoppingCart);
        if(list!=null && list.size()!=0){
            list.forEach(shoppingCart1 -> {
                int number = shoppingCart1.getNumber()-1;
                if(number==0){
                    shoppingCartMapper.delete(shoppingCart1);
                }
                shoppingCart1.setNumber(number);
                shoppingCartMapper.updateNumber(shoppingCart1);
            });
        }

    }

    /**
     * 清空购物车
     */
    public void deleteShoppingCart() {
        shoppingCartMapper.deleteAll();
    }
}
