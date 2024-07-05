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
package org.apache.ibatis.test;

import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaClass;

public class MetaClassTest {

  public static void main(String[] args) {
    MetaClass metaClass = MetaClass.forClass(Son.class, new DefaultReflectorFactory());
    MetaClass person = metaClass.metaClassForProperty("person");
    System.out.println("person = " + person);
    String person1 = metaClass.findProperty("person");
    System.out.println("person1 = " + person1);
  }
}
class Person{



}
class Son{

  public Person getPerson(){
    return new Person();
  }
}
