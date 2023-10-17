package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "店铺相关接口")
@RequestMapping("/admin/shop")
@Slf4j
public class ShopController {

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String STATUS = "SHOP_STATUS";

    /**
     * 设置店铺状态
     * @param status
     * @return
     */
    @ApiOperation(value = "设置店铺状态")
    @PutMapping("/{status}")
    public Result setShopStatus(@PathVariable Integer status){

        log.info("设置店铺状态：{}",status);

        redisTemplate.opsForValue().set("SHOP_STATUS",status);

        return Result.success();
    }

    /**
     * 查询店铺状态
     * @return
     */
    @ApiOperation(value = "查询店铺状态")
    @GetMapping("/status")
    public Result<Integer> getShopStatus(){
        Integer status = (Integer) redisTemplate.opsForValue().get("SHOP_STATUS");
        return Result.success(status);
    }

}
