package com.qf.service.impl;

import com.qf.dao.UserMapper;
import com.qf.pojo.User;
import com.qf.pojo.UserExample;
import com.qf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByEmail(String email) {
        return userMapper.findByEmail(email);
    }

    @Override
    public void insertUser(User user) {
        userMapper.insert(user);
    }

    @Override
    public void updateUser(User user) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();

        criteria.andIdEqualTo(user.getId());
        userMapper.updateByExampleSelective(user, userExample);
    }


}
