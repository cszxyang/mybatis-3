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
package com.github.cszxyang.ibatis.mapper;


import com.github.cszxyang.ibatis.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * UserMapper 接口
 *
 * @author cszxyang
 * @since 2020-08-18
 */
public interface UserMapper {

   // @Select("select * from t_user where id = #{id}")
    User selectUser(Integer id);

    @Update("update user set name = #{name} where id = #{id}")
    int updateById(Integer id, String name);

    int updateByIdXml(@Param("id") Integer id, @Param("name") String name);

    int addUser(@Param("name") String name, @Param("age") Integer age);
}
