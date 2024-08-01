package v1;

import com.study.spring.beans.DefaultBeanFactory;
import com.study.spring.beans.PreBuildBeanFactory;
import org.junit.AfterClass;
import org.junit.Test;

import com.study.spring.beans.BeanDefinition;
import com.study.spring.beans.GenericBeanDefinition;
import com.study.spring.samples.ABean;
import com.study.spring.samples.ABeanFactory;

public class DefaultBeanFactoryTest {

//	static DefaultBeanFactory bf = new DefaultBeanFactory();
	static PreBuildBeanFactory bf = new PreBuildBeanFactory();

	@Test
	public void testRegist() throws Exception {

		GenericBeanDefinition bd = new GenericBeanDefinition();

		bd.setBeanClass(ABean.class);
		bd.setScope(BeanDefinition.SCOPE_SINGLETION);
		// bd.setScope(BeanDefinition.SCOPE_PROTOTYPE);

		bd.setInitMethodName("init");
		bd.setDestroyMethodName("destroy");

		bf.registerBeanDefinition("aBean", bd);

	}

	@Test
	public void testRegistStaticFactoryMethod() throws Exception {
		GenericBeanDefinition bd = new GenericBeanDefinition();
		bd.setBeanClass(ABeanFactory.class);
		bd.setFactoryMethodName("getABean");
		bf.registerBeanDefinition("staticAbean", bd);
	}

	@Test
	public void testRegistFactoryMethod() throws Exception {
		//注册工厂Bean
		GenericBeanDefinition bd = new GenericBeanDefinition();
		bd.setBeanClass(ABeanFactory.class);
		String fbname = "factory";
		bf.registerBeanDefinition(fbname, bd);

		//注册由工厂bean来创建实例的bean
		bd = new GenericBeanDefinition();
		bd.setFactoryBeanName(fbname);
		bd.setFactoryMethodName("getABean2");
		bd.setScope(BeanDefinition.SCOPE_PROTOTYPE);
		bd.setPrimary(true);

		bf.registerBeanDefinition("factoryAbean", bd);
	}

	@AfterClass
	public static void testGetBean() throws Exception {

		//执行typeMap生成
		bf.registerTypeMap();

		bf.preInstantiateSingletons();

		System.out.println("构造方法方式------------");
		for (int i = 0; i < 3; i++) {
			ABean ab = (ABean) bf.getBean("aBean");
			ab.doSomthing();
		}

		System.out.println("静态工厂方法方式------------");
		for (int i = 0; i < 3; i++) {
			ABean ab = (ABean) bf.getBean("staticAbean");
			ab.doSomthing();
		}

		System.out.println("工厂方法方式------------");
		for (int i = 0; i < 3; i++) {
			ABean ab = (ABean) bf.getBean("factoryAbean");
			ab.doSomthing();
		}

		System.out.println("测试按Type获取Bean----------------");
		ABean ab = bf.getBean(ABean.class);
		ab.doSomthing();

//		bf.close();

		//程序结束的时候： 自然结束   主动关停
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("hook shut down");
				try {
					bf.close();
				}catch (Exception e){
					System.err.println(e);
				}
			}
		}));
	}
}
