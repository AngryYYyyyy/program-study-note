/**
 *    Copyright 2009-2024 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Clinton Begin
 */
public interface TypeHandler<T> {

  /**
   * 类型处理器：
   *   JAVA类型   <------>  JDBC类型
   *       JAVA类型 ----> JDBC类型    写
   *       JDBC类型 ----> JAVA类型    读
   *     SQL操作：读 写
   * 负责将Java类型转换为JDBC的类型
   *    本质上执行的就是JDBC操作中的 如下操作
   *        String sql = "SELECT id,user_name,real_name,password,age,d_id from t_user where id = ? and user_name = ?";
   *        ps = conn.prepareStatement(sql);
   *        ps.setInt(1,2);
   *        ps.setString(2,"张三");
   * @param ps
   * @param i 对应占位符的 位置
   * @param parameter 占位符对应的值
   * @param jdbcType 对应的 jdbcType 类型
   * @throws SQLException
   */
  void setParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException;

  /**
   * 从ResultSet中获取数据时会调用此方法，会将数据由JdbcType转换为Java类型
   * ResultSet rs = ps.executeQuery();
   * rs.next;
   * rs.getString(columnName);
   * rs.getInteger(columnIndex)
   *
   * @param columnName Colunm name, when configuration <code>useColumnLabel</code> is <code>false</code>
   */
  T getResult(ResultSet rs, String columnName) throws SQLException;

  T getResult(ResultSet rs, int columnIndex) throws SQLException;

  T getResult(CallableStatement cs, int columnIndex) throws SQLException;

}
