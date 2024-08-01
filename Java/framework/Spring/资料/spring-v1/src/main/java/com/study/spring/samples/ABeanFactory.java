package com.study.spring.samples;

public class ABeanFactory {

	public static ABean getABean() {
		return new ABean();
	}

	public ABean getABean2() {
		return new ABean();
	}
}
