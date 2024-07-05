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
package org.apache.ibatis.session;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.exceptions.ExceptionFactory;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;

/**
 * Builds {@link SqlSession} instances.
 *   通过建造者模式创建 SqlSessionFactory 对象
 * @author Clinton Begin
 */
public class SqlSessionFactoryBuilder {

  public SqlSessionFactory build(Reader reader) {
    return build(reader, null, null);
  }

  public SqlSessionFactory build(Reader reader, String environment) {
    return build(reader, environment, null);
  }

  public SqlSessionFactory build(Reader reader, Properties properties) {
    return build(reader, null, properties);
  }

  public SqlSessionFactory build(Reader reader, String environment, Properties properties) {
    try {
      // 读取配置文件
      XMLConfigBuilder parser = new XMLConfigBuilder(reader, environment, properties);
      // 解析配置文件得到Configuration对象 创建DefaultSqlSessionFactory对象
      return build(parser.parse());
    } catch (Exception e) {
      throw ExceptionFactory.wrapException("Error building SqlSession.", e);
    } finally {
      ErrorContext.instance().reset();
      try {
        reader.close();
      } catch (IOException e) {
        // Intentionally ignore. Prefer previous error.
      }
    }
  }

  public SqlSessionFactory build(InputStream inputStream) {
    return build(inputStream, null, null);
  }

  public SqlSessionFactory build(InputStream inputStream, String environment) {
    return build(inputStream, environment, null);
  }

  public SqlSessionFactory build(InputStream inputStream, Properties properties) {
    return build(inputStream, null, properties);
  }

  /**
   * 1.创建了一个XMLConfigBuilder对象 会完成很多初始化操作 最终的是创建了 Configuration 对象
   * 2.parser.parse() 完成全局配置文件的加载解析 并将相关的信息封装到 Configuration 对象中
   *                  同时会完成映射文件的加载解析，相关的信息同样的会被保存到 Configuration对象中
   *                  select/insert/udpate/delete 标签的信息会被封装到 MapperdStatement对象中
   * 3.build(parser.parse()) ==》直接创建了 DefaultSqlSessionFactory对象
   *
   *
   * 1.创建了一个解析器
   * 2.通过解析器解析全局配置文件
   * 3.通过得到的Configuration对象来创建DefaultSqlSessionFactory实例
   * @param inputStream
   * @param environment
   * @param properties
   * @return
   */
  public SqlSessionFactory build(InputStream inputStream, String environment, Properties properties) {
    try {
      // 用于解析 mybatis-config.xml，同时创建了 Configuration 对象 >> ok  完成了很多的初始化操作
      XMLConfigBuilder parser = new XMLConfigBuilder(inputStream, environment, properties);
      // 解析XML，最终返回一个 DefaultSqlSessionFactory >>
      //  全局配置文件中的信息都被封装到了 Configuration对象中
      //  映射文件中的 配置信息 同样的也被封装到了 Configuration 对象中
      //  一个具体的 CRUD 标签的信息 被封装到 MappedStatment 对象中
      return build(parser.parse());
    } catch (Exception e) {
      throw ExceptionFactory.wrapException("Error building SqlSession.", e);
    } finally {
      ErrorContext.instance().reset();
      try {
        inputStream.close();
      } catch (IOException e) {
        // Intentionally ignore. Prefer previous error.
      }
    }
  }

  public SqlSessionFactory build(Configuration config) {
    return new DefaultSqlSessionFactory(config);
  }

}
