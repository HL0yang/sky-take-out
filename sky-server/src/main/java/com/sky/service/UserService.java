package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;

public interface UserService {
    /**
     * 员工登录
     *
     * @param userLoginDTO
     * @return
     */
    User userLogin(UserLoginDTO userLoginDTO);
}
