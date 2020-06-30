package com.example.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.User;
import com.example.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @author jy
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {
}
