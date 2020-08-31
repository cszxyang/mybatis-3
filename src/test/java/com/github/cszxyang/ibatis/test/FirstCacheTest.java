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

import com.github.cszxyang.ibatis.JDBC;
import com.github.cszxyang.ibatis.mapper.UserMapper;
import org.apache.ibatis.executor.BatchExecutor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.ReuseExecutor;
import org.apache.ibatis.executor.SimpleExecutor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.*;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 缓存测试
 *
* @author cszxyang
* @since 2020-08-31
*/
public class FirstCacheTest {

    private Configuration configuration;
    private Connection connection;
    private JdbcTransaction jdbcTransaction;

    @Before
    public void init() throws SQLException {
        SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = factoryBuilder.build(ExecutorTest.class.getResourceAsStream("/mybatis/mybatis-config.xml"));
        configuration = sqlSessionFactory.getConfiguration();
        connection = DriverManager.getConnection(JDBC.url, JDBC.username, JDBC.password);
        jdbcTransaction = new JdbcTransaction(connection);
    }

    @Test
    public void testBaseExecutor() throws SQLException {
      Executor executor = new SimpleExecutor(configuration, jdbcTransaction);
      MappedStatement ms = configuration.getMappedStatement("com.github.cszxyang.ibatis.mapper.UserMapper.selectUser");
      executor.query(ms, 1, RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER);
      executor.query(ms, 1, RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER);
    }
}
