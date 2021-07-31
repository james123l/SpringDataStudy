package com.example.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtils {
    private static EntityManagerFactory factory;
    static{
        //加载persistence.xml 创建entitymanagerfactory
        //entitymanager 管理数据库数据 管理对象 和映射
        //读取是线程安全的 过程消耗太多资源 所以需要作为配置出现
         factory = Persistence.createEntityManagerFactory("myJpa");
    }

    /**
     * 获取entitymanager对象
     */
    public static EntityManager getEntityManager(){
        return factory.createEntityManager();
    }

}
