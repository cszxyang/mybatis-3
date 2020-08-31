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
import com.github.cszxyang.ibatis.model.User;
import org.apache.ibatis.executor.BatchExecutor;
import org.apache.ibatis.executor.ReuseExecutor;
import org.apache.ibatis.executor.SimpleExecutor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.*;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author cszxyang
* @since 2020-08-30
*/
public class ExecutorTest {

    private final Logger logger = LoggerFactory.getLogger(ExecutorTest.class);

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
    public void testSimpleExecutor() throws SQLException {
        SimpleExecutor simpleExecutor = new SimpleExecutor(configuration, jdbcTransaction);
        MappedStatement mappedStatement = configuration.
          getMappedStatement("com.github.cszxyang.ibatis.mapper.UserMapper.selectUser");
        List<Object> objects = simpleExecutor.doQuery(mappedStatement, 1, RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER, mappedStatement.getBoundSql(1));
        System.out.println(objects.get(0));
        List<Object> objects1 = simpleExecutor.doQuery(mappedStatement, 1, RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER, mappedStatement.getBoundSql(1));
        System.out.println(objects1.get(0));
    }

  @Test
  public void testReuseExecutor() throws SQLException {
      ReuseExecutor simpleExecutor = new ReuseExecutor(configuration, jdbcTransaction);
      MappedStatement mappedStatement = configuration.
        getMappedStatement("com.github.cszxyang.ibatis.mapper.UserMapper.selectUser");
      List<Object> objects = simpleExecutor.doQuery(mappedStatement, 1, RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER, mappedStatement.getBoundSql(1));
      System.out.println(objects.get(0));
      List<Object> objects1 = simpleExecutor.doQuery(mappedStatement, 1, RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER, mappedStatement.getBoundSql(1));
      System.out.println(objects1.get(0));
  }

  @Test
  public void testBatchExecutor() throws SQLException {
      BatchExecutor batchExecutor = new BatchExecutor(configuration, jdbcTransaction);
      MappedStatement mappedStatement = configuration.
        getMappedStatement("com.github.cszxyang.ibatis.mapper.UserMapper.updateById");
      Map<String, Object> objectHashMap = new HashMap<>();
      objectHashMap.put("id", 1);
      objectHashMap.put("name", "new-cszxyang");
      Map<String, Object> objectHashMap1 = new HashMap<>();
      objectHashMap1.put("id", 1);
      objectHashMap1.put("name", "new-cszxyang1");
      batchExecutor.doUpdate(mappedStatement, objectHashMap);
      batchExecutor.doUpdate(mappedStatement, objectHashMap1);
      batchExecutor.doFlushStatements(false);
  }
}
