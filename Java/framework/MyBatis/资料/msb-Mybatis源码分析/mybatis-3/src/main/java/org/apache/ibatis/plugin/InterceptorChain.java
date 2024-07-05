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
package org.apache.ibatis.plugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * InterceptorChain 记录所有的拦截器
 * @author Clinton Begin
 */
public class InterceptorChain {

  // 保存所有的 Interceptor  也就我所有的插件是保存在 Interceptors 这个List集合中的
  private final List<Interceptor> interceptors = new ArrayList<>();

  // 现在我们定义的有一个 Interceptor MyInterceptor
  public Object pluginAll(Object target) {
    for (Interceptor interceptor : interceptors) { // 获取拦截器链中的所有拦截器
      target = interceptor.plugin(target); // 创建对应的拦截器的代理对象
    }
    return target;
  }

  public void addInterceptor(Interceptor interceptor) {
    interceptors.add(interceptor);
  }

  public List<Interceptor> getInterceptors() {
    return Collections.unmodifiableList(interceptors);
  }

}
