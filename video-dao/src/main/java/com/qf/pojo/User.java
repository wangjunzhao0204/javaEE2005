package com.qf.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Integer id;

    private String email;

    private String phoneNum;

    private String password;

    private String code;

    private String nickName;

    private String sex;

    private String birthday;

    private String address;

    private String imgUrl;

    private Date createtime;


}