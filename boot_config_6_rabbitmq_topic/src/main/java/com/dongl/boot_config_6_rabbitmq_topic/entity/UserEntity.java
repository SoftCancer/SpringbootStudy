package com.dongl.boot_config_6_rabbitmq_topic.entity;

import lombok.Data;

/**
 * @description:
 * @author: YaoGuangXun
 * @date: 2020/1/6 15:49
 */
@Data
public class UserEntity {

    private String id ;
    private String name;
    private int age ;

    public UserEntity() {
    }

    public UserEntity(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
