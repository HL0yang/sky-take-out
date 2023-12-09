package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {
    List<ShoppingCart> getCart(ShoppingCart shoppingCart);

    @Update("update shopping_cart set number = #{number} where id = #{id}" )
    void updateNumber(ShoppingCart shoppingCart);

    void insert(ShoppingCart shoppingCart);

    @Delete("delete from shopping_cart where id = #{id}")
    void delete(ShoppingCart shoppingCart1);

    @Delete("delete from shopping_cart")
    void deleteAll();
}
