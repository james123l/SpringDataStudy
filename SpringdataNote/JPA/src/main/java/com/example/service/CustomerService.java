package com.example.service;

import com.example.domain.Customer;
import com.example.utils.JPAUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/*
* persist find/getReference  remove merge
* */
public class CustomerService {
    public static void main(String[] args) {
        //测试函数
        //saveCustomer("zhangsan","wu");

    }
    //测试persist函数 保存
    public static void saveCustomer(String name,String industry){
        //获取事务对象
        EntityManager entityManager = JPAUtils.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        //开启事务
        transaction.begin();

        //插入数据
        Customer customer = new Customer();
        customer.setCustName(name);
        customer.setCustIndustry(industry);
        //persist函数 保存
        entityManager.persist(customer);

        //提交事务
        transaction.commit();
        //释放资源
        entityManager.close();
    }


    //测试find / getReference函数 保存
    public static Customer findCustomerById(long id){
        //获取事务对象
        EntityManager entityManager = JPAUtils.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        //开启事务
        transaction.begin();

        //find函数 立即查询所有字段
        Customer customer1 = entityManager.find(Customer.class, id);
        // getReference函数 延迟加载
        Customer customer2 = entityManager.getReference(Customer.class, id);

        //提交事务
        transaction.commit();
        //释放资源
        entityManager.close();
        return customer1;
    }

    //测试remove函数 删除
    public static void deleteCustomerById(long id){
        //获取事务对象
        EntityManager entityManager = JPAUtils.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        //开启事务
        transaction.begin();

        //Customer customer = new Customer();
        //customer.setCustId( id);
        Customer customer = entityManager.getReference(Customer.class, id);
        entityManager.remove(customer);

        //提交事务
        transaction.commit();
        //释放资源
        entityManager.close();
    }

    //测试merge函数 更新
    public static void updateCustomerById(long id,String address){
        //获取事务对象
        EntityManager entityManager = JPAUtils.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        //开启事务
        transaction.begin();

        //更新
        Customer customer = entityManager.getReference(Customer.class, id);
        customer.setCustAddress(address);
        entityManager.merge(customer);

        //提交事务
        transaction.commit();
        //释放资源
        entityManager.close();
    }
}
