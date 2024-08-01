package v2;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.study.spring.beans.BeanReference;
import com.study.spring.beans.GenericBeanDefinition;
import com.study.spring.beans.PreBuildBeanFactory;
import com.study.spring.samples.DBean;
import com.study.spring.samples.EBean;

public class CirculationDiTest {

	static PreBuildBeanFactory bf = new PreBuildBeanFactory();

	@Test
	public void testCirculationDI() throws Exception {
		GenericBeanDefinition bd = new GenericBeanDefinition();
		bd.setBeanClass(DBean.class);
		List<Object> args = new ArrayList<>();
		args.add(new BeanReference("ebean"));
		bd.setConstructorArgumentValues(args);
		bf.registerBeanDefinition("dbean", bd);

		bd = new GenericBeanDefinition();
		bd.setBeanClass(EBean.class);
		args = new ArrayList<>();
		args.add(new BeanReference("dbean"));
		bd.setConstructorArgumentValues(args);
		bf.registerBeanDefinition("ebean", bd);

		bf.preInstantiateSingletons();
	}
}

/*
	 单例：
	 1   A  -- B
	 2       A

	 原型：
	 1   A1 ---
	 2    A2
* */
