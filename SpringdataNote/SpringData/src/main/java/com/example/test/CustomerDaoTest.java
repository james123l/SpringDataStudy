package com.example.test;


import com.example.dao.CustomerDao;
import com.example.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)     //注明spring提供的单元测试环境
@ContextConfiguration(locations = "classpath:applicationContext.xml")       //指定spring容器的配置信息 类路径下的application.Context.xml
public class CustomerDaoTest {
    @Autowired
    private CustomerDao customerDao;


    @Test
    //findById 根据id查询单一对象
    //底层调用find 立即加载
    public void findbyid(){
        Customer customer = customerDao.findById(1l).get();
        System.out.println(customer);
    }

    @Test
    @Transactional
    //getone 根据id查询单一对象 需要在事务中使用
    //底层调用getReference延迟加载 返回的是customer的动态代理对象
    public void getone(){
        Customer customer = customerDao.getOne(1l);
        System.out.println(customer);
    }

    @Test
    //save 保存或者更新
    //如果save函数内的对象id不为空就是更新 为空就是储存
    public void save(){
        Customer customer = new Customer();
        customer.setCustId(1l);
        customerDao.save(customer);
    }

    @Test
    //delete 删除
    public void delete(){
        Customer customer = new Customer();
        customer.setCustId(1l);
        customerDao.delete(customer);
        customerDao.deleteById(1l);
    }

    @Test
    //findAll 查找所有
    public void findAll(){
        customerDao.findAll();
    }

    @Test
    //count 统计查询
    public void count(){
        long count = customerDao.count();
        System.out.println(count);
    }

    @Test
    //exist 查询是否存在
    public void exist(){
        boolean b = customerDao.existsById(1l);
    }


    //********************************** jpql ******************************************
    @Test
    public void findByCustName(){
        Customer zhangsan = customerDao.findCustomerByCustName("zhangsan");
        System.out.println(zhangsan);
    }
    @Test
    public void findByCustNameAndCustId(){
        Customer zhangsan = customerDao.findByCustNameAndCustId("zhangsan",1l);
        System.out.println(zhangsan);
    }
    @Test
    @Transactional
    @Rollback(false)
    public void updateName(){
        customerDao.updateName(1l,"lisi");
    }
    @Test
    public void findAllBySql(){
        List<Object[]> allNativeQuery = customerDao.findAllNativeQuery();
        for (Object[] objs: allNativeQuery ) {
            System.out.println(objs);
        }
        List<Object[]> nameLike = customerDao.findNameLike("li%");
        for (Object[] objs: nameLike ) {
            System.out.println(objs);
        }
    }
}
