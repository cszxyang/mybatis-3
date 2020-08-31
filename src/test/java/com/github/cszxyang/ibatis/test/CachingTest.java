/**
* Copyright 2019-2999 the original author or authors.
* <p>
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* <p>
* http://www.apache.org/licenses/LICENSE-2.0
* <p>
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.github.cszxyang.ibatis.test;

import com.github.cszxyang.ibatis.mapper.UserMapper;
import org.apache.ibatis.session.*;
import org.junit.Before;
import org.junit.Test;

/**
 * 缓存测试
 *
* @author cszxyang
* @since 2020-08-31
*/
public class CachingTest {

    private SqlSessionFactory sqlSessionFactory;

    @Test
    public void selectUser() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession(true); // 自动提交事务
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        System.out.println(mapper.selectUser(1));
        System.out.println(mapper.selectUser(1));
    }

    @Test
    public void addUser() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession(true); // 自动提交事务
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        System.out.println(mapper.selectUser(1));
        System.out.println("增加了" + mapper.addUser("added", 1) + "个用户");
        System.out.println(mapper.selectUser(1));
        sqlSession.close();
    }

    @Test
    public void testLocalCacheScope() throws Exception {
        SqlSession sqlSession1 = sqlSessionFactory.openSession(true); // 自动提交事务
        UserMapper mapper1 = sqlSession1.getMapper(UserMapper.class);

        SqlSession sqlSession2 = sqlSessionFactory.openSession(true); // 自动提交事务
        UserMapper mapper2 = sqlSession2.getMapper(UserMapper.class);

        System.out.println("mapper1 读取数据: " + mapper1.selectUser(1));
        System.out.println("mapper1 读取数据: " + mapper1.selectUser(1));
        System.out.println("mapper2 更新了" + mapper2.updateByIdXml(1, "testLocalCacheScope") + "个用户的数据");
        System.out.println("mapper1 读取数据: " + mapper1.selectUser(1));
        System.out.println("mapper2 读取数据: " + mapper2.selectUser(1));
    }

    @Before
    public void init() {
        SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
        sqlSessionFactory = factoryBuilder.build(CachingTest.class.getResourceAsStream("/mybatis/mybatis-config.xml"));
    }
}
