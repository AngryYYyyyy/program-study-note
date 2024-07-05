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
package org.apache.ibatis.reflection.property;

import java.util.Iterator;

/**
 * 属性分词器
 *   orders[0].items[0].name
 * @author Clinton Begin
 */
public class PropertyTokenizer implements Iterator<PropertyTokenizer> {
  private String name; // 当前表达式的名称
  private final String indexedName; // 当前表达式的索引名称
  private String index; // 索引下标 数组[0] 则是0 map[key] 则是key
  private final String children; // 子表达式

  /**
   *  例如 ：orders[0].items[0].name
   * 解析结果：
   * name=orders
   * indexedName=orders[0]
   * index=0
   * children=item[name].name
   * @param fullname
   */
  public PropertyTokenizer(String fullname) {
    int delim = fullname.indexOf('.'); // 查找 . 的位置
    if (delim > -1) {
      // orders[0].items[0].name
      name = fullname.substring(0, delim); // 初始化 name = orders[0]
      children = fullname.substring(delim + 1); // 初始化 children = items[0].name
    } else { // 如果不包含 .
      name = fullname;
      children = null;
    }
    indexedName = name; // 初始化 indexedName  indexedName = orders[0]
    delim = name.indexOf('[');
    if (delim > -1) {
      index = name.substring(delim + 1, name.length() - 1); // index = 0
      name = name.substring(0, delim); // name = orders
    }
  }

  public String getName() {
    return name;
  }

  public String getIndex() {
    return index;
  }

  public String getIndexedName() {
    return indexedName;
  }

  public String getChildren() {
    return children;
  }

  @Override
  public boolean hasNext() {
    return children != null;
  }

  @Override
  public PropertyTokenizer next() {
    return new PropertyTokenizer(children);
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException("Remove is not supported, as it has no meaning in the context of properties.");
  }
}
