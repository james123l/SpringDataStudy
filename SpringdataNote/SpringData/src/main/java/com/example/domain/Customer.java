package com.example.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
//使用javax和springdata

@Entity     //标记为实体类
@Table(name="cst_customer") //映射customer表
@Data
@ToString
public class Customer {
    /*主键
     GenerationType配置主键策略
        IDENTITY:自增 适合mysql
        SEQUENCE: 序列 适合oracle
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
