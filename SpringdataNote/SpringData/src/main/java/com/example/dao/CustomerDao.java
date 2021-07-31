package com.example.dao;


import com.example.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**  增删改查测试类在test文件夹 但是git不会上传test文件夹 所以在source包下创建一个test储存
 * springdata提供了两个接口，只要符合SpringDataJpa的dao层接口规范，可以通过继承接口的方式无需写DAO实现类 就实现增删改查功能
 * 原理： 通过动态代理 生成体现了此接口的实现类
 *  JpaRepository<操作的实体类类型，实体类中主键属性的类型> （JpaRepository<User,Integer>） 简单查询 User是Dao对象 Integer是主键类型  * 封装了基本CRUD操作
 *      save()：可以执行更新和保存的工作
 *      delete(Object): 根据主键删除
 *      findOne(Object):根据主键查找
 *      getOne(Object): 需要在dao对应的实体类上加@Proxy(lazy=false)或者在调用类上加@Transaction
 * JpaSpecificationExecutor<操作的实体类类型>（JpaSpecificationExecutor<User> ）支持复杂查询， 需要在接口的自定义的方法上加上@Query(jpql语句)，或者@Query(value= jpql语句,nativeQuery=true)使用sql语句进行查询
 */
//@RepositoryDefinition(domainClass = Customer.class, idClass = Long.class) 可以替代实现接口JpaRepository<Customer,Long>
public interface CustomerDao extends JpaRepository<Customer,Long> ,JpaSpecificationExecutor<Customer> {
    //JpaSpecificationExecutor 可以配置jpql语句
    // jpql语句 ： value可以输入查询语句。  如果需要通过sql语句进行查询 则需要value写入sql，nativeQuery= true;

    /** 根据custName查询
    * jpql： from Customer where custName = ?
    */
    @Query(value = "from Customer where custName = ?")
    public Customer findCustomerByCustName(String custName);

    /** 根据custName 和id查询 多参数时 方法的参数下标 和ql语句中的？需要对应。 或者规定取值来源
     *      query语句中占位符可以添加模糊查询 使用%
     *     @Query(value = "from Customer where custName = ?2 and custId = ?1")
     *     public Customer findByCustNameAndCustId(Long id,String custName);
     * jpql： from Customer where custName = ? and custId = ?
     */
    @Query(value = "from Customer where custName = %?% and custId = ?")
    public Customer findByCustNameAndCustId(String custName,Long id);

    /** query注解只能进行查询 需要配置注解modifying
     * sql ：update cst_customer set cust_name = ? where cust_id = ?
     * jpql : update Customer set custName = ? where custId= ?
     */
    @Modifying  //注明更新操作注解 调用的时候需要调用者在方法是标记事务 @Transactional 但是事务执行后默认回滚 所以需要在调用方法上标记@Rollback(false)
    @Query(value = "update Customer set custName = ?2 where custId = ?1")
    public void updateName(long custId, String custName);

    /**
     * springdata支持注解标记参数 采用  ":param" 的形式 也可以加%模糊匹配
     */
    @Query("select Customer from Customer c where c.custName=:%name% or c.level>:level")
    public Customer getCustomerNameLinkOrLevelGreater(@Param("level")Integer level,@Param("name") String name);

    /**
     * native 查询
     */
    @Query(value = "select * from cst_customer", nativeQuery = true)
    //因为返回的customer信息都是包装类等 所以 用object数组
    public List<Object[]> findAllNativeQuery();

    //@Query(value = " select * from cst_customer" ,nativeQuery = true)
    @Query(value="select * from cst_customer where cust_name like ?1",nativeQuery = true)
    public List<Object [] > findNameLike(String name);


    /**
     * 方法名的约定：可以通过jpa的约定方法名不写jpql语句就实现查询  （ springdataJpa的运行阶段会根据方法名称进行解析）
     *      findBy + 属性名(对象中的属性名首字母大写) + (查询方式)  注：find get read都可以被解析
     *          例如 findByCustName   --   根据客户名称查询
     *
     *      1.findBy  + 属性名称 （根据属性名称进行完成匹配的查询=）
     *      2.findBy  + 属性名称 + “查询方式（Like | isnull）”
     *          findByCustNameLike
     *      3.多条件查询 between and or like notlike isnull isnotnull orderby lessthan greaterthan not in notin endingwith startingwith
     *       支持级联查找 如果主表有合适的属性不会直接从从表找 如果想从从表找 可以在属性名前加下划线
     *          findBy + 属性名 + “查询方式”   + “多条件的连接符（and|or）”  + 属性名 + “查询方式”
     */
    public Customer findByCustName(String custName);


    public List<Customer> findByCustNameLike(String custName);

    //使用客户名称模糊匹配和客户所属行业精准匹配的查询
    public Customer findByCustNameLikeAndCustIndustry(String custName,String custIndustry);

}
