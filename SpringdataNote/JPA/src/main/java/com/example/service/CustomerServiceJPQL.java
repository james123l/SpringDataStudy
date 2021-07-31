package com.example.service;

import com.example.domain.Customer;
import com.example.utils.JPAUtils;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerServiceJPQL {

    /* 查询全部
    * jpql: from com.example.domain.Customer  使用对象路径 可以省略为Customer
    * sql: select * from cst_customer;
    * */
    public List<Customer> findAll(){
        //获取事务对象
        EntityManager entityManager = JPAUtils.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        //开启事务
        transaction.begin();

        //查询所有customer
        String jpql = "from com.example.domain.Customer";
        Query query = entityManager.createQuery(jpql);
        List<Customer> list = query.getResultList();

        //提交事务
        transaction.commit();
        //释放资源
        entityManager.close();
        return list;
    }

    /* 倒叙查询全部
     * jpql: from Customer  order by custId desc ; 使用对象路径 和属性名
     * sql: select * from cst_customer order by cust_id desc;
     * */
    public List<Customer> findAllDesc(){
        //获取事务对象
        EntityManager entityManager = JPAUtils.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        //开启事务
        transaction.begin();

        //查询所有customer desc
        String jpql = "from com.example.domain.Customer order by custId desc";
        Query query = entityManager.createQuery(jpql);
        List<Customer> list = query.getResultList();

        //提交事务
        transaction.commit();
        //释放资源
        entityManager.close();
        return list;
    }

    /* 查询customer数量
     * jpql: select count(custId) from Customer ; 使用对象路径 和属性名
     * sql: select count(*) from cst_customer ;
     * */
    public Customer findCustomerCount(){
        //获取事务对象
        EntityManager entityManager = JPAUtils.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        //开启事务
        transaction.begin();

        //查询数量
        String jpql = "select count(custId) from Customer ";
        Query query = entityManager.createQuery(jpql);
        Customer res =(Customer) query.getSingleResult();

        //提交事务
        transaction.commit();
        //释放资源
        entityManager.close();
        return res;
    }

    /* 分页查询
     * jpql: from Customer ; 使用对象路径 和属性名
     * sql: select * from cst_customer limit 0,10;
     * */
    public List<Customer> findAllCustomerByPage(){
        //获取事务对象
        EntityManager entityManager = JPAUtils.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        //开启事务
        transaction.begin();

        //查询所有customer
        String jpql = "from Customer ";
        //entityManager.createNativeQuery(sql) 可以通过sql查询
        Query query = entityManager.createQuery(jpql);
        //分页参数 设置起始索引
        query.setFirstResult(0);
        //分页参数 设置每页行数
        query.setMaxResults(10);
        List<Customer> list = query.getResultList();

        //提交事务
        transaction.commit();
        //释放资源
        entityManager.close();
        return list;
    }

    /* 条件查询
     * jpql: from Customer where custName like ?; 使用对象路径 和属性名 ?占位符
     * sql: select * from cst_customer where cust_name like 'ab';
     * */
    public List<Customer> findByNameLike(String schema){
        //获取事务对象
        EntityManager entityManager = JPAUtils.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        //开启事务
        transaction.begin();

        //查询所有customer
        String jpql = " from Customer where custName like ?; ";
        Query query = entityManager.createQuery(jpql);
        //设置占位符 index val 索引位置从1开始计算
        query.setParameter(1,"ab%");
        List<Customer> list = query.getResultList();

        //提交事务
        transaction.commit();
        //释放资源
        entityManager.close();
        return list;
    }

}
