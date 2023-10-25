package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.UserNotLoginException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private static final String wx_Login = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private WeChatProperties weChatProperties;
    @Autowired
    private UserMapper userMapper;
    /**
     * 员工登录
     *
     * @param userLoginDTO
     * @return
     */
    public User userLogin(UserLoginDTO userLoginDTO) {
        //根据微信授权码，调用微信登录相关接口，查询员工
        String openId = getOpenId(userLoginDTO.getCode());

        //判断openid是否为空，如果为空，表示没有在这样的用户，抛出异常
        if(openId==null||openId.length()==0){
            throw new UserNotLoginException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //判断是否是新用户,如果是新用户，则注册新用户
        User user = userMapper.getByOpenId(openId);
        if(user == null){
            user = User.builder()
                    .openid(openId)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(user);
        }

        return user;
    }

    private String getOpenId(String code){

        Map<String, String> claims = new HashMap<>();
        claims.put("appid",weChatProperties.getAppid());
        claims.put("secret",weChatProperties.getSecret());
        claims.put("js_code",code);
        claims.put("grant_type","authorization_code");

        String doGet = HttpClientUtil.doGet(wx_Login, claims);
        JSONObject jsonObject = JSON.parseObject(doGet);

        String openId = jsonObject.getString("openid");

        return openId;
    }
}
