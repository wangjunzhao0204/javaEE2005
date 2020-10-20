package com.qf.service;

import com.qf.pojo.User;

public interface UserService {
    User findByEmail(String email);

    void insertUser(User user);

    void updateUser(User user);

}
