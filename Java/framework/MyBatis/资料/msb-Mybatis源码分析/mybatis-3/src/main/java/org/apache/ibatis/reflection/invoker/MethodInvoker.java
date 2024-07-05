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
package org.apache.ibatis.reflection.invoker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.ibatis.reflection.Reflector;

/**
 * @author Clinton Begin
 */
public class MethodInvoker implements Invoker {

  private final Class<?> type; // 记录第一个参数类型或者方法的返回类型
  private final Method method; // 记录原始的 Method

  /**
   * 构造器：初始化了type和method成员变量
   * @param method
   */
  public MethodInvoker(Method method) {
    this.method = method;
    // 如果method的参数只有一位就记录在type中
    if (method.getParameterTypes().length == 1) {
      // 如果是setter方法，参数个数为一个就返回参数类型
      type = method.getParameterTypes()[0];
    } else {
      // 如果是getter方法 ，type记录方法的返回类型
      type = method.getReturnType();
    }
  }

  @Override
  public Object invoke(Object target, Object[] args) throws IllegalAccessException, InvocationTargetException {
    try {
      // 执行方法
      return method.invoke(target, args);
    } catch (IllegalAccessException e) {
      // 权限检查
      if (Reflector.canControlMemberAccessible()) {
        // 放开强制访问的权限
        method.setAccessible(true);
        // 执行对象的方法
        return method.invoke(target, args);
      } else {
        throw e;
      }
    }
  }

  @Override
  public Class<?> getType() {
    return type;
  }
}
