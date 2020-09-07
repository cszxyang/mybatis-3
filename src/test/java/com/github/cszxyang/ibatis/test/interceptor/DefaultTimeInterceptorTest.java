package com.github.cszxyang.ibatis.test.interceptor;

import com.github.cszxyang.ibatis.mapper.ContainerMapper;
import com.github.cszxyang.ibatis.mapper.UserMapper;
import com.github.cszxyang.ibatis.test.CachingTest;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

public class DefaultTimeInterceptorTest {
  @Before
  public void init() {
    SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
    sqlSessionFactory = factoryBuilder.build(CachingTest.class.getResourceAsStream("/mybatis/mybatis-config.xml"));
  }

  private SqlSessionFactory sqlSessionFactory;

  @Test
  public void selectUser() throws Exception {
    SqlSession sqlSession = sqlSessionFactory.openSession(true); // 自动提交事务
    ContainerMapper mapper = sqlSession.getMapper(ContainerMapper.class);
    System.out.println(mapper.listContainer(1));
  }
}
