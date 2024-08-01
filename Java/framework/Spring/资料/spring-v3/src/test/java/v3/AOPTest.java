package v3;

import java.util.ArrayList;
import java.util.List;

import com.study.spring.beans.BeanPostProcessor;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;

import com.study.spring.aop.AdvisorAutoProxyCreator;
import com.study.spring.aop.advisor.AspectJPointcutAdvisor;
import com.study.spring.beans.BeanReference;
import com.study.spring.beans.GenericBeanDefinition;
import com.study.spring.beans.PreBuildBeanFactory;
import com.study.spring.samples.ABean;
import com.study.spring.samples.CBean;
import com.study.spring.samples.aop.MyAfterReturningAdvice;
import com.study.spring.samples.aop.MyBeforeAdvice;
import com.study.spring.samples.aop.MyMethodInterceptor;

public class AOPTest {

	static PreBuildBeanFactory bf = new PreBuildBeanFactory();

	@Test
	public void testAop() throws Throwable {

		GenericBeanDefinition bd = new GenericBeanDefinition();
		bd.setBeanClass(ABean.class);
		List<Object> args = new ArrayList<>();
		args.add("abean01");
		args.add(new BeanReference("cbean"));
		bd.setConstructorArgumentValues(args);
		bf.registerBeanDefinition("abean", bd);

		bd = new GenericBeanDefinition();
		bd.setBeanClass(CBean.class);
		args = new ArrayList<>();
		args.add("cbean01");
		bd.setConstructorArgumentValues(args);
		bf.registerBeanDefinition("cbean", bd);

		// 前置增强advice bean注册
		bd = new GenericBeanDefinition();
		bd.setBeanClass(MyBeforeAdvice.class);
		bf.registerBeanDefinition("myBeforeAdvice", bd);

		// 环绕增强advice bean注册
		bd = new GenericBeanDefinition();
		bd.setBeanClass(MyMethodInterceptor.class);
		bf.registerBeanDefinition("myMethodInterceptor", bd);

		// 后置增强advice bean注册
		bd = new GenericBeanDefinition();
		bd.setBeanClass(MyAfterReturningAdvice.class);
		bf.registerBeanDefinition("myAfterReturningAdvice", bd);

		//注册Advisor(通知者/切面）bean
		//切面1
		bd = new GenericBeanDefinition();
		bd.setBeanClass(AspectJPointcutAdvisor.class);
		args = new ArrayList<>();
		args.add("myBeforeAdvice");
		args.add("execution(* com.study.spring.samples.ABean.*(..))");
		bd.setConstructorArgumentValues(args);
		bf.registerBeanDefinition("aspectJPointcutAdvisor1", bd);

		//切面2
		bd = new GenericBeanDefinition();
		bd.setBeanClass(AspectJPointcutAdvisor.class);
		args = new ArrayList<>();
		args.add("myMethodInterceptor");
		args.add("execution(* com.study.spring.samples.ABean.do*(..))");
		bd.setConstructorArgumentValues(args);
		bf.registerBeanDefinition("aspectJPointcutAdvisor2", bd);

		//切面3
		bd = new GenericBeanDefinition();
		bd.setBeanClass(AspectJPointcutAdvisor.class);
		args = new ArrayList<>();
		args.add("myAfterReturningAdvice");
		args.add("execution(* com.study.spring.samples.ABean.do*(..))");
		bd.setConstructorArgumentValues(args);
		bf.registerBeanDefinition("aspectJPointcutAdvisor3", bd);

		//配置AdvisorAutoProxyCreator的Bean
		bd = new GenericBeanDefinition();
		bd.setBeanClass(AdvisorAutoProxyCreator.class);
		bf.registerBeanDefinition("advisorAutoProxyCreator", bd);

		//生成Type映射
		bf.registerTypeMap();

		//Bean定义都注册完成后，在生成普通Bean实例前
		// 从BeanFactory中得到所有用户配置的BeanPostProcessor类型的Bean实例，注册到BeanFactory
		List<BeanPostProcessor> beanPostProcessors = bf.getBeansOfTypeList(BeanPostProcessor.class);
		if(CollectionUtils.isNotEmpty(beanPostProcessors)){
			for (BeanPostProcessor bpp : beanPostProcessors) {
				bf.registerBeanPostProcessor(bpp);
			}
		}

		//提前实例化单例
		bf.preInstantiateSingletons();

		//--- IOC容器准备好了，可以用了，来获取Bean吧！！

		ABean abean = (ABean) bf.getBean("abean");

		abean.doSomthing();
		System.out.println("--------------------------------");
		abean.sayHello();
	}
}
