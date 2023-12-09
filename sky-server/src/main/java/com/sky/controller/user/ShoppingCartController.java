package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@Api(tags = "C端-购物车接口")
@RequestMapping("/user/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;
    /**
     * 添加购物车
     * @param shoppingCartDTO
     * @return
     */
    @ApiOperation(value = "C端-添加购物车")
    @PostMapping("/add")
    public Result addShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO){

        log.info("添加购物车：{}",shoppingCartDTO);
        shoppingCartService.add(shoppingCartDTO);

        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation(value = "查看购物车")
    public Result<List> getShoppingCart(){

        log.info("查看购物车..");
        List<ShoppingCart> list = shoppingCartService.getShoppingCart();

        return Result.success(list);
    }

    @ApiOperation(value = "删除购物车中的一个商品")
    @PostMapping("/sub")
    public Result subShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO){

        log.info("删除购物车中的一个商品");

        shoppingCartService.subShoppingCart(shoppingCartDTO);
        return Result.success();
    }

    /**
     * 清空购物车
     * @return
     */
    @DeleteMapping("clean")
    @ApiOperation(value = "清空购物车")
    public Result deleteShoppingCart(){
        shoppingCartService.deleteShoppingCart();
        return Result.success();
    }
}
