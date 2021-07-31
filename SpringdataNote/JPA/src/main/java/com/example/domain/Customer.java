package com.example.domain;


import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
// javax的原生jpa使用

@Entity     //标记为实体类
@Table(name="customer") //映射customer表
@Data
@ToString
public class Customer {
    /*主键
     GenerationType配置主键策略
        IDENTITY:自增 适合mysql
        SEQUENCE: 序列 适合oracle
             1.@SequenceGenerator(name ="myGen", sequenceName ="MY_SEQUENCE", allocateSize =1)
                name - 表示该表主键生成策略名称，它被引用在@GeneratedValue中设置的“gernerator”值中。
                sequenceName - 表示生成策略用到的数据库序列名称。
                initialValue - 表示主键初始值，默认为0.
                allocationSize - 每次主键值增加的大小，例如设置成1，则表示每次创建新记录后自动加1，默认为50.
             2. @GeneratedValue(strategy = GenerationType.AUTO（SEQUENCE）, generator ="myGen")
        TABLE:JPA提供的机制 通过一张数据库表提供自增 （记录下次主键值）
        AUTO ：程序自动选择策略
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cust_id")   //映射数据库表内字段
    private Long custId;

    //姓名
    @Column(name = "cust_name")
    private String custName;

    //来源
    @Column(name = "cust_source")
    private String custSource;

    //级别
    @Column(name = "cust_level")
    private String custLevel;

    //所属行业
    @Column(name = "cust_industry")
    private String custIndustry;

    //联系方式
    @Column(name = "cust_phone")
    private String custPhone;

    //地址
    @Column(name = "cust_address")
    private String custAddress;
}
