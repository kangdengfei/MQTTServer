package com.usi.service.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.Reader;

/**
 * created  by KDF on 2017/9/27.
 */


public class GetSessionUtil {
    private static SqlSessionFactory sqlSessionFactory;
    public static SqlSessionFactory getSqlSessionFactory(){
        if(sqlSessionFactory == null){
            String resource = "mybatis-config.xml";
            try{
                Reader reader = Resources.getResourceAsReader(resource);
                sqlSessionFactory=new SqlSessionFactoryBuilder().build(reader);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return sqlSessionFactory;
    }
    public static SqlSession openSession(){
        return getSqlSessionFactory().openSession();
    }
}
