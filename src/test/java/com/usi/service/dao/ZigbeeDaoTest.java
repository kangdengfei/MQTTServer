package com.usi.service.dao;

import com.usi.service.entity.Zigbee;
import com.usi.service.utils.GetSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * created  by KDF on 2017/11/9.
 */


public class ZigbeeDaoTest {

    @Test
 public void getAll() {
       SqlSession session= GetSessionUtil.openSession();
        ZigbeeDao zigbeeDao = session.getMapper(com.usi.service.dao.ZigbeeDao.class);

       List<Zigbee> user1 = zigbeeDao.findAllZigbee();
       System.out.println(user1);
    }

}