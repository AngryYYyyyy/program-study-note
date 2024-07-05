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
package org.apache.ibatis.datasource;

import java.util.Properties;
import javax.sql.DataSource;

/**
 * DataSourceFactory 在全局配置文件加载解析到 environments 标签中的 dataSource 标签的时候会初始化
 *                   且完成 setProperties 的处理
 * @author Clinton Begin
 */
public interface DataSourceFactory {
  // 设置 DataSource 的相关属性，一般紧跟在初始化完成之后
  void setProperties(Properties props);

  // 获取 DataSource 对象
  DataSource getDataSource();

}
