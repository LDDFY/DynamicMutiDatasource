/*
 * 版权所有(C)，上海海鼎信息工程股份有限公司，2018/11/21，所有权利保留。
 *
 * 项目名：	MultDataSource
 * 文件名：	User
 * 模块说明：
 * 修改历史：
 *  2018年-11月-21日  - changhao - 创建。
 */
package com.dynamic.mutidatasource.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;


/**
 * @author changhao
 */
@Data
@Entity
@ToString
@AllArgsConstructor
@Table(schema = "spring_jpa_user")
public class User implements Serializable {

    public User() {

    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    public Integer id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private int age;
}
